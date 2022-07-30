package it.unibo.pps.prolog

import alice.tuprolog.{Prolog, SolveInfo, Struct, Term, Theory}

object PrologEngineModule:

  given Conversion[String, Term] = Term.createTerm(_)
  given Conversion[Seq[_], Term] = _.mkString("[", ",", "]")
  given Conversion[String, Theory] = Theory.parseLazilyWithStandardOperators(_)

  trait PrologEngine:
    def calcNewPosition(
        x: Double,
        velocity: Double,
        time: Double,
        acceleration: Double
    ): Double

  trait Provider:
    val prologEngine: PrologEngine

  trait Component:
    class PrologEngineImpl extends PrologEngine:
      private val engine =
        Scala2P.createEngine("/UAMCalc.pl")

      def calcNewPosition(
          x: Double,
          velocity: Double,
          time: Double,
          acceleration: Double
      ): Double =
        engine(s"computeNewPosition($x, $velocity, $time, $acceleration, Np)")
          .map(Scala2P.extractTermToString(_, "Np"))
          .toSeq
          .head
          .toDouble

  trait Interface extends Provider with Component
