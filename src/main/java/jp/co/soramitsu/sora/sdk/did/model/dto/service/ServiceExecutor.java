package jp.co.soramitsu.sora.sdk.did.model.dto.service;

import jp.co.soramitsu.sora.sdk.did.model.dto.Service;

public interface ServiceExecutor<T extends Service> {

  void execute(T service);
}
