package it.unibo.iris

import org.bytedeco.javacpp.PointerScope
import org.bytedeco.pytorch.OutputArchive
import torch.*

import scala.util.Using

class CustomNetwork extends nn.Module {
  val flatten = nn.Flatten()
  val linearReluStack = register(nn.Sequential(
    nn.Linear(4, 16),
    nn.ReLU(),
    nn.Linear(16, 16),
    nn.ReLU(),
    nn.Linear(16, 3)
  ))

  def apply(x: Tensor[Float32]) =
    val flattened = flatten(x)
    val logits = linearReluStack(flattened)
    nn.functional.softmax(logits, 1)()
}

object Train extends App {
  private val model = CustomNetwork()
  private val dataset = LoadDataset.load("src/main/resources/Iris.csv")

  private val lossFunction = torch.nn.loss.CrossEntropyLoss()
  private val optimizer = torch.optim.Adam(model.parameters, lr=1e-3, amsgrad = true)

  for (epoch <- 1 to 5) {
    for (batch <- dataset.batches(50)) {
      Using.resource(new PointerScope()) { p =>
        val (f, t) = batch.unzip
        val feature = torch.stack(f.map(Tensor(_).to(torch.float32)))
        val target = torch.stack(t.map(Tensor(_).to(torch.float32)))
        optimizer.zeroGrad()
        val prediction = model(feature)
        val loss = lossFunction(prediction, target)
        loss.backward()
        optimizer.step()
      }
    }
  }

  private val archive = new OutputArchive()
  model.save(archive)
  archive.save_to("src/main/resources/net.pt")
}