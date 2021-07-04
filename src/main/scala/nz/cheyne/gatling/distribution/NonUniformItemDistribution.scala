package nz.cheyne.gatling.distribution

import com.google.common.collect.ImmutableRangeMap
import com.google.common.collect.{Range => JRange}

import java.util.concurrent.ThreadLocalRandom

/**
 * This distribution returns random items according to a non-uniform distribution.
 *
 * @param source A mapping of items to the relative frequency they should occur.
 *               A map like Map('A' -> 2, 'B' -> 8) indicates 'A' occurs 20% of the time, and 'B' occurs 80% of the time.
 * @tparam T The type of item
 */
class NonUniformItemDistribution[T](source: Map[T, Int]) extends Iterator[T] {
  def hasNext = true

  val total: Int = source.values.sum

  val distribution: ImmutableRangeMap[Integer, T] = NonUniformItemDistribution.createRangeMap(source)

  override def next(): T = {
    val i = ThreadLocalRandom.current().nextInt(total)
    distribution.get(i)
  }
}

object NonUniformItemDistribution {
  /**
   * Transform the frequency distribution map into an ImmutableRangeMap representation.
   *
   * @param source The source distribution mapping items to their relative frequency
   * @tparam T
   * @return The frequency distribution as an ImmutableRangeMap
   */
  def createRangeMap[T](source: Map[T, Int]): ImmutableRangeMap[Integer, T] = {
    val builder = new ImmutableRangeMap.Builder[Integer, T]
    var lower: Int = 0
    source.foreach(x => {
      val upper: Int = lower + x._2
      val range: JRange[Integer] = JRange.closedOpen(lower, upper)
      lower = upper
      if (!range.isEmpty) {
        builder.put(range, x._1)
      }
    })
    builder.build()
  }
}
