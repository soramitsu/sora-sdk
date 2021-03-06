package jp.co.soramitsu.sora.sdk.crypto.common;


import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

@UtilityClass
@FieldDefaults(makeFinal = true)
public class Consts {

  public String PROOF_KEY = "proof";
  public String ED25519_SHA3_SIGNATURE = "Ed25519Sha3Signature";
  public String SHA3_256 = "SHA3-256";
  public String SHA3_512 = "SHA3-512";
}
