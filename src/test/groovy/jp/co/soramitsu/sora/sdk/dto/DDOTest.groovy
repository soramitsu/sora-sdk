package jp.co.soramitsu.sora.sdk.dto

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import jp.co.soramitsu.crypto.ed25519.Ed25519Sha3
import jp.co.soramitsu.crypto.ed25519.EdDSAPublicKey
import jp.co.soramitsu.sora.sdk.crypto.json.JSONEd25519Sha3SignatureSuite
import jp.co.soramitsu.sora.sdk.did.model.dto.DDO
import jp.co.soramitsu.sora.sdk.did.model.dto.DID
import jp.co.soramitsu.sora.sdk.did.model.dto.Options
import jp.co.soramitsu.sora.sdk.did.model.dto.authentication.Ed25519Sha3Authentication
import jp.co.soramitsu.sora.sdk.did.model.dto.publickey.Ed25519Sha3VerificationKey
import jp.co.soramitsu.sora.sdk.did.model.dto.service.GenericService
import jp.co.soramitsu.sora.sdk.did.model.type.SignatureTypeEnum
import jp.co.soramitsu.sora.sdk.json.JsonUtil
import spock.lang.Specification

import javax.xml.bind.DatatypeConverter
import java.time.Instant

class DDOTest extends Specification {

    def "can parse valid ddo from json and print to json"() {
        given:
        def mapper = JsonUtil.buildMapper()

        expect:
        stream != null

        when: "deserialize from json"
        DDO ddo = mapper.readValue(stream, DDO.class)

        then: "success"
        noExceptionThrown()

        when: "serialize to json"
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ddo)

        then: "success; json is valid"
        noExceptionThrown()

        where:
        stream = this.getClass().getResourceAsStream("/json/ddo/canonical-1.json")
    }

    def "save ddo then load"() {
        given:
        def file = File.createTempFile("ddo", ".json")
        DID owner = DID.randomUUID()
        Instant created = Instant.now()

        DID pubkeyId = owner.withFragment("keys-1")

        DDO ddo1 = DDO.builder()
                .id(owner)
                .owner(owner)
                .publicKey(new Ed25519Sha3VerificationKey(pubkeyId, null, [48] as byte[]))
                .authentication(new Ed25519Sha3Authentication(pubkeyId))
                .service(new GenericService(owner.withFragment("service-1"), new URL("https://google.com/")))
                .created(created)
                .build()

        ObjectMapper mapper = JsonUtil.buildMapper()

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(file, ddo1)

        when: 'load ddo from file'
        DDO ddo2 = mapper.readValue(file, DDO.class)
        println(ddo2)

        then:
        ddo2 == ddo1
    }

    def "ddo can be signed"() {
        given:
        DID owner = DID.parse("did:sora:bogdan")
        Instant created = Instant.ofEpochMilli(0)

        DID pubkeyId = owner.withFragment("keys-1")

        def privKeySeed = DatatypeConverter.parseHexBinary("0000000000000000000000000000000000000000000000000000000000000000")
        def engine = new Ed25519Sha3()
        def keyPair = engine.generateKeypair(privKeySeed)

        DDO ddo = DDO.builder()
                .id(owner)
                .publicKey(new Ed25519Sha3VerificationKey(pubkeyId, null, keyPair.getPublic().getEncoded()))
                .authentication(new Ed25519Sha3Authentication(pubkeyId))
                .service(new GenericService(owner.withFragment("service-1"), new URL("https://google.com/")))
                .created(created)
                .build()

        def suite = new JSONEd25519Sha3SignatureSuite()
        def mapper = JsonUtil.buildMapper()

        def options = Options.builder()
                .type(SignatureTypeEnum.Ed25519Sha3Signature)
                .nonce("nonce")
                .creator(pubkeyId)
                .created(created)
                .build()

        when:
        ObjectNode signedJson = suite.sign(
                ddo,
                keyPair.getPrivate(),
                options
        )

        DDO signed = mapper.readValue(signedJson.toString(), DDO.class)

        then: "signature is valid"
        suite.verify(signed, keyPair.getPublic() as EdDSAPublicKey)

        when: "break ddo signature"
        signed.setId(DID.randomUUID())

        then:
        !suite.verify(signed, keyPair.getPublic() as EdDSAPublicKey)
    }

}
