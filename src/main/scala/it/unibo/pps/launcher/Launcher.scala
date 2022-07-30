package it.unibo.pps.launcher

import it.unibo.pps.view.ViewModule
import it.unibo.pps.model.ModelModule
import it.unibo.pps.controller.ControllerModule
import it.unibo.pps.engine.EngineModule
import it.unibo.pps.prolog.PrologEngineModule

object Launcher
    extends ControllerModule.Interface
    with ModelModule.Interface
    with ViewModule.Interface
    with EngineModule.Interface
    with PrologEngineModule.Interface:

  override val controller = ControllerImpl()
  override val model = ModelImpl()
  override val view = ViewImpl()
  override val simulationEngine = SimulationEngineImpl()
  override val prologEngine = PrologEngineImpl()

  @main def Main(): Unit =
    println("Starting simulation")
