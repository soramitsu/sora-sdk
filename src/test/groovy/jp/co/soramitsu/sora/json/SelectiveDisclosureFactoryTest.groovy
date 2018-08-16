package jp.co.soramitsu.sora.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import jp.co.soramitsu.sora.crypto.common.Hash
import jp.co.soramitsu.sora.crypto.common.SaltGenerator
import jp.co.soramitsu.sora.crypto.json.Flattener
import jp.co.soramitsu.sora.crypto.json.Hashifier
import jp.co.soramitsu.sora.crypto.json.Saltifier
import jp.co.soramitsu.sora.crypto.json.SelectiveDisclosureFactory
import jp.co.soramitsu.sora.crypto.merkle.MerkleTreeFactory
import org.spongycastle.jcajce.provider.digest.SHA3
import spock.lang.Specification

import java.security.MessageDigest

import static jp.co.soramitsu.sora.crypto.common.ArrayTree.getParentIndex

class SelectiveDisclosureFactoryTest extends Specification {

    def "selective disclosure works"() {
        given:
        def expectedRoot = "45B83A888B53A5937836DDE0A02E1667BA7151C4778D7C2E12CDB1EE800F6980"

        def claim = [
                "name": "bogdan",
                "has" : [
                        "documents": [
                                "passport",
                                "driverlicense"
                        ],
                        "animals"  : [],
                ]
        ]

        MessageDigest digest = Spy(SHA3.Digest256)
        SaltGenerator gen = Mock(SaltGenerator) {
            next() >> { "salt" }
        }
        ObjectMapper mapper = Spy(ObjectMapper)
        Flattener flattener = Spy(Flattener)
        Saltifier saltifier = Spy(Saltifier, constructorArgs: [mapper, gen])
        Hashifier hashifier = Spy(Hashifier, constructorArgs: [digest])
        MerkleTreeFactory merkleTreeFactory = Spy(MerkleTreeFactory, constructorArgs: [digest])

        def factory = new SelectiveDisclosureFactory(
                mapper,
                flattener,
                saltifier,
                hashifier,
                merkleTreeFactory
        )


        when: "selective disclosure item is created"
        def sd = factory.createCommitment(claim)

        then: "it is valid"
        noExceptionThrown()
        sd.getCommitment().toString() == expectedRoot
        sd.getMerkleTree().getHashTree().size() == 7 // total number of nodes for 4 leaves
        sd.getSaltifiedJson().toString() == "{\"/4:name\":{\"v\":\"bogdan\",\"s\":\"salt\"},\"/3:has/9:documents_0\":{\"v\":\"passport\",\"s\":\"salt\"},\"/3:has/9:documents_1\":{\"v\":\"driverlicense\",\"s\":\"salt\"},\"/3:has/7:animals\":{\"v\":[],\"s\":\"salt\"}}"

        interaction {
            1 * mapper.valueToTree(_)
            1 * flattener.flatten(_ as ObjectNode)
            1 * saltifier.saltify(_ as ObjectNode)  // 1 call for ObjectNode
            4 * saltifier.saltify(_)                // 4 calls for keys of ObjectNode
            1 * hashifier.hashify(_ as ObjectNode)
            1 * merkleTreeFactory.createFromLeaves(_ as List<Hash>)
        }


        when: "calculate affected (covered by hash) JSON keys by ROOT"
        def keys = sd.getAffectedKeys(Hash.fromHex(expectedRoot))

        then: "affected JSON keys by ROOT are ALL keys"
        keys.size() == 4 // all keys are covered by root
        sd.getSaltifiedJson().fieldNames().toSet() == keys.toSet()

        when: "calculate affected (covered by hash) JSON keys by leftmostleaf's parent"
        def tree = sd.getMerkleTree().getHashTree()
        def parentIndex = getParentIndex(tree.leftmostLeafIndex())
        def parentHash = tree.get(parentIndex)
        def keys2 = sd.getAffectedKeys(parentHash)

        then: "should be equal to the first two keys in JSON"
        keys2.toSet() == ["/4:name", "/3:has/9:documents_0"].toSet()
    }
}
