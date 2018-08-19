package jp.co.soramitsu.sora.crypto.merkle;

import static jp.co.soramitsu.sora.crypto.common.ArrayTree.getNeighborIndex;
import static jp.co.soramitsu.sora.crypto.common.ArrayTree.getParentIndex;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import jp.co.soramitsu.sora.crypto.common.ArrayTree;
import jp.co.soramitsu.sora.crypto.common.Hash;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Getter
public class MerkleTree {

  private final MessageDigest digest;

  /**
   * This is binary hashTree and it is stored "by levels", so first level is root and stored as
   * tree[0], next level (2 items) is stored in tree[1] and tree[2], and so on.
   *
   * Scheme: [root] [intermediate nodes] [leaves]
   */
  private final ArrayTree<Hash> hashTree;

  /**
   * @return merkle root hash
   */
  public Hash getRoot() {
    return hashTree.get(0);
  }

  /**
   * Creates {@link MerkleTreeProof} path in the tree for given <code>hash</code>
   *
   * @param hash hash that should be proved to be inside merkle tree
   * @return {@link MerkleTreeProof} if <code>hash</code> is found in tree, {@code null} otherwise
   * @throws InvalidMerkleTreeException if required element is not in the tree
   */
  public MerkleTreeProof createProof(final Hash hash) {
    final int rootPosition = 0;

    int pos = hashTree.indexOf(hash);
    if (pos < 0) {
      return null;
    }

    LinkedList<Integer> stack = new LinkedList<>();
    stack.addFirst(pos);
    while (pos != rootPosition) {
      int neighbour = getNeighborIndex(pos);
      if (!hashTree.hasNodeAt(neighbour)) {
        throw new InvalidMerkleTreeException(neighbour);
      }
      stack.addLast(neighbour);
      pos = getParentIndex(pos);
    }

    // given list of positions, create a list of path Nodes
    List<MerkleNode> path = stack
        .stream()
        .map(i -> new MerkleNode(i, hashTree.get(i)))
        .collect(Collectors.toList());

    return new MerkleTreeProof(digest, path);
  }


  public List<Integer> getLeavesIndicesBelowHash(Hash hash) {
    return getLeavesIndicesBelowIndex(hashTree.indexOf(hash));
  }

  /**
   * Given element index, returns indices of leafs, which are located ("hanged") under given index
   *
   * Example:
   * <pre>
   *    0
   *  1   2
   * 3 4 5 -
   * </pre>
   *
   * <code>
   * <pre>
   * getLeavesIndicesBelowIndex(0) -> [3, 4, 5];
   * getLeavesIndicesBelowIndex(1) -> [3, 4];
   * getLeavesIndicesBelowIndex(2) -> [5];
   * getLeavesIndicesBelowIndex(3) -> [3];
   * </pre>
   * </code>
   */
  public List<Integer> getLeavesIndicesBelowIndex(int index) {
    if (index < 0 || index >= hashTree.size()) {
      return Collections.emptyList();  // not found, no leafs affected
    }

    List<Integer> result = new ArrayList<>();
    hashTree.preorderTraversal(index, (i, h) -> {
      if (hashTree.isLeaf(i) && Objects.nonNull(h)) {
        result.add(i);
      }
    });

    return result;
  }
}

