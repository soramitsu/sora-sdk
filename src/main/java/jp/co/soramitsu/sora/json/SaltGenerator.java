package jp.co.soramitsu.sora.json;

public interface SaltGenerator {

  /**
   * Get next salt represented as string.
   * @return salt
   */
  String next();
}
