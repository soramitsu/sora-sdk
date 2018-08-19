package jp.co.soramitsu.sora.json

import com.fasterxml.jackson.databind.ObjectMapper
import jp.co.soramitsu.sora.crypto.common.SaltGenerator
import jp.co.soramitsu.sora.crypto.json.Saltifier
import spock.lang.Shared
import spock.lang.Specification

class SaltifierTest extends Specification {


    static def mapper = new ObjectMapper()

    @Shared
    def initial = mapper.readTree(this.getClass().getResourceAsStream('/json/_initial.json'))
    @Shared
    def saltified = mapper.readTree(this.getClass().getResourceAsStream('/json/saltified.json'))


    def "saltify works"() {
        given:
        def gen = Mock(SaltGenerator) {
            next() >> "salt"
        }

        def saltifier1 = new Saltifier(mapper, gen)
        def saltifier2 = new Saltifier(mapper, gen, "v", "s")

        when:
        def salted1 = saltifier1.saltify(initial)
        def salted2 = saltifier2.saltify(initial)

        then:
        salted1 == saltified
        salted2 == saltified
    }

    def "desaltify works"() {
        def saltifier = new Saltifier(mapper, null, "v", "s")

        when:
        def actual = saltifier.desaltify(saltified)

        then:
        actual == initial
    }

    def "saltify then desaltify works"() {
        given:
        def gen = Mock(SaltGenerator) {
            next() >> "salt"
        }

        def saltifier = new Saltifier(mapper, gen, "v", "s")

        when:
        def salted = saltifier.saltify(initial)
        def actual = saltifier.desaltify(salted)

        then:
        actual == initial
    }

    def "is salted works"() {
        given:
        def gen = Mock(SaltGenerator) {
            next() >> "salt"
        }

        def saltifier = new Saltifier(mapper, gen)

        when:
        def actual = saltifier.isSalted(json)

        then:
        actual == expected

        where:
        json      | expected
        initial   | false
        saltified | true
    }
}
