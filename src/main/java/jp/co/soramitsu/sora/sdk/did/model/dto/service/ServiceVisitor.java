package jp.co.soramitsu.sora.sdk.did.model.dto.service;

public interface ServiceVisitor {

  default void visit(GenericService service) {
  }

  default void visit(SharingRulesService service) {
  }

  default void visit(TransferDataService service) {
  }
}
