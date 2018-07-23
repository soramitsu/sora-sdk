package jp.co.soramitsu.sora.json

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class SaltifierTest extends Specification {


    def mapper = new ObjectMapper()

    def initial = mapper.readTree(this.getClass().getResourceAsStream('/json/_initial.json'))
    def saltified = mapper.readTree(this.getClass().getResourceAsStream('/json/saltified.json'))


    def "saltify works"() {
        given:
        def gen = Mock(SaltGenerator) {
            next() >> "salt"
        }

        def saltifier = new Saltifier(mapper, gen, "v", "s")

        when:
        def salted = saltifier.saltify(initial)

        then:
        salted == saltified
    }
}
