package jp.co.soramitsu.sora.crypto

import jp.co.soramitsu.sora.common.MockSumMessageDigest
import jp.co.soramitsu.sora.crypto.common.ArrayTree
import jp.co.soramitsu.sora.crypto.common.ArrayTreeFactory
import jp.co.soramitsu.sora.crypto.common.Hash
import jp.co.soramitsu.sora.crypto.merkle.*
import spock.lang.Specification
import spock.lang.Unroll

import static jp.co.soramitsu.sora.crypto.common.Util.ceilToPowerOf2


class MerkleTreeTest extends Specification {

    def "tree is constructed with the correct size"() {
        given:
        leafs
        power

        when:
        def actual = ceilToPowerOf2(leafs)
        def fromleafs = ArrayTreeFactory.createWithNLeafs(leafs)
        def fromcap = ArrayTreeFactory.createWithCapacity(treesize)

        then:
        actual == power
        fromleafs.size() == treesize
        fromcap.size() == treesize

        where:
        leafs | power | treesize
        1     | 1     | 1
        2     | 2     | 3
        3     | 4     | 7
        4     | 4     | 7
        7     | 8     | 15
        8     | 8     | 15
    }

    def "valid merkle tree is created from leafs and from the full tree"() {
        given:
        def digest = new MockSumMessageDigest()
        def expTree = arr2list(expectedTree)

        MerkleTreeFactory factory = new MerkleTreeFactory(digest)

        when:
        MerkleTree tree = factory.createFromLeaves(arr2list(leafs) as List<Hash>)
        MerkleTree full = factory.createFromFullTree(tree.getHashTree())

        then:
        noExceptionThrown()

        tree.root() == expTree.get(0)
        full.root() == expTree.get(0)

        expTree.eachWithIndex { Hash entry, int i ->
            tree.getHashTree().get(i) == entry
            full.getHashTree().get(i) == entry
        }


        where:

        leafs                     | expectedTree
        [[1], [2], [3], [4], [5]] | [[15], [10], [5], [3], [7], [5], null, [1], [2], [3], [4], [5], null, null, null]
        [[1], [2], [3], [4]]      | [[10], [3], [7], [1], [2], [3], [4]]
        [[1], [2], [3]]           | [[6], [3], [3], [1], [2], [3], null]
        [[1], [2]]                | [[3], [1], [2]]
        [[1]]                     | [[1]]
    }

    @Unroll
    def "valid merkle proof is generated from valid merkle tree"() {
        given: 'merkle tree'
        def digest = new MockSumMessageDigest()

        MerkleTreeFactory mtfactory = new MerkleTreeFactory(digest)

        MerkleTree tree = mtfactory.createFromFullTree(
                new ArrayTree<Hash>(arr2list(arraytree))
        )

        when: 'prove that `target` is in the tree'
        Hash h = new Hash(target as byte[])
        MerkleTreeProof p = tree.createProof(h)

        then: 'proof is valid'
        p.verify(digest, new Hash([root] as byte[]))
        p.getPath() == arr2path(proof)

        where:
        a | b | c | d    | e     | f     | root  || target | arraytree                               | proof
        1 | 2 | 4 | 8    | a + b | c + d | e + f || [a]    | [[root], [e], [f], [a], [b], [c], [d]]  | [[3, a], [4, b], [2, f]]
        1 | 2 | 4 | 8    | a + b | c + d | e + f || [b]    | [[root], [e], [f], [a], [b], [c], [d]]  | [[4, b], [3, a], [2, f]]
        1 | 2 | 4 | 8    | a + b | c + d | e + f || [c]    | [[root], [e], [f], [a], [b], [c], [d]]  | [[5, c], [6, d], [1, e]]
        1 | 2 | 4 | 8    | a + b | c + d | e + f || [d]    | [[root], [e], [f], [a], [b], [c], [d]]  | [[6, d], [5, c], [1, e]]
        1 | 2 | 4 | 8    | a + b | c + d | e + f || [e]    | [[root], [e], [f], [a], [b], [c], [d]]  | [[1, e], [2, f]]
        1 | 2 | 4 | 8    | a + b | c + d | e + f || [f]    | [[root], [e], [f], [a], [b], [c], [d]]  | [[2, f], [1, e]]
        1 | 2 | 4 | 8    | a + b | c + d | e + f || [root] | [[root], [e], [f], [a], [b], [c], [d]]  | [[0, root]]
        1 | 2 | 4 | null | a + b | c     | e + f || [a]    | [[root], [e], [f], [a], [b], [c], null] | [[3, a], [4, b], [2, c]]
        1 | 2 | 4 | null | a + b | c     | e + f || [b]    | [[root], [e], [f], [a], [b], [c], null] | [[4, b], [3, a], [2, c]]
        1 | 2 | 4 | null | a + b | c     | e + f || [c]    | [[root], [e], [f], [a], [b], [c], null] | [[2, c], [1, e]]
        1 | 2 | 4 | null | a + b | c     | e + f || [e]    | [[root], [e], [f], [a], [b], [c], null] | [[1, e], [2, c]]
        1 | 2 | 4 | null | a + b | c     | e + f || [root] | [[root], [e], [f], [a], [b], [c], null] | [[0, root]]
    }

    ArrayList<Hash> arr2list(def arr) {
        ArrayList<Hash> list = new ArrayList<>(arr.size() as int)
        for (int i = 0; i < arr.size(); i++) {
            list.add(
                    i,
                    arr[i] == null ?
                            null :
                            new Hash(arr[i] as byte[])
            )
        }
        return list
    }

    List<MerkleNode> arr2path(def arr) {
        ArrayList<MerkleNode> nodes = new ArrayList<>(arr.size() as int)
        for (int i = 0; i < arr.size(); i++) {
            def index = arr[i][0]
            def element = arr[i][1]

            nodes.add(
                    new MerkleNode(
                            index as int,
                            new Hash([element] as byte[])
                    )
            )
        }

        return nodes
    }

}
