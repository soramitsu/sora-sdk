package jp.co.soramitsu.sora.sdk.did.parser;

import java.util.List;
import jp.co.soramitsu.sora.sdk.did.model.dto.DID;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Parser;
import jp.co.soramitsu.sora.sdk.did.parser.generated.ParserException;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_ALPHA;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_CHAR;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_DIGIT;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_HEXDIG;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_did;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_did_fragment;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_did_path;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_did_reference;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_gen_delims;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_idchar;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_idstring;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_method;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_methodchar;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_path_abempty;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_path_absolute;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_path_empty;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_path_noscheme;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_path_rootless;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_pchar;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_pct_encoded;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_reserved;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_scheme;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_segment;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_segment_nz;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_segment_nz_nc;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_specific_idstring;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_sub_delims;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Rule_unreserved;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Terminal_NumericValue;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Terminal_StringValue;
import jp.co.soramitsu.sora.sdk.did.parser.generated.Visitor;
import lombok.val;

public class DIDVisitor implements Visitor {

  private DID.DIDBuilder builder = DID.builder();

  public static DID parse(String did, String topLevelRule) throws ParserException {
    Rule rule = Parser.parse(topLevelRule, did);
    DIDVisitor visitor = new DIDVisitor();
    rule.accept(visitor);
    return visitor.build();
  }

  private void visitRules(List<Rule> rules) {
    for (val rule : rules) {
      rule.accept(this);
    }
  }

  public DID build() throws ParserException {
    return builder.build();
  }

  @Override
  public Object visit(Rule_did rule) {
    visitRules(rule.rules);
    return null;
  }

  @Override
  public Object visit(Rule_method rule) {
    builder.method(rule.spelling);
    return null;
  }

  @Override
  public Object visit(Rule_methodchar rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_specific_idstring rule) {
    visitRules(rule.rules);
    return null;
  }

  @Override
  public Object visit(Rule_idstring rule) {
    builder.identifier(rule.spelling);
    return null;
  }

  @Override
  public Object visit(Rule_idchar rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_did_reference rule) {
    visitRules(rule.rules);
    return null;
  }

  @Override
  public Object visit(Rule_scheme rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_did_fragment rule) {
    builder.fragment(rule.spelling);
    return null;
  }

  @Override
  public Object visit(Rule_did_path rule) {
    builder.path(rule.spelling);
    return null;
  }

  @Override
  public Object visit(Rule_path_abempty rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_path_absolute rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_path_noscheme rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_path_rootless rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_path_empty rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_segment rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_segment_nz rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_segment_nz_nc rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_pchar rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_pct_encoded rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_unreserved rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_reserved rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_gen_delims rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_sub_delims rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_ALPHA rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_HEXDIG rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_DIGIT rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Rule_CHAR rule) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Terminal_StringValue value) {
    /* ignore */
    return null;
  }

  @Override
  public Object visit(Terminal_NumericValue value) {
    /* ignore */
    return null;
  }
}
