package it.unibo.pps.launcher

import it.unibo.pps.view.ViewModule
import it.unibo.pps.model.ModelModule
import it.unibo.pps.controller.ControllerModule
import it.unibo.pps.engine.EngineModule

object Launcher
  extends ControllerModule.Interface
  with ModelModule.Interface
  with ViewModule.Interface
  with EngineModule.Interface:

  override val controller = ControllerImpl()
  override val model = ModelImpl()
  override val view = ViewImpl()
  override val simulationEngine = SimulationEngineImpl()

  @main def Main(): Unit =
    println("Starting simulation")