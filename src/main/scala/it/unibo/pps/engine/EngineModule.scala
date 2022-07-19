package it.unibo.pps.engine

import it.unibo.pps.view.ViewModule
import it.unibo.pps.model.ModelModule

object EngineModule:

  trait SimulationEngine

  trait Provider:
    val simulationEngine: SimulationEngine

  type Requirements = ViewModule.Provider with ModelModule.Provider

  trait Component:
    class SimulationEngineImpl extends SimulationEngine

  trait Interface extends Provider with Component
