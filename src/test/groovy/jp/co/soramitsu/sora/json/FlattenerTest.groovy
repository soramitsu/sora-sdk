package jp.co.soramitsu.sora.json

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import spock.genesis.Gen
import spock.genesis.transform.Iterations
import spock.lang.Shared
import spock.lang.Specification

class FlattenerTest extends Specification {
    static class FlattenerImpl extends Flattener {
        /* to test protected methods */
    }

    def mapper = new ObjectMapper()
    def flattener = new FlattenerImpl()

    @Shared
    def initial = mapper.readTree(this.getClass().getResourceAsStream('/json/_initial.json'))
    def flattened = mapper.readTree(this.getClass().getResourceAsStream('/json/flattened.json'))

    @Iterations(500)
    def "sanitize and desanitize are opposite"() {
        given:
        expected

        when:
        def sanitized = flattener.sanitize(expected)
        def actual = flattener.desanitize(sanitized)

        then:
        actual == expected

        where:
        expected << Gen.string(~/[a-z\\\/_0-9]+/)
    }

    def "isFlattened works"() {
        given:
        initial
        flattened

        when:
        def f1 = flattener.isFlattened(initial)
        def f2 = flattener.isFlattened(flattened)

        then:
        !f1
        f2
    }

    def "flatten works"() {
        given:
        initial
        flattened

        when:
        def flatActual = flattener.flatten(initial)

        then:
        flatActual == flattened
    }

}
