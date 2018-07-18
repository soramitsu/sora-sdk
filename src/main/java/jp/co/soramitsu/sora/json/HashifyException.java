package jp.co.soramitsu.sora.json;

public class HashifyException extends RuntimeException {

  public HashifyException(String s) {
    super(s);
  }

  public HashifyException(Exception e){
    super(e);
  }

}
