package nz.cheyne.gatling.feeder

import nz.cheyne.gatling.distribution.UniformDistribution

import io.gatling.core.feeder.{Feeder, Record}

/**
 * This feeder returns one of the supplied values at random.
 *
 * @param name   The name of the Session Variable
 * @param values The possible values this Feeder returns
 * @tparam T
 */
class UniformFeeder[T](name: String, values: Seq[T]) extends Feeder[T] {
  override def hasNext: Boolean = true

  require(values.nonEmpty)

  private val distribution = new UniformDistribution(0, values.length)

  private val data = values.toIndexedSeq

  override def next(): Record[T] = {
    val i = distribution.next()
    Map(name -> data(i))
  }
}
