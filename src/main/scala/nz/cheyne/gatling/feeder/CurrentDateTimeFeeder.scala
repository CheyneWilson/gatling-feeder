package nz.cheyne.gatling.feeder

import io.gatling.core.feeder.{Feeder, Record}

import java.time.format.DateTimeFormatter
import java.time.{Instant, ZoneId}

/**
 * This feeder returns the current datetime.
 *
 * @param name     The name of the Session Variable
 * @param format   The date format to use. Defaults to "yyyy-MM-dd'T'HH:mm:ss.SSS".
 * @param timeZone The timezone to use. Defaults to Pacific/Auckland.
 */
class CurrentDateTimeFeeder(name: String,
                            format: String = "yyyy-MM-dd'T'HH:mm:ss.SSS",
                            timeZone: ZoneId = ZoneId.of("Pacific/Auckland")
                           )
  extends Feeder[String] {

  def hasNext = true

  val df: DateTimeFormatter = DateTimeFormatter
    .ofPattern(format)
    .withZone(timeZone)

  override def next(): Record[String] = Map(name -> df.format(Instant.now()))
}
