package it.unibo


import torch.Device.{CPU, CUDA}

object BasicExample extends App {

  private val device = if torch.cuda.isAvailable then CUDA else CPU
  // device: Device = Device(device = CUDA, index = -1)
  torch.rand(Seq(3, 3), device = device)
  // res1: Tensor[Float32] = dtype=float32, shape=[3, 3], device=CUDA
  // [[0.3990, 0.5167, 0.0249],
  //  [0.9401, 0.9459, 0.7967],
  //  [0.4150, 0.8203, 0.2290]]

  // Use device index if you have multiple GPUs
  torch.rand(Seq(3, 3), device = torch.Device(torch.DeviceType.CUDA, 0: Byte))
  // res2: Tensor[Float32] = dtype=float32, shape=[3, 3], device=CUDA
  // [[0.9722, 0.7910, 0.4690],
  //  [0.3300, 0.3345, 0.3783],
  //  [0.7640, 0.6405, 0.1103]]

}
