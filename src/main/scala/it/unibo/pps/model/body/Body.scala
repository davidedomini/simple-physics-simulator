package it.unibo.pps.model.body

import it.unibo.pps.util.RichTuple2.*

case class Body(position: Tuple2[Double, Double], velocity: Tuple2[Double, Double])

object Body:
  def computeNewVelocity(acceleration: Tuple2[Double, Double], oldVelocity: Tuple2[Double, Double], dt: Double) =
    oldVelocity ++ (acceleration x dt)

  def computeNewPosition(velocity: Tuple2[Double, Double], oldPosition: Tuple2[Double, Double], dt: Double) =
    oldPosition ++ (velocity x dt)

