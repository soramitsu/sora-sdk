package jp.co.soramitsu.sora.sdk.did.validation

import spock.lang.Specification

import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset

class ISO8601DateTimeFormatterTest extends Specification {
    def "given UTC+1 datetime, formatted as UTC+0"() {
        given:
        def dt = Instant.ofEpochSecond(0)
                .atZone(ZoneId.of("UTC+1"))

        when:
        String s = ISO8601DateTimeFormatter.format(dt.toInstant())
        Instant parsed = Instant.parse(s)

        then:
        parsed.atZone(ZoneOffset.UTC).hour == 0
        dt.hour == 1
        parsed.atZone(ZoneOffset.UTC).minute == dt.minute
        parsed.atZone(ZoneOffset.UTC).second == dt.second
    }
}
