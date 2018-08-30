/* -----------------------------------------------------------------------------
 * Displayer.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.5
 * Produced : Sat Aug 25 23:27:45 EEST 2018
 *
 * -----------------------------------------------------------------------------
 */

package jp.co.soramitsu.sora.sdk.did.parser.generated;

import java.util.ArrayList;

public class Displayer implements Visitor {

  public Object visit(Rule_did_reference rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_scheme rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_did rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_method rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_methodchar rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_specific_idstring rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_idstring rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_idchar rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_did_fragment rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_did_path rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_path_abempty rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_path_absolute rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_path_noscheme rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_path_rootless rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_path_empty rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_segment rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_segment_nz rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_segment_nz_nc rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_pchar rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_pct_encoded rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_unreserved rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_reserved rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_gen_delims rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_sub_delims rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_ALPHA rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_HEXDIG rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_DIGIT rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Rule_CHAR rule) {
    return visitRules(rule.rules);
  }

  public Object visit(Terminal_StringValue value) {
    System.out.print(value.spelling);
    return null;
  }

  public Object visit(Terminal_NumericValue value) {
    System.out.print(value.spelling);
    return null;
  }

  private Object visitRules(ArrayList<Rule> rules) {
    for (Rule rule : rules) {
      rule.accept(this);
    }
    return null;
  }
}

/* -----------------------------------------------------------------------------
 * eof
 * -----------------------------------------------------------------------------
 */
