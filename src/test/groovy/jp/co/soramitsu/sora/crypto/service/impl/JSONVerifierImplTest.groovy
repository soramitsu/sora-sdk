package jp.co.soramitsu.sora.crypto.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import jp.co.soramitsu.sora.common.MockSignature
import jp.co.soramitsu.sora.crypto.common.SignatureTypeEnum
import jp.co.soramitsu.sora.crypto.proof.Options
import jp.co.soramitsu.sora.crypto.proof.Proof
import jp.co.soramitsu.sora.crypto.service.JSONCanonizer
import jp.co.soramitsu.sora.crypto.service.JSONVerifier
import spock.lang.Specification

import java.security.PublicKey
import java.security.Signature


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

        Options options = new Options(
                SignatureTypeEnum.Ed25519Sha3Signature,
                "created",
                "creator",
                "nonce",
                "purpose"
        )

        Proof proof = new Proof(options, "signature" as byte[])

        Map<String, Object> withCorrectProof = ["proof": proof]


        ObjectMapper jsonMapper = Spy(ObjectMapper) {
            valueToTree(noproof) >> {
                new ObjectMapper().valueToTree(noproof)
            }

            valueToTree(withproofButIncorrect) >> {
                new ObjectMapper().valueToTree(withproofButIncorrect)
            }

            valueToTree(_ as Proof) >> {
                new ObjectMapper().valueToTree(proofNode)
            }
        }


        JSONCanonizer canonizer = Mock(JSONCanonizer) {
            canonize(_ as Options) >> { "options" as byte[] }
            canonize(_ as ObjectNode) >> { "objectNode" as byte[] }
        }

        JSONVerifier verifier = new JSONVerifierImpl(canonizer, jsonMapper)

        Signature signature = Spy(MockSignature, constructorArgs: [
                SignatureTypeEnum.Ed25519Sha3Signature.getAlgorithm(), signatureBytes
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
