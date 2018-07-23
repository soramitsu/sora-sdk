package jp.co.soramitsu.sora.crypto;

import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import jp.co.soramitsu.sora.common.ArrayTree;
import lombok.Value;

@Value
public class MerkleTree {

  MessageDigest digest;

  /**
   * This is binary hashTree and it is stored "by levels", so first level is root and stored as hashTree[0],
   * next level (2 items) is stored in hashTree[1] and hashTree[2], and so on.
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
   */
  public MerkleTreeProof createProof(final Hash hash) {
    final int rootPosition = 0;

    LinkedList<Integer> stack = new LinkedList<>();
    stack.add(rootPosition); // start from root

    // preorder dfs tree traversal
    while (!stack.isEmpty()) {
      int nodepos = stack.removeFirst();

      if (hashTree.get(nodepos).equals(hash)) {
        // found correct hash

        if(nodepos != rootPosition) {
          // [element] [path...] [root]
          stack.addFirst(nodepos); // element position
          stack.addLast(rootPosition);     // root
        }

        // given list of positions, create a list of path Nodes
        List<MerkleNode> path = stack
            .stream()
            .map(i -> new MerkleNode(i, hashTree.get(i)))
            .collect(Collectors.toList());

        return new MerkleTreeProof(
            path,
            digest.getAlgorithm()
        );
      }

      int left = ArrayTree.getLeftChild(nodepos);
      int right = ArrayTree.getRightChild(nodepos);

      // visit right
      if (hashTree.hasElement(right)) {
        stack.addFirst(right);
      }

      // visit left
      if (hashTree.hasElement(left)) {
        stack.addFirst(left);
      }
    }

    return null;
  }

  @Override
  public String toString(){
    return hashTree.toString();
  }
}

