package jp.co.soramitsu.sora.sdk.did.model.dto.authentication;

public interface AuthenticationVisitor {

  void visit(Ed25519Sha3Authentication auth);
}
