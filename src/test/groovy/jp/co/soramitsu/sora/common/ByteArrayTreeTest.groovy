package jp.co.soramitsu.sora.common

import spock.lang.Specification


class ByteArrayTreeTest extends Specification {

    def "navigation works"() {
        expect:
        ByteArrayTree.getLeftChild(parent) == left
        ByteArrayTree.getRightChild(parent) == right
        ByteArrayTree.getParent(left) == parent
        ByteArrayTree.getParent(right) == parent

        ByteArrayTree.getParent(0) == -1

        where:
        left | right | parent
        1    | 2     | 0
        3    | 4     | 1
        5    | 6     | 2
        7    | 8     | 3
        9    | 10    | 4
        11   | 12    | 5
        13   | 14    | 6
    }

    def "find works"() {
        given:
        def tree = new ByteArrayTree(10)
        for (int i = 0; i < 10; i++) {
            tree.set(i, [i + 1] as byte[])
        }

        when:
        int actual = tree.find(el as byte[])

        then:
        actual == pos

        where:
        pos | el
        0   | [1]
        1   | [2]
        2   | [3]
        3   | [4]
        -1  | [155]
    }

}
