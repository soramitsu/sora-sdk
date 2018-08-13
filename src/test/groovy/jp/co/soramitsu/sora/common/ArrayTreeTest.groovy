package jp.co.soramitsu.sora.common

import jp.co.soramitsu.sora.crypto.common.ArrayTree
import jp.co.soramitsu.sora.crypto.common.ArrayTreeFactory
import jp.co.soramitsu.sora.crypto.common.InvalidNodeNumberException
import spock.lang.Specification


class ArrayTreeTest extends Specification {

    def "navigation works"() {
        expect:
        ArrayTree.getLeftChildIndex(parent) == left
        ArrayTree.getRightChildIndex(parent) == right
        ArrayTree.getParentIndex(left) == parent
        ArrayTree.getParentIndex(right) == parent
        ArrayTree.getParentIndex(0) == -1
        ArrayTree.getNeighborIndex(0) == 0
        ArrayTree.getNeighborIndex(-1) == -1
        ArrayTree.getNeighborIndex(left) == right
        ArrayTree.getNeighborIndex(right) == left

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

    def "indexOf works"() {
        given:
        def list = [1, 2, 3, 4, 4, 3, 2] as List<Integer>
        ArrayTree<Integer> tree = ArrayTreeFactory.createFromNodes(list)

        when:
        def pos = tree.indexOf(item)

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
        thrown(InvalidNodeNumberException)
    }
}
