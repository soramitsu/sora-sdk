package jp.co.soramitsu.sora.crypto

import jp.co.soramitsu.sora.common.ByteArrayTree
import spock.lang.Specification

import java.security.MessageDigest

import static jp.co.soramitsu.sora.common.Util.allocateEmptyTree
import static jp.co.soramitsu.sora.common.Util.ceilToPowerOf2

class MerkleTreeTest extends Specification {


    def "tree is constructed with the correct size"() {
        given:
        leafs
        power

        when:
        def actual = ceilToPowerOf2(leafs)
        def tree = allocateEmptyTree(leafs)

        then:
        actual == power
        tree.size() == treesize

        where:
        leafs | power | treesize
        1     | 1     | 1
        2     | 2     | 3
        3     | 4     | 7
        4     | 4     | 7
        7     | 8     | 15
        8     | 8     | 15
    }


    byte[][] bytes(def array) {
        byte[][] b = new byte[array.size()][]
        for (int i = 0; i < b.length; i++) {
            b[i] = array[i] as byte[]
        }
        return b
    }

    List<byte[]> list(def array) {
        List<byte[]> l = new ArrayList<>(array.size());
        for (int i = 0; i < array.size(); i++) {
            l.add(i, array[i] as byte[])
        }
        return l
    }


    def "valid merkle tree is created from leafs and from the full tree"() {
        given:
        def digest = Mock(MessageDigest)

        // for all
        digest.digest([a, b] as byte[]) >> [e]
        digest.digest([c, d] as byte[]) >> [f]

        // for 4 leafs
        digest.digest([e, f] as byte[]) >> [root]

        // for 3 leafs
        digest.digest([e, c] as byte[]) >> [root]

        MerkleTreeFactory factory = new MerkleTreeFactory(digest)

        when:
        MerkleTree tree = factory.createFromLeafs(leafs as List<byte[]>)
        MerkleTree full = factory.createFromFullTree(tree.getHashTree())

        then:
        noExceptionThrown()
        tree.root() == [root] as byte[]
        tree.getHashTree().getTree() == expectedTree as byte[][]
        full.root() == [root] as byte[]
        full.getHashTree().getTree() == expectedTree as byte[][]

        where:
        a | b | c | d | e | f | root
        1 | 2 | 3 | 4 | 5 | 6 | 7
        1 | 2 | 3 | 4 | 5 | 6 | 7
        1 | 2 | 3 | 4 | 5 | 6 | 7
        1 | 2 | 3 | 4 | 5 | 6 | 7

        leafs << [
                list([[a], [b], [c], [d]]),
                list([[a], [b], [c]]),
                list([[e], [f]]),  // e+f = root
                list([[root]])     // single leaf hashTree = root

        ]

        expectedTree << [
                bytes([[root], [e], [f], [a], [b], [c], [d]]),
                bytes([[root], [e], [c], [a], [b], [c], null]),
                bytes([[root], [e], [f]]),
                bytes([[root]])
        ]
    }

}
