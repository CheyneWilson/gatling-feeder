package nz.cheyne.gatling.feeder

import io.gatling.core.feeder.{Feeder, Record}
import nz.cheyne.gatling.distribution.UniformDistribution

/**
 * Produces random integers
 *
 * @param name     The name of the Session Variable
 * @param lower    The lower bound (equal or greater than)
 * @param upper    The upper bound (less than)
 * @param interval The minimum interval between values
 */
class RandomNumberFeeder(name: String, lower: Int = 0, upper: Int, interval: Int = 1) extends Feeder[Int] {
  def hasNext = true

  val distribution = new UniformDistribution(lower, upper, interval)

  override def next(): Record[Int] = Map(name -> distribution.next())
}
