package it.unibo.simple

import torch.*
import torch.nn.functional as F

class NeuralNetwork extends nn.Module:
  val flatten = nn.Flatten()
  val linearReluStack = register(nn.Sequential(
    nn.Linear(28*28, 512),
    nn.ReLU(),
    nn.Linear(512, 512),
    nn.ReLU(),
    nn.Linear(512, 10),
  ))

  def apply(x: Tensor[Float32]) =
    val flattened = flatten(x)
    val logits = linearReluStack(flattened)
    logits

object CustomNN extends App {
  val m = NeuralNetwork()
}
