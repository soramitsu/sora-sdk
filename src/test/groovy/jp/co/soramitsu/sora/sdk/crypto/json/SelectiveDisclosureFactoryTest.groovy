package jp.co.soramitsu.sora.sdk.crypto.json


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import jp.co.soramitsu.sora.sdk.crypto.common.Hash
import jp.co.soramitsu.sora.sdk.crypto.common.SaltGenerator
import jp.co.soramitsu.sora.sdk.crypto.json.flattener.Flattener
import jp.co.soramitsu.sora.sdk.crypto.merkle.MerkleTreeFactory
import org.spongycastle.jcajce.provider.digest.SHA3
import spock.lang.Specification

import java.security.MessageDigest

import static jp.co.soramitsu.sora.sdk.crypto.common.ArrayTree.getParentIndex

class SelectiveDisclosureFactoryTest extends Specification {

    def "selective disclosure works"() {
        given:
        def expectedRoot = "d923052347125b4305269925fe6197903b6c354547dcb49e59a695d6a2fc0264"

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

        def saltifiedClaim = [
                "/4:name"             : [
                        "v": "bogdan",
                        "s": "salt"
                ],
                "/3:has/9:documents_0": [
                        "v": "passport",
                        "s": "salt"
                ],
                "/3:has/9:documents_1": [
                        "v": "driverlicense",
                        "s": "salt"
                ],
                "/3:has/7:animals"    : [
                        "v": [],
                        "s": "salt"
                ]
        ]

        def expectedSaltified = "{\"/4:name\":{\"v\":\"bogdan\",\"s\":\"salt\"},\"/3:has/9:documents_0\":{\"v\":\"passport\",\"s\":\"salt\"},\"/3:has/9:documents_1\":{\"v\":\"driverlicense\",\"s\":\"salt\"},\"/3:has/7:animals\":{\"v\":[],\"s\":\"salt\"}}"

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
        sd.getSaltifiedJson().toString() == expectedSaltified

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
        keys2.toSet() == ["/3:has/7:animals", "/3:has/9:documents_0"].toSet()

        //  fromSaltified
        when: "selective disclosure item is created from saltified"
        def sdsaltified = factory.createCommitmentFromSaltified(
                (ObjectNode) mapper.valueToTree(saltifiedClaim))

        then: "it is valid"
        sdsaltified.getMerkleTree().getHashTree().size() == 7
        sdsaltified.getCommitment().toString() == expectedRoot
        sdsaltified.getSaltifiedJson().toString() == expectedSaltified

        interaction {
            1 * mapper.valueToTree(_)
            1 * hashifier.hashify(_ as ObjectNode)
            1 * merkleTreeFactory.createFromLeaves(_ as List<Hash>)
        }

        when: "calculate affected (covered by hash) JSON keys by ROOT"
        def keysSaltified = sdsaltified.getAffectedKeys(Hash.fromHex(expectedRoot))

        then: "affected JSON keys by ROOT are ALL keys"
        keysSaltified.size() == 4 // all keys are covered by root
        sdsaltified.getSaltifiedJson().fieldNames().toSet() == keysSaltified.toSet()

        when: "calculate affected (covered by hash) JSON keys by leftmostleaf's parent"
        def treeSaltified = sdsaltified.getMerkleTree().getHashTree()
        def parentIndexSaltified = getParentIndex(treeSaltified.leftmostLeafIndex())
        def parentHashSaltified = treeSaltified.get(parentIndexSaltified)
        def keys2Saltified = sd.getAffectedKeys(parentHashSaltified)

        then: "should be equal to the first two keys in JSON"
        keys2Saltified.toSet() == ["/3:has/7:animals", "/3:has/9:documents_0"].toSet()

    }

    def "usage example"() {
        given:
        def obj = [
                "name": "Bogdan",
                "age" : "25"
        ]

        def factory = new SelectiveDisclosureFactory()

        when: "create a commitment and proof for 'name'"
        def sd = factory.createCommitment(obj)
        def proof = sd.createProofForKey("/4:name")

        then: "proof is valid for valid commitment"
        proof.verify(sd.getCommitment())

        and: "proof is not valid for invalid commitment"
        !proof.verify(Hash.fromHex("0000000000000000000000000000000000000000000000000000000000000000"))
    }
}
