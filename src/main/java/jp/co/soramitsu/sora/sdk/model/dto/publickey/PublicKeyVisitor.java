package jp.co.soramitsu.sora.sdk.model.dto.publickey;

public interface PublicKeyVisitor {

  void visit(Ed25519Sha3VerificationKey pub);
}
