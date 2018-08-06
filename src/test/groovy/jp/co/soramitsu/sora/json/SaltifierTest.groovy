package jp.co.soramitsu.sora.json

import com.fasterxml.jackson.databind.ObjectMapper
import jp.co.soramitsu.sora.common.SaltGenerator
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

        def saltifier1 = new Saltifier(mapper, gen)
        def saltifier2 = new Saltifier(mapper, gen, "v", "s")

        when:
        def salted1 = saltifier1.saltify(initial)
        def salted2 = saltifier2.saltify(initial)

        then:
        salted1 == saltified
        salted2 == saltified
    }
}