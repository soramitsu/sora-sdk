package jp.co.soramitsu.sora.sdk.crypto.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import jp.co.soramitsu.sora.sdk.common.MockSignature
import jp.co.soramitsu.sora.sdk.crypto.json.JSONCanonizer
import jp.co.soramitsu.sora.sdk.crypto.json.JSONSigner
import jp.co.soramitsu.sora.sdk.crypto.json.JSONSignerImpl
import jp.co.soramitsu.sora.sdk.model.dto.DID
import jp.co.soramitsu.sora.sdk.model.dto.Options
import jp.co.soramitsu.sora.sdk.model.dto.Proof
import spock.lang.Specification

import java.security.PrivateKey
import java.security.Signature
import java.time.Instant

import static jp.co.soramitsu.sora.sdk.model.type.SignatureTypeEnum.Ed25519Sha3Signature

class JSONSignerImplTest extends Specification {

    def "sign unit test"() {
        given:
        byte[] signatureBytes = "signature" as byte[]
        Map<String, Integer> object = ["a": 1]
        String proofNode = "valid"

        ObjectMapper jsonMapper = Mock(ObjectMapper) {
            valueToTree(object) >> {
                new ObjectMapper().valueToTree(object)
            }

            valueToTree(_ as Proof) >> {
                new ObjectMapper().valueToTree(proofNode)
            }
        }


        JSONCanonizer canonizer = Mock(JSONCanonizer) {
            canonize(_ as Options) >> { "options" as byte[] }
            canonize(_ as ObjectNode) >> { "objectNode" as byte[] }
        }

        def serialized = "objectNodeoptions" as byte[]

        JSONSigner signer = new JSONSignerImpl(canonizer, jsonMapper)

        Signature signature = Spy(MockSignature, constructorArgs: [
                Ed25519Sha3Signature.getAlgorithm(), signatureBytes
        ])
        signature.initSign(Stub(PrivateKey))

        Options options = Options.builder()
                .type(Ed25519Sha3Signature)
                .created(Instant.ofEpochSecond(0))
                .creator(DID.parse("did:sora:uuid:1"))
                .nonce("nonce")
                .purpose("purpose")
                .build()

        when:
        ObjectNode out = signer.sign(object, signature, options)

        then:
        interaction {
            1 * signature.engineUpdate(serialized, 0, serialized.length)
            1 * signature.engineSign() >> { signatureBytes }
        }

        out.toString() == '{"a":1,"proof":"valid"}'
    }
}
