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


    def "getTreeSize works"() {
        given:
        leafs
        size

        when:
        def actual = MerkleTree.getTreeSize(leafs)

        then:
        actual == size

        where:
        leafs | size
        1     | 1
        2     | 2
        3     | 4
        4     | 4
        7     | 8
        8     | 8
    }

    def "newTree allocates the tree"() {
        given:
        leafs
        size

        when:
        def tree = MerkleTree.newTree(leafs)

        then:
        tree.length == size

        where:
        leafs | size
        1     | 1
        2     | 2
        3     | 4
        4     | 4
        5     | 8
        8     | 8

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

        where:
        a | b | c | d | e | f | root
        1 | 2 | 3 | 4 | 5 | 6 | 7
    }
}
