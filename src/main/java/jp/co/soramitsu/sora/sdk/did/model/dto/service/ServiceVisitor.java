package jp.co.soramitsu.sora.sdk.did.model.dto.service;

public interface ServiceVisitor {

  void visit(GenericService service);

  void visit(SharingRulesService service);
}
