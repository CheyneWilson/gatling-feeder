package nz.cheyne.gatling.distribution

import java.util.concurrent.ThreadLocalRandom


/**
 * Produces a random uniformly distributed integer.
 *
 * @param min      The minimum possible value (inclusive).
 * @param max      The minimum possible value (exclusive).
 * @param interval The minimum interval between possible values (default 1).
 */
class UniformDistribution(min: Int = 1, max: Int, interval: Int = 1) extends Iterator[Int] {
  override def hasNext: Boolean = true

  require(min < max) // nextInt is only defined for values greater than 0

  def next(): Int = {

    val randValue = ThreadLocalRandom.current().nextInt(max - min)

    // Scale value
    if (interval == 1) {
      min + randValue
    } else {
      min + (randValue / interval) * interval
    }
  }
}
