package it.unibo.pps.util

object RichTuple2:
  extension(v: Tuple2[Double, Double])
    def x(n: Double): Tuple2[Double, Double] = (v._1 * n, v._2 * n)
    def ++(v2: Tuple2[Double, Double]): Tuple2[Double, Double] = (v._1 + v2._1, v._2 + v2._2)
    def module: Double = Math.sqrt(v._1 * v._1 + v._2 * v._2)
    def normalize: Tuple2[Double, Double] = (v._1/v.module, v._2/v.module)

