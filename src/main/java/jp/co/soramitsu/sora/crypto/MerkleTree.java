package jp.co.soramitsu.sora.crypto;

import static jp.co.soramitsu.sora.common.ArrayTree.getNeighborIndex;
import static jp.co.soramitsu.sora.common.ArrayTree.getParentIndex;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import jp.co.soramitsu.sora.common.ArrayTree;
import lombok.Value;

@Value
public class MerkleTree {

  /**
   * This is binary hashTree and it is stored "by levels", so first level is root and stored as
   * tree[0], next level (2 items) is stored in tree[1] and tree[2], and so on.
   *
   * Scheme: [root] [intermediate nodes] [leafs]
   */
  ArrayTree<Hash> hashTree;

  /**
   * @return merkle root hash
   */
  public Hash root() {
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

    return new MerkleTreeProof(path);
  }
}

