package it.unibo.pps.util

object RichTuple2:
  extension(v: Tuple2[Double, Double])
    def x(n: Double): Tuple2[Double, Double] = (v._1 * n, v._2 * n)

