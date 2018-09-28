package jp.co.soramitsu.sora.sdk.crypto.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import jp.co.soramitsu.sora.sdk.crypto.common.Hash;
import jp.co.soramitsu.sora.sdk.crypto.common.HexdigestSaltGenerator;
import jp.co.soramitsu.sora.sdk.crypto.common.SaltGenerator;
import jp.co.soramitsu.sora.sdk.crypto.common.SecurityProvider;
import jp.co.soramitsu.sora.sdk.crypto.json.flattener.Flattener;
import jp.co.soramitsu.sora.sdk.crypto.merkle.MerkleTree;
import jp.co.soramitsu.sora.sdk.crypto.merkle.MerkleTreeFactory;
import jp.co.soramitsu.sora.sdk.crypto.merkle.MerkleTreeProof;
import jp.co.soramitsu.sora.sdk.did.model.type.DigestTypeEnum;
import jp.co.soramitsu.sora.sdk.json.JsonUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class SelectiveDisclosureFactory {

  private final ObjectMapper mapper;
  private final Flattener flattener;
  private final Saltifier saltifier;
  private final Hashifier hashifier;
  private final MerkleTreeFactory merkleTreeFactory;

  public SelectiveDisclosureFactory() {
    MessageDigest digest = new SecurityProvider().getMessageDigest(DigestTypeEnum.SHA3_256);
    SaltGenerator generator = new HexdigestSaltGenerator();

    this.mapper = JsonUtil.buildMapper();
    this.flattener = new Flattener();
    this.hashifier = new Hashifier(digest);
    this.saltifier = new Saltifier(mapper, generator);
    this.merkleTreeFactory = new MerkleTreeFactory(digest);
  }

  /**
   * Create cryptographic commitment for given raw input.
   *
   * @param input can be a Map<String, Object>, POJO, JsonNode; anything that {@link ObjectMapper}
   *     can process
   * @return single selective disclosure item, which is used to create cryptographic proofs
   * @throws IOException is thrown when {@link ObjectMapper} can not process input JSON
   */
  public SelectiveDisclosureItem createCommitment(Object input) throws IOException {

    JsonNode json = mapper.valueToTree(input);
    if (!json.isObject()) {
      throw new NotJsonObjectException(json);
    }

    return getSelectiveDisclosureItemFromSaltified(getSaltified((ObjectNode) json));
  }

  /**
   * Create cryptographic commitment for given prepared (saltify and flatten) input.
   *
   * @param inputNode ObjectNode, JsonNode; anything that {@link ObjectMapper} can process
   * @return single selective disclosure item, which is used to create cryptographic proofs
   * @throws IOException is thrown when {@link ObjectMapper} can not process input JSON
   */
  public SelectiveDisclosureItem createCommitmentFromSaltified(ObjectNode inputNode)
      throws IOException {
    return getSelectiveDisclosureItemFromSaltified(inputNode);
  }

  /**
   * Create cryptographic commitment, when tree and json are read from a disk.
   *
   * @param merkleTree valid merkle tree (validity is not checked)
   * @param saltifiedJson saltified json (validity is not checked)
   * @return selective disclosure item
   */
  public SelectiveDisclosureItem createCommitment(MerkleTree merkleTree, ObjectNode saltifiedJson) {
    return new SelectiveDisclosureItem(merkleTree, saltifiedJson);
  }

  private ObjectNode getSaltified(ObjectNode jsonNodes) {
    ObjectNode flattened = flattener.flatten(jsonNodes);
    return (ObjectNode) saltifier.saltify(flattened);
  }

  private SelectiveDisclosureItem getSelectiveDisclosureItemFromSaltified(ObjectNode saltified)
      throws IOException {
    List<Hash> hashes = hashifier.hashify(saltified);
    MerkleTree tree = merkleTreeFactory.createFromLeaves(hashes);

    return new SelectiveDisclosureItem(tree, saltified);
  }

  /**
   * An object, which stores state, enough to create cryptographic proofs for given JSON. JSON is
   * stored as saltified JSON.
   */
  @RequiredArgsConstructor
  @Getter
  public class SelectiveDisclosureItem {

    private final MerkleTree merkleTree;
    private final ObjectNode saltifiedJson;

    /**
     * Getter for the merkle root.
     *
     * @return hash
     */
    public Hash getCommitment() {
      return merkleTree.getRoot();
    }

    /**
     * Creates a cryptographic proof for given key in JSON (key from flattened JSON), which can be
     * resolved (validated) in TRUE or FALSE
     */
    public MerkleTreeProof createProofForKey(String key) throws IOException {
      Hash hash = hashifier.hashJsonField(new SimpleEntry<>(key, saltifiedJson.get(key)));

      return createProof(hash);
    }

    public MerkleTreeProof createProof(Hash hash) {
      return merkleTree.createProof(hash);
    }

    /** Getter for the original JSON, without salt and without flat keys. */
    public ObjectNode getOriginalJson() {
      ObjectNode node = (ObjectNode) saltifier.desaltify(saltifiedJson);
      return flattener.deflatten(node);
    }

    /**
     * Getter for all keys, affected (which are "hang") by given hash (or list of hashes).
     *
     * @param hashes list of hashes
     * @return list of keys in salted JSON
     * @throws IOException is thrown when JSON can not be processed
     */
    public Collection<String> getAffectedKeys(List<Hash> hashes) throws IOException {
      Set<Hash> affectedLeafHashes = new HashSet<>();
      val hashTree = merkleTree.getHashTree();

      hashes.forEach(
          h ->
              affectedLeafHashes.addAll(
                  merkleTree
                      .getLeavesIndicesBelowHash(
                          h) // get indices of leafs, affected by current hash
                      .stream()
                      .map(hashTree::get) // return only affected leafs
                      .collect(Collectors.toSet())));

      return hashifier.dehashify(affectedLeafHashes, saltifiedJson).values();
    }

    public Collection<String> getAffectedKeys(Hash hash) throws IOException {
      return getAffectedKeys(Collections.singletonList(hash));
    }
  }
}
