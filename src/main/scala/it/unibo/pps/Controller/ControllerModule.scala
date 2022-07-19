package it.unibo.pps.Controller

object ControllerModule:

  trait Controller:
    def notifyStart(): Unit

  trait Provider:
    val controller: Controller

  trait Component:
    class ControllerImpl extends Controller:
      override def notifyStart(): Unit = ???

  trait Interface extends Provider with Component