package jp.co.soramitsu.sora.sdk.did.model.dto.authentication;

public interface AuthenticationExecutor {

  void execute(Ed25519Sha3Authentication auth);
}
