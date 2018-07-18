package jp.co.soramitsu.sora.crypto

import spock.lang.Specification

import java.security.MessageDigest


class MerkleTreeTest extends Specification {

    class MTree extends MerkleTree {
        /* to test protected methods */

        MTree(MessageDigest d) {
            super(d)
        }
    }

    def digest = Mock(MessageDigest)


    def "tree is constructed with the correct size"() {
        given:
        leafs
        power

        when:
        def actual = MerkleTree.ceilToPowerOf2(leafs)
        def tree = MerkleTree.newTree(leafs)

        then:
        actual == power
        tree.length == treesize

        where:
        leafs | power | treesize
        2     | 2     | 3
        3     | 4     | 7
        4     | 4     | 7
        7     | 8     | 15
        8     | 8     | 15
    }

    def "merkle tree works"() {
        given:
        def transactions = [
                [a] as byte[],
                [b] as byte[],
                [c] as byte[],
                [d] as byte[]
        ] as List<byte[]>

        digest.digest([a, b] as byte[]) >> [e]
        digest.digest([c, d] as byte[]) >> [f]
        digest.digest([e, f] as byte[]) >> [root]

        when:
        def tree = new MTree(digest)
        tree.create(transactions)

        then:
        tree.root() == [root] as byte[]
        tree.getTree() == [
                [root], [e], [f], [a], [b], [c], [d]
        ] as byte[][]

        where:
        a | b | c | d | e | f | root
        1 | 2 | 3 | 4 | 5 | 6 | 7
    }
}
