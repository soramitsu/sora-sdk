package jp.co.soramitsu.sora.json

import com.fasterxml.jackson.databind.ObjectMapper
import jp.co.soramitsu.sora.crypto.json.Hashifier
import spock.genesis.Gen
import spock.lang.Specification

import java.security.MessageDigest

class HashifierTest extends Specification {

    def digest = Mock(MessageDigest)

    def hashifier = new Hashifier(digest)

    def mapper = new ObjectMapper()

    /**
     * Generates <code>len</code> bytearrays
     * @param len number of bytearrays generated
     * @return arr2list
     */
    def bytearraygen(int len) {
        List<byte[]> list = new ArrayList<>(len)

        // generates bytearrays of length 3
        def gen = Gen.list(
                Gen.integer(-127, 127),
                3,
                3
        ).iterator()

        for (int i = 0; i < len; i++) {
            list.add(gen.next() as byte[])
        }

        return list
    }

    def "hashify"() {
        given:
        def tree = mapper.readTree(json)

        // prepare mocked digest:
        for (int i = 0; i < onecoded.size(); i++) {
            byte[] serialized = onecoded.get(i) as byte[]
            byte[] hash = expected.get(i) as byte[]
            digest.digest(serialized) >> hash
        }

        when:
        def hashes = hashifier.hashify(tree)

        then:
        hashes.size() == onecoded.size()
        hashes == expected


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

        onecoded << [
                ["d1:ai2ee"],
                [
                        "d1:adee",
                        "d1:blee",
                        "d1:c0:e",
                        "d1:di0ee"
                ],
                ["d1:ad1:ad1:ad1:a1:?eeee"]
        ]

        expected << [
                bytearraygen(1),
                bytearraygen(4),
                bytearraygen(1)
        ]
    }
}
