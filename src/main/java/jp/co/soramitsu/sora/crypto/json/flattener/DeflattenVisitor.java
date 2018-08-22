package jp.co.soramitsu.sora.crypto.json.flattener;

public interface DeflattenVisitor {
  void visitArrayKey(Integer index);
  void visitDictKey(String key);
}
