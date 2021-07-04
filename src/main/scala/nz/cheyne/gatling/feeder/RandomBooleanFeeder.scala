package nz.cheyne.gatling.feeder

import io.gatling.core.feeder.{Feeder, Record}
import java.util.concurrent.ThreadLocalRandom

/**
 * This feeder returns boolean values at random.
 *
 * @param name  The name of the Session Variable
 * @param ratio The ratio of True and False values. If 0, always False. If 1 always True, otherwise proportional to this value.
 */
class RandomBooleanFeeder(name: String, ratio: BigDecimal = 0.5) extends Feeder[Boolean] {
  def hasNext = true

  private val rand: ThreadLocalRandom = ThreadLocalRandom.current();

  private val ratioF: Float = ratio.toFloat

  override def next(): Record[Boolean] = {
    if (ratio == 0.5) {
      Map(name -> rand.nextBoolean())
    } else {
      Map(name -> (rand.nextFloat() < ratioF))
    }
  }
}
