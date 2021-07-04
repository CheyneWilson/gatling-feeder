package nz.cheyne.gatling.feeder

import nz.cheyne.gatling.distribution.NonUniformItemDistribution

import io.gatling.core.feeder.{Feeder, Record}

/**
 * This feeder returns one of the supplied values at random.
 *
 * @param name The name of the Session Variable
 * @param frequencyMap A map of items and the relative frequency which each should occur.
 * @tparam T
 */
class NonUniformFeeder[T](name: String, frequencyMap: Map[T, Int]) extends Feeder[T]{
  override def hasNext: Boolean = true

  require(frequencyMap.nonEmpty)

  private val distribution = new NonUniformItemDistribution(frequencyMap)

  override def next(): Record[T] = {
    Map(name -> distribution.next())
  }
}
