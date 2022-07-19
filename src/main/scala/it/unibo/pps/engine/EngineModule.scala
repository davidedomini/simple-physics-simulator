package it.unibo.pps.engine

import it.unibo.pps.view.ViewModule
import it.unibo.pps.model.{ModelModule, Snapshot}
import it.unibo.pps.model.body.Body
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
      given tuple2ToTask: Conversion[Tuple2[Double, Double], Task[Tuple2[Double, Double]]] = Task(_)
      given snapToTask: Conversion[Snapshot, Task[Snapshot]] = Task(_)

      override def simulationStep(): Task[Unit] =
        for
          _ <- waitFor(1 seconds)
          ls <- getLastSnapshot()
          newPos <- computeNewPosition(ls.body)
          newSnap <- createNewSnapshot(newPos)
          _ <- addSnapshot(newSnap)
          _ <- context.view.render(newSnap)
        yield ()

      private def getLastSnapshot(): Task[Snapshot] = context.model.getLastSnapshot()
      private def computeNewPosition(b: Body): Task[Tuple2[Double, Double]] = Body.dummyNewPosition(b.position)
      private def waitFor(d: FiniteDuration): Task[Unit] = Task.sleep(d)
      private def createNewSnapshot(newPos: Tuple2[Double, Double]): Task[Snapshot] = Snapshot(Body(newPos, (0,0)))
      private def addSnapshot(newSnapshot: Snapshot): Task[Unit] = context.model.addSnapshot(newSnapshot)

  trait Interface extends Provider with Component:
    self: Requirements =>
