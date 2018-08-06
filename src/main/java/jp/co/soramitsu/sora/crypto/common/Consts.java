package jp.co.soramitsu.sora.crypto.common;


import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

@UtilityClass
@FieldDefaults(makeFinal = true)
public class Consts {

  public static String PROOF_KEY = "proof";
  public static String ED25519_SHA3_SIGNATURE = "Ed25519Sha3Signature";
  public static String ED25519_SHA3_VERIFICATION_KEY = "Ed25519Sha3VerificationKey";
  public static String SHA3_256 = "SHA3-256";
  public static String SHA3_512 = "SHA3-512";
}
