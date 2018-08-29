package jp.co.soramitsu.sora.sdk.dto

import com.fasterxml.jackson.databind.ObjectMapper
import jp.co.soramitsu.sora.sdk.json.JsonUtil
import jp.co.soramitsu.sora.sdk.model.dto.DDO
import jp.co.soramitsu.sora.sdk.model.dto.DID
import jp.co.soramitsu.sora.sdk.model.dto.authentication.Ed25519Sha3Authentication
import jp.co.soramitsu.sora.sdk.model.dto.publickey.Ed25519Sha3VerificationKey
import jp.co.soramitsu.sora.sdk.model.dto.publickey.PublicKeyVisitor
import jp.co.soramitsu.sora.sdk.model.dto.service.GenericService
import spock.lang.Specification

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
                .publicKey(new Ed25519Sha3VerificationKey(pubkeyId, [48] as byte[]))
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

}
