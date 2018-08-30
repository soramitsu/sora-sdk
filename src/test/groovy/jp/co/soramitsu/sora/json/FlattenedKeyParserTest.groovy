package jp.co.soramitsu.sora.json

import jp.co.soramitsu.sora.sdk.crypto.json.flattener.DeflattenVisitor
import jp.co.soramitsu.sora.sdk.crypto.json.flattener.FlattenedKeyParser
import jp.co.soramitsu.sora.sdk.crypto.json.flattener.ParsingException
import spock.lang.Specification
import spock.lang.Unroll

class FlattenedKeyParserTest extends Specification {

    @Unroll
    def "correct case works: #teststream"() {
        given:
        def visitor = Mock(DeflattenVisitor)
        def parser = new FlattenedKeyParser(teststream)

        when:
        parser.parse(visitor)

        then:
        noExceptionThrown()

        interaction {
            dictKeysToVisit.each { k ->
                1 * visitor.visitDictKey(k)
            }

            arrKeysToVisit.each { z ->
                1 * visitor.visitArrayKey(z)
            }
        }


        where:
        teststream       | dictKeysToVisit | arrKeysToVisit
        "/1:a"           | ["a"]           | []
        "/1:a/1:b/1:c"   | ["a", "b", "c"] | []
        "/1:a_0"         | ["a"]           | [0]
        "_0_1_2"         | []              | [0, 1, 2]
        "/3:a_0"         | ["a_0"]         | []
        "/0:"            | [""]            | []
        "/1:a_0_0_0_0_0" | ["a"]           | [0, 0, 0, 0, 0]
    }


    @Unroll
    def "incorrect case throws: #teststream"() {
        given:
        def visitor = Mock(DeflattenVisitor)
        def parser = new FlattenedKeyParser(teststream)

        when:
        parser.parse(visitor)

        then:
        thrown(ParsingException)

        where:

        _ | teststream
        _ | "1:a"    // does not start with /
        _ | "/2:a"   // end of stream
        _ | "0"      // does not start with _
        _ | ""       // empty string
        _ | "/1:a/0" // valid, then invalid; no colon
        _ | "/-2:aa" // only positive is allowed
        _ | "/00:aa" // two zeroes
    }
}
