package jp.co.soramitsu.sora.sdk.crypto.json.flattener;

import java.util.function.Consumer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LambdaDeflattenVisitor implements DeflattenVisitor {

  private Consumer<String> dictLambda;
  private Consumer<Integer> arrayLambda;


  @Override
  public void visitArrayKey(Integer index) {
    arrayLambda.accept(index);
  }

  @Override
  public void visitDictKey(String key) {
    dictLambda.accept(key);
  }
}
