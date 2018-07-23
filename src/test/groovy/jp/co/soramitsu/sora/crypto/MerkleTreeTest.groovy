package jp.co.soramitsu.sora.crypto

import jp.co.soramitsu.sora.common.ArrayTreeFactory
import jp.co.soramitsu.sora.common.MockSumMessageDigest
import spock.lang.Specification

import static jp.co.soramitsu.sora.common.Util.ceilToPowerOf2

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
//        def digest = Mock(MessageDigest) {
//            digest([a, b] as byte[]) >> [e]
//            digest([c, d] as byte[]) >> [f]
//            digest([e, f] as byte[]) >> [root]
//            digest([e, c] as byte[]) >> [root]
//        }

        def digest = new MockSumMessageDigest()

        MerkleTreeFactory factory = new MerkleTreeFactory(digest)

        when:
        MerkleTree tree = factory.createFromLeafs(leafs as List<Hash>)
        MerkleTree full = factory.createFromFullTree(tree.getHashTree())

        then:
        noExceptionThrown()

        tree.root() == expectedTree.get(0)
        full.root() == expectedTree.get(0)

        tree.getHashTree().getTree() == expectedTree
        full.getHashTree().getTree() == expectedTree

        where:

        leafs << [
                arr2list([[1], [2], [3], [4], [5]]),
                arr2list([[1], [2], [3], [4]]),
                arr2list([[1], [2], [3]]),
                arr2list([[1], [2]]),
                arr2list([[1]]),
        ]

        expectedTree << [
                arr2list([[15], [10], [5], [3], [7], [5], null, [1], [2], [3], [4], [5], null, null, null]),
                arr2list([[10], [3], [7], [1], [2], [3], [4]]),
                arr2list([[6], [3], [3], [1], [2], [3], null]),
                arr2list([[3], [1], [2]]),
                arr2list([[1]])
        ]
    }

    /*
    def "valid merkle proof is generated from valid merkle tree"() {
        given:
        def algorithm = "algorithm"
        def digest = Mock(MessageDigest) {
            digest([a, b] as byte[]) >> [e]
            digest([c, d] as byte[]) >> [f]
            digest([e, f] as byte[]) >> [root]
            digest([e, c] as byte[]) >> [root]

            getAlgorithm() >> algorithm
        }

        MerkleTreeFactory mtfactory = new MerkleTreeFactory(digest)

        MerkleTree tree = mtfactory.createFromFullTree(
                new ArrayTree<Hash>(arr2list(arraytree))
        )

        when:
        Hash h = new Hash(proofsTarget as byte[])
        MerkleTreeProof p = tree.createProof(h)

        then:
        p.getHashAlgorithm() == algorithm
        p.getPath() == arr2path(proof)


        where:
        proofsTarget << [
                [a], [b], [c], [d], [e], [f], [root],  // even-sized tree
                [a], [b], [c], [e], [root],  // odd-sized tree
        ]

        proof << [
                // even
                [[3, a], [4, b], [2, f], [0, root]], // query a
                [[4, b], [3, a], [2, f], [0, root]], // query b
                [[5, c], [6, d], [1, e], [0, root]], // query c
                [[6, d], [5, c], [1, e], [0, root]], // query d
                [[1, e], [2, f], [0, root]],         // query e
                [[2, f], [1, e], [0, root]],         // query f
                [[0, root], [0, root]],              // query root

                // odd
                [[3, a], [4, b], [2, c], [0, root]], // query a
                [[4, b], [3, a], [2, c], [0, root]], // query b
                [[2, c], [1, e], [0, root]],         // query c
                [[1, e], [2, c], [0, root]],         // query e
                [[0, root], [0, root]]               // query root
        ]

        arraytree << [
                // even
                [[root], [e], [f], [a], [b], [c], [d]],
                [[root], [e], [f], [a], [b], [c], [d]],
                [[root], [e], [f], [a], [b], [c], [d]],
                [[root], [e], [f], [a], [b], [c], [d]],
                [[root], [e], [f], [a], [b], [c], [d]],
                [[root], [e], [f], [a], [b], [c], [d]],
                [[root], [e], [f], [a], [b], [c], [d]],

                // odd
                [[root], [e], [c], [a], [b], [c], null],
                [[root], [e], [c], [a], [b], [c], null],
                [[root], [e], [c], [a], [b], [c], null],
                [[root], [e], [c], [a], [b], [c], null],
                [[root], [e], [c], [a], [b], [c], null],
                [[root], [e], [c], [a], [b], [c], null]
        ]
    }
    */
    ArrayList<Hash> arr2list(def arr) {
        ArrayList<Hash> l = new ArrayList<>(arr.size() as int)
        for (int i = 0; i < arr.size(); i++) {
            l.add(i,
                    arr[i] == null ?
                            null :
                            new Hash(arr[i] as byte[])
            )
        }
        return l
    }

    /*
    List<MerkleNode> arr2path(def arr) {
        ArrayList<MerkleNode> nodes = new ArrayList<>(arr.size() as int)
        for (int i = 0; i < arr.size(); i++) {
            println(arr[i])
//            def index = arr[i][0]
//            def element = arr[i][1]
//
//            nodes.add(new MerkleNode(index as int, new Hash([element] as byte[])))
        }

        return nodes
    }

*/
}
