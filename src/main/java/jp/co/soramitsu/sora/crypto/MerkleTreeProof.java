package jp.co.soramitsu.sora.crypto;

import static jp.co.soramitsu.sora.common.Util.allocateEmptyTree;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import jp.co.soramitsu.sora.common.ByteArrayTree;
import lombok.NonNull;
import lombok.Value;

@Value
public class MerkleTreeProof implements Serializable {

  @Value
  public static class Node implements Serializable {

    int position;
    byte[] hash;
  }

  // [element] [path...] [root]
  private List<Node> path;

  /**
   * <pre>
   * {@code
   * MessageDigest instance = MessageDigest.getInstance({@link #hashAlgorithm});
   * }
   * </pre>
   */
  String hashAlgorithm;


  /**
   * Verifies {@link MerkleTreeProof} path against supplied <code>root</code>
   */
  public boolean verify(@NonNull byte[] root) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);

    // 2^path.size()-1
    ByteArrayTree hashTree = allocateEmptyTree((1 << path.size()) - 1);

    path.forEach(n -> hashTree.set(n.getPosition(), n.getHash()));
    // TODO
    return false;
  }


  // TODO: test
  /**
   * Creates {@link MerkleTreeProof} path in <code>tree</code> for given <code>hash</code>
   *
   * @param tree tree that is used to calculate proof path
   * @param hash hash that should be proved to be inside merkle tree
   * @return {@link MerkleTreeProof} if <code>hash</code> is found in tree, {@code null} otherwise
   */
  public static MerkleTreeProof createFrom(final MerkleTree tree, final byte[] hash) {
    ByteArrayTree hashTree = tree.getHashTree();
    final int rootPosition = 0;

    LinkedList<Integer> stack = new LinkedList<>();
    stack.add(rootPosition); // start from root

    // preorder dfs tree traversal
    while (!stack.isEmpty()) {
      int nodepos = stack.removeFirst();

      if (Arrays.equals(hashTree.get(nodepos), hash)) {
        // found correct hash

        if(nodepos != rootPosition) {
          // [element] [path...] [root]
          stack.addFirst(nodepos); // element position
          stack.addLast(rootPosition);     // root
        }

        // given list of positions, create a list of path Nodes
        List<Node> path = stack
            .stream()
            .map(i -> new Node(i, hashTree.get(i)))
            .collect(Collectors.toList());

        return new MerkleTreeProof(
            path,
            tree.getDigest().getAlgorithm()
        );
      }

      int left = ByteArrayTree.getLeftChild(nodepos);
      int right = ByteArrayTree.getRightChild(nodepos);

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
}
