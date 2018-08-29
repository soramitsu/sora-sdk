package jp.co.soramitsu.sora.crypto.json.flattener;

public interface DeflattenVisitor {

  /**
   * Triggered when parser finds next array key.
   *
   * @param index - index in array that is found
   */
  void visitArrayKey(Integer index);

  /**
   * Triggered when parser finds next dict key.
   *
   * @param key - dict key that is found
   */
  void visitDictKey(String key);
}
