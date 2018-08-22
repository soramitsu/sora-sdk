package jp.co.soramitsu.sora.crypto.json.flattener;

public class DeflattenException extends RuntimeException {

  public DeflattenException(Exception e) {
    super(e);
  }

  public DeflattenException(String msg){
    super(msg);
  }
}
