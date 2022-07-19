package it.unibo.pps.model

object ModelModule:

  trait Model:
    def addSnapshot(snapshot: Snapshot): Unit
    def getLastSnapshot(): Snapshot

  trait Provider:
    val model: Model

  trait Component:
    class ModelImpl extends Model:
      private var history: List[Snapshot] = List.empty
      override def addSnapshot(snapshot: Snapshot): Unit = history = history :+ snapshot
      override def getLastSnapshot(): Snapshot = history.last

  trait Interface extends Provider with Component
