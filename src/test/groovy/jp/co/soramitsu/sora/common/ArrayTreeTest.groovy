package jp.co.soramitsu.sora.common

import spock.lang.Specification


class ArrayTreeTest extends Specification {

    def "navigation works"() {
        expect:
        ArrayTree.getLeftChild(parent) == left
        ArrayTree.getRightChild(parent) == right
        ArrayTree.getParent(left) == parent
        ArrayTree.getParent(right) == parent
        ArrayTree.getParent(0) == -1

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
}
