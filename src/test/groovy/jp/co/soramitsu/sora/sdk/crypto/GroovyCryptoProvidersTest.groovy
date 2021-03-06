package jp.co.soramitsu.sora.sdk.crypto

import jp.co.soramitsu.crypto.ed25519.EdDSAEngine
import jp.co.soramitsu.crypto.ed25519.EdDSAKey
import jp.co.soramitsu.crypto.ed25519.EdDSASecurityProvider
import org.spongycastle.jce.provider.BouncyCastleProvider
import spock.lang.Specification

import java.security.KeyPairGenerator
import java.security.MessageDigest
import java.security.Security
import java.security.Signature

class GroovyCryptoProvidersTest extends Specification {

    def "has ed25519-sha3"() {
        when:
        Security.addProvider(new EdDSASecurityProvider())
        Signature.getInstance(EdDSAEngine.SIGNATURE_ALGORITHM, EdDSASecurityProvider.PROVIDER_NAME)
        Signature.getInstance(EdDSAEngine.SIGNATURE_ALGORITHM)
        KeyPairGenerator.getInstance(EdDSAKey.KEY_ALGORITHM, EdDSASecurityProvider.PROVIDER_NAME)
        KeyPairGenerator.getInstance(EdDSAKey.KEY_ALGORITHM)

        then:
        noExceptionThrown()
    }

    def "has bouncycastle"() {
        when:
        Security.addProvider(new BouncyCastleProvider())
        MessageDigest.getInstance("SHA3-256", BouncyCastleProvider.PROVIDER_NAME)
        MessageDigest.getInstance("SHA3-256")

        then:
        noExceptionThrown()
    }
}
