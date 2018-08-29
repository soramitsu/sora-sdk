package jp.co.soramitsu.sora.sdk.crypto.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import jp.co.soramitsu.sora.sdk.common.MockSignature
import jp.co.soramitsu.sora.sdk.crypto.json.JSONCanonizer
import jp.co.soramitsu.sora.sdk.crypto.json.JSONVerifier
import jp.co.soramitsu.sora.sdk.crypto.json.JSONVerifierImpl
import jp.co.soramitsu.sora.sdk.json.JsonUtil
import jp.co.soramitsu.sora.sdk.model.dto.DID
import jp.co.soramitsu.sora.sdk.model.dto.Options
import jp.co.soramitsu.sora.sdk.model.dto.Proof
import spock.lang.Specification

import java.security.PublicKey
import java.security.Signature
import java.time.Instant

import static jp.co.soramitsu.sora.sdk.model.type.SignatureTypeEnum.Ed25519Sha3Signature

class JSONVerifierImplTest extends Specification {

    def "verify unit test"() {
        given:
        def valid = false
        byte[] signatureBytes = "signature" as byte[]
        String proofNode = "valid"

        Map<String, Integer> noproof = ["noproof": 1]
        Map<String, Object> withproofButIncorrect = ["proof": [
                "signatureValue": "0123456789ABCDEF"
        ]]

        Options options = Options.builder()
                .type(Ed25519Sha3Signature)
                .created(Instant.ofEpochSecond(0))
                .creator(DID.parse("did:sora:uuid:1"))
                .nonce("nonce")
                .purpose("purpose")
                .build()

        Map<String, Object> withCorrectProof = [
                "proof": Proof.builder()
                        .options(options)
                        .signatureValue(signatureBytes)
                        .build()
        ]


        ObjectMapper jsonMapper = Spy(ObjectMapper) {
            valueToTree(noproof) >> {
                JsonUtil.buildMapper().valueToTree(noproof)
            }

            valueToTree(withproofButIncorrect) >> {
                JsonUtil.buildMapper().valueToTree(withproofButIncorrect)
            }

            valueToTree(withCorrectProof) >> {
                JsonUtil.buildMapper().valueToTree(withCorrectProof)
            }

            valueToTree(_ as Proof) >> {
                JsonUtil.buildMapper().valueToTree(proofNode)
            }
        }

        jsonMapper.registerModule(new JavaTimeModule())



        JSONCanonizer canonizer = Mock(JSONCanonizer) {
            canonize(_ as Options) >> { "options" as byte[] }
            canonize(_ as ObjectNode) >> { "objectNode" as byte[] }
        }

        JSONVerifier verifier = new JSONVerifierImpl(canonizer, jsonMapper)

        Signature signature = Spy(MockSignature, constructorArgs: [
                Ed25519Sha3Signature.getAlgorithm(), signatureBytes
        ])

        signature.initVerify(Stub(PublicKey))



        when: "input json has no proof key"
        valid = verifier.verify(noproof, signature)

        then: "proof is invalid"
        !valid

        when: "input json has proof without required fields"
        verifier.verify(withproofButIncorrect, signature)

        then: "can not read proof from json; exception"
        thrown(IOException)

        when: "input json has correct proof"
        valid = verifier.verify(withCorrectProof, signature)

        then: "proof is valid"
        noExceptionThrown()
        valid
        interaction {
            1 * signature.engineVerify(signatureBytes) >> { true }
        }
    }
}
