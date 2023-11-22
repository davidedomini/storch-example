package it.unibo.simple

import torch.*

object TensorConstruction extends App {

  val t1 = Tensor(Seq(1.0, 0.0, 0.0)).to(torch.float32)
  val t2 = Tensor(Seq(0.0, 1.0, 0.0)).to(torch.float32)
  val t3 = Tensor(Seq(0.0, 0.0, 1.0)).to(torch.float32)
  println(torch.stack(Seq(t1, t2, t3)))

}
