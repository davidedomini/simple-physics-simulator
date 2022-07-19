package it.unibo.pps.engine

import it.unibo.pps.view.ViewModule
import it.unibo.pps.model.ModelModule
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
      override def simulationStep(): Task[Unit] = ???

  trait Interface extends Provider with Component:
    self: Requirements =>
