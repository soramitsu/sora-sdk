package jp.co.soramitsu.sora.crypto.json.flattener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

interface Token {

  Object getValue();

  JsonNode createNode(ObjectMapper mapper);

  KeyTypeEnum getType();

  void setValue(JsonNode parent, JsonNode value);
}





