package it.unibo.pps.view

object ViewModule:

  trait View:
    def render(): Unit

  trait Provider:
    val view: View

  trait Component:
    class ViewImpl extends View:
      override def render(): Unit = ???

  trait Interface extends Provider with Component
