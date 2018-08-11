package jp.co.soramitsu.sora.crypto.signature.suite

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import jp.co.soramitsu.crypto.ed25519.EdDSAPrivateKey
import jp.co.soramitsu.crypto.ed25519.EdDSAPublicKey
import jp.co.soramitsu.sora.common.MockSignature
import jp.co.soramitsu.sora.crypto.common.Consts
import jp.co.soramitsu.sora.crypto.common.KeyTypeEnum
import jp.co.soramitsu.sora.crypto.common.SecurityProvider
import jp.co.soramitsu.sora.crypto.common.SignatureTypeEnum
import jp.co.soramitsu.sora.crypto.proof.Options
import jp.co.soramitsu.sora.crypto.service.JSONCanonizer
import spock.lang.Specification

import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature

class JSONEd25519Sha3SignatureSuiteTest extends Specification {

    def "ed25519-sha3 signature suite unit test"() {
        given:

        def signatureBytes = "signature" as byte[]
        def algorithm = SignatureTypeEnum.Ed25519Sha3Signature.getAlgorithm()

        Signature signature = Spy(MockSignature, constructorArgs: [
                algorithm,
                signatureBytes
        ])

        SecurityProvider provider = Mock(SecurityProvider) {
            getSignature(SignatureTypeEnum.Ed25519Sha3Signature) >> { signature }
        }

        ObjectMapper jsonMapper = Spy(ObjectMapper)

        JSONCanonizer canonizer = Mock(JSONCanonizer) {
            canonize(_ as Options) >> { "options" as byte[] }
            canonize(_ as ObjectNode) >> { "objectNode" as byte[] }
        }


        JSONEd25519Sha3SignatureSuite suite = new JSONEd25519Sha3SignatureSuite(provider, canonizer, jsonMapper)

        def object = [
                "a": 1
        ] as Map<String, Object>

        PrivateKey privateKey = Stub(EdDSAPrivateKey)
        PublicKey publicKey = Stub(EdDSAPublicKey)

        Options options = new Options(
                SignatureTypeEnum.Ed25519Sha3Signature,
                "created",
                "creator",
                "nonce",
                "purpose"
        )


        when: "sign JSON"
        ObjectNode out = suite.sign(object, privateKey, options)

        then:
        out.toString() == '{"a":1,"proof":{"type":"Ed25519Sha3Signature","created":"created","creator":"creator","nonce":"nonce","purpose":"purpose","signatureValue":"7369676E6174757265"}}'

        when: "verify proof"
        boolean valid = suite.verify(out, publicKey)

        then: "proof is valid"
        noExceptionThrown()
        valid
    }


    def "ed25519-sha3 with onecoder integration test"() {
        given:
        def securityProvider = new SecurityProvider()

        JSONEd25519Sha3SignatureSuite suite = new JSONEd25519Sha3SignatureSuite()
        def object = [
                "a": 1
        ] as Map<String, Object>


        when: "generate a keypair"
        KeyPairGenerator generator = securityProvider.getKeyPairGenerator(KeyTypeEnum.Ed25519Sha3)
        KeyPair keyPair = generator.generateKeyPair()

        then: "EdDSA keypair is generated"
        noExceptionThrown()
        keyPair != null

        when: "sign object"
        Options options = new Options(
                suite.type,
                "created",
                "creator",
                "nonce",
                "purpose"
        )

        ObjectNode signed = suite.sign(object, keyPair.getPrivate() as EdDSAPrivateKey, options)

        then: "output object contains required fields"
        noExceptionThrown()
        signed != null
        signed.hasNonNull(Consts.PROOF_KEY)
        signed.hasNonNull("a")
        signed.get("a").intValue() == 1
        signed.get(Consts.PROOF_KEY).get("type").asText() == Consts.ED25519_SHA3_SIGNATURE
        signed.get(Consts.PROOF_KEY).hasNonNull("signatureValue")

        when: "signature is valid"
        boolean valid = suite.verify(signed, keyPair.getPublic() as EdDSAPublicKey)

        then:
        noExceptionThrown()
        valid
    }
}
