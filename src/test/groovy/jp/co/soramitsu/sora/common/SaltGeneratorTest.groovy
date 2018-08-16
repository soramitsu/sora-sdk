package jp.co.soramitsu.sora.common

import jp.co.soramitsu.sora.crypto.common.HexdigestSaltGenerator
import spock.lang.Specification

import javax.xml.bind.DatatypeConverter

class SaltGeneratorTest extends Specification {

    def "generates hexdigest"() {
        given:
        def mockRandom = new MockRandom(singlebyte as byte)
        def gen = new HexdigestSaltGenerator(mockRandom, length)
        when:
        String salt = gen.next()

        then:
        noExceptionThrown()
        salt.size() == length * 2
        salt == DatatypeConverter.printHexBinary(expected as byte[])

        where:
        expected | singlebyte | length
        [1]      | 1          | 1
        [3, 3]   | 3          | 2
    }
}
