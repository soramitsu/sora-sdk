package jp.co.soramitsu.sora.sdk.did.validation

import spock.lang.Specification

import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset

class ISO8601DateTimeFormatterTest extends Specification {
    def "given UTC+1 datetime, formatted as UTC+0"() {
        given:
        def b = Instant.ofEpochSecond(0)
                .atZone(ZoneId.of("UTC+1"))

        when:
        String s = ISO8601DateTimeFormatter.format(b.toInstant())
        Instant parsed = Instant.parse(s)
        def a = parsed.atZone(ZoneOffset.UTC)

        then:
        a.offset != b.offset

        a.hour == 0
        b.hour == 1

        a.year == b.year
        a.month == b.month
        a.dayOfMonth == b.dayOfMonth
        a.dayOfWeek == b.dayOfWeek
        a.dayOfYear == b.dayOfYear
        a.minute == b.minute
        a.second == b.second
        a.nano == b.nano
    }
}
