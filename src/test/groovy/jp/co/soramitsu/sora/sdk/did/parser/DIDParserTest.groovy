package jp.co.soramitsu.sora.sdk.did.parser

import jp.co.soramitsu.sora.sdk.did.model.dto.DID
import jp.co.soramitsu.sora.sdk.did.parser.generated.ParserException
import spock.lang.Specification
import spock.lang.Unroll

class DIDParserTest extends Specification {

    @Unroll
    def "#did is valid"() {
        when:
        DID d = DID.parse(did)

        then:
        d.toString() == did
        d.getMethod() == method
        d.getIdentifiers().toList() == ids.toList()
        d.getPath() == path
        d.getFragment() == fragment

        where:
        did                                   || method | ids           | fragment | path
        "did:sora:uuid:1#keys-1"              || "sora" | ["uuid", "1"] | "keys-1" | null
        "did:sora:uuid:1"                     || "sora" | ["uuid", "1"] | null     | null
        "did:sora:uuid:1/this/is/path"        || "sora" | ["uuid", "1"] | null     | "this/is/path"
        "did:sora:uuid:1/this/is/path#keys-1" || "sora" | ["uuid", "1"] | "keys-1" | "this/is/path"
    }

    @Unroll
    def "#did is invalid"() {
        when:
        DID d = DID.parse(did)
        println(d)

        then:
        thrown(ParserException)

        where:
        _ | did
        _ | "DID:sora:uuid:1"  // did must be lowercase
        _ | "did:SORA:uuid:1"  // method must be lowercase
        _ | "dod:sora:uuid:1"  // does no start with did
        _ | "did:"             // empty method

    }
}
