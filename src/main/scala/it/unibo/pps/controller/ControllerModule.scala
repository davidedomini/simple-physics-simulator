package it.unibo.pps.controller

import it.unibo.pps.engine.EngineModule
import it.unibo.pps.model.ModelModule
import monix.execution.Scheduler.Implicits.global

object ControllerModule:

  trait Controller:
    def notifyStart(): Unit

  trait Provider:
    val controller: Controller

  type Requirements = ModelModule.Provider with EngineModule.Provider
  
  trait Component:
    context: Requirements =>
    class ControllerImpl extends Controller:
      override def notifyStart(): Unit =
        context.model.init()
        context.simulationEngine
          .simulationStep()
          .loopForever
          .runAsyncAndForget

  trait Interface extends Provider with Component:
    self: Requirements =>