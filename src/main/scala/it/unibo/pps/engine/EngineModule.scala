package it.unibo.pps.engine

import it.unibo.pps.view.ViewModule
import it.unibo.pps.model.{ModelModule, Snapshot}
import it.unibo.pps.model.body.{Body, Body2}
import monix.eval.{Task, TaskApp}
import monix.execution.Scheduler

import concurrent.duration.{Duration, DurationInt, FiniteDuration}
import scala.language.postfixOps
import scala.language.implicitConversions

object EngineModule:

  trait SimulationEngine:
    def simulationStep(): Task[Unit]

  trait Provider:
    val simulationEngine: SimulationEngine

  type Requirements = ViewModule.Provider with ModelModule.Provider

  trait Component:
    context: Requirements =>
    class SimulationEngineImpl extends SimulationEngine:
      given unitToTask: Conversion[Unit, Task[Unit]] = Task(_)
      given intToTask: Conversion[Int, Task[Int]] = Task(_)
      given doubleToTask: Conversion[Double, Task[Double]] = Task(_)
      given tuple2ToTask: Conversion[Tuple2[Double, Double], Task[Tuple2[Double, Double]]] = Task(_)
      given snapToTask: Conversion[Snapshot, Task[Snapshot]] = Task(_)

      override def simulationStep(): Task[Unit] =
        for
          _ <- waitFor(1 seconds)
          ls <- getLastSnapshot()
          newVel <- computeNewVelocity(ls)
          newPos <- computeNewPosition(Body2(ls.body.position, newVel), ls.time)
          newSnap <- createNewSnapshot(newPos, newVel, ls)
          _ <- addSnapshot(newSnap)
          _ <- context.view.render(newSnap)
        yield ()

      private def getLastSnapshot(): Task[Snapshot] = context.model.getLastSnapshot()
      private def computeNewPosition(b: Body2, t: Int): Task[Tuple2[Double, Double]] = Body.computeNewPosition(8.0, b, t + 1)
      private def waitFor(d: FiniteDuration): Task[Unit] = Task.sleep(d)
      private def createNewSnapshot(newPos: Tuple2[Double, Double], newVel: Double, s: Snapshot): Task[Snapshot] = Snapshot(Body2(newPos, newVel), s.time + 1)
      private def addSnapshot(newSnapshot: Snapshot): Task[Unit] = context.model.addSnapshot(newSnapshot)
      private def computeNewVelocity(s: Snapshot): Task[Double] = Body.computeNewVelocity(8.0, s.body, s.time + 1)

  trait Interface extends Provider with Component:
    self: Requirements =>
