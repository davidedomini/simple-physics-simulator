package it.unibo.pps.view

import it.unibo.pps.controller.ControllerModule
import it.unibo.pps.model.Snapshot

object ViewModule:

  trait View:
    def render(s: Snapshot): Unit

  trait Provider:
    val view: View

  type Requirements = ControllerModule.Provider
  
  trait Component:
    context: Requirements =>
    class ViewImpl extends View:
      private val gui = Gui(400, 200, context.controller)
      
      override def render(s: Snapshot): Unit = gui render s.body

  trait Interface extends Provider with Component:
    self: Requirements =>
