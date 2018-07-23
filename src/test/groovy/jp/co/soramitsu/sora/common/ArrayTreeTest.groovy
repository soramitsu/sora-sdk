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
        ArrayTree.getNeighbor(0) == 0
        ArrayTree.getNeighbor(-1) == -1
        ArrayTree.getNeighbor(left) == right
        ArrayTree.getNeighbor(right) == left

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
        def list = [1, 2, 3, 4, 4, 3] as List<Integer>
        ArrayTree<Integer> tree = ArrayTreeFactory.createFromNodes(list)

        when:
        def pos = tree.find(item)

        then:
        pos == position

        where:
        item | position
        1    | 0
        2    | 1
        3    | 2
        4    | 3
        5    | -1
    }

    def "creation of a tree with invalid number of leafs throws"() {
        when:
        ArrayTreeFactory.createWithNLeafs(0)

        then:
        thrown(InvalidLeafNumberException)
    }
}
