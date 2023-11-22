package it.unibo.iris

import scala.util.Random

class Dataset {

  private var data: Seq[(Seq[Double], Seq[Double])] = Seq.empty

  def add(features: Seq[Double], target: String): Unit =
    data = data :+ (features, oneHotEncode(target))

  def batches(size: Int): Seq[Seq[(Seq[Double], Seq[Double])]] =
    Random(seed = 42).shuffle(data).grouped(size).toSeq

  private def oneHotEncode(target: String): Seq[Double] = target match {
      case "Iris-setosa" => Seq(1, 0, 0)
      case "Iris-versicolor" => Seq(0, 1, 0)
      case "Iris-virginica" => Seq(0, 0, 1)
    }

}

object LoadDataset {

  def load(path: String): Dataset =
    val dataset = Dataset()
    val buffer = io.Source.fromFile(path)
    for (line <- buffer.getLines()) {
      val col: List[String] = line.split(",").map(_.trim).toList
      val feature: Seq[Double] = col.slice(1, col.length-1).map(_.toDouble)
      val target = col.last
      dataset.add(feature, target)
    }
    buffer.close()
    dataset
}

object TestCsvLoad extends App {
  private val dataset = LoadDataset.load("src/main/resources/Iris.csv")
  for (batch <- dataset.batches(50)) {
    println(batch)
  }
}