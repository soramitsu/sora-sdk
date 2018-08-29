package jp.co.soramitsu.sora.sdk.crypto.service.impl

import jp.co.soramitsu.sora.sdk.crypto.json.JSONCanonizer
import jp.co.soramitsu.sora.sdk.crypto.json.JSONCanonizerWithOneCoding
import spock.lang.Specification


class JSONCanonizerWithOneCodingTest extends Specification {

    def "canonizer works"() {
        given:
        JSONCanonizer canonizer = new JSONCanonizerWithOneCoding()

        when:
        def actual = canonizer.canonize(input)

        then:
        actual == expected

        where:

        input              | expected
        ["a": 1, "b": "2"] | "d1:ai1e1:b1:2e" as byte[]
    }

}
