package jp.co.soramitsu.sora.sdk.crypto.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import spock.lang.Specification

import java.security.MessageDigest

class HashifierTest extends Specification {

    def mapper = new ObjectMapper()

    def "hashify"() {
        given:
        def digest = Mock(MessageDigest) {
            digest(_) >> { [1] as byte[] }
        }
        def hashifier = new Hashifier(digest)
        def tree = mapper.readTree(json)

        when:
        def hashes = hashifier.hashify(tree as ObjectNode)

        then:
        hashes.size() == expected


        where:
        json << [
                '{"a":2}',

                '''
                {
                    "a": {},
                    "b": [],
                    "c": "",
                    "d": 0
                }
                ''',

                '''
                {
                  "a": {
                    "a": {
                      "a": {
                        "a": "?"
                      }
                    }
                  }
                }
                '''
        ]

        expected << [1, 4, 1]
    }
}
