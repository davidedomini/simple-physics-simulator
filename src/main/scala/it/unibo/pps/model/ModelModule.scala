package it.unibo.pps.model

import it.unibo.pps.model.body.Body

object ModelModule:

  trait Model:
    def init(): Unit
    def addSnapshot(snapshot: Snapshot): Unit
    def getLastSnapshot(): Snapshot

  trait Provider:
    val model: Model

  trait Component:
    class ModelImpl extends Model:
      private var history: List[Snapshot] = List.empty
      override def init(): Unit = history :+ Snapshot(Body((10,10), (1,1)))
      override def addSnapshot(snapshot: Snapshot): Unit = history = history :+ snapshot
      override def getLastSnapshot(): Snapshot = history.last

  trait Interface extends Provider with Component
