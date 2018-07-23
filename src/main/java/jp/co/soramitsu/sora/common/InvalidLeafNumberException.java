package jp.co.soramitsu.sora.common;

public class InvalidLeafNumberException extends RuntimeException {
  public InvalidLeafNumberException(int leafs){
    super("Number of leafs must be positive " + leafs);
  }
}
