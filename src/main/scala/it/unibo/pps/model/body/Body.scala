package it.unibo.pps.model.body

import it.unibo.pps.util.RichTuple2.*

case class Body(position: Tuple2[Double, Double], velocity: Tuple2[Double, Double])
case class Body2(position: Tuple2[Double, Double], velocity: Double)

object Body:
  def computeNewVelocity(acceleration: Double , body: Body2, t: Double): Double = body.velocity + acceleration * t

  def computeNewPosition(acceleration: Double , body: Body2, t: Double): Tuple2[Double, Double] =
    (body.position._1 + body.velocity * t + 0.5 * acceleration * (t*t), body.position._2)

  def dummyNewPosition(oldPosition: Tuple2[Double, Double]) = (oldPosition._1 + 2, oldPosition._2)

