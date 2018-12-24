package jp.co.soramitsu.sora.sdk.did.model.dto.publickey;

public interface PublicKeyExecutor {

  void execute(Ed25519Sha3VerificationKey pub);
}
