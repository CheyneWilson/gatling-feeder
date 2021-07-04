package nz.cheyne.gatling.feeder

import org.scalatest.flatspec.AnyFlatSpec

class RandomBooleanFeederTest extends AnyFlatSpec {
  // It is not easy to unit-test probabilistic functions. We can boundary value test it.
  it should "only return True when ratio is 1" in {
    val sessionVar = "example"
    val feeder = new RandomBooleanFeeder(sessionVar, ratio = 1)
    for (_ <- 1 to 10) {
      assert(feeder.next()(sessionVar))
    }
  }

  it should "only return False when ratio is 0" in {
    val sessionVar = "example"
    val feeder = new RandomBooleanFeeder(sessionVar, ratio = 0)
    for (_ <- 1 to 10) {
      assert(!feeder.next()(sessionVar))
    }
  }
}
