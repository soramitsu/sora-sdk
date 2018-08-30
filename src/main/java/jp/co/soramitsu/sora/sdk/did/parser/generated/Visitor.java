/* -----------------------------------------------------------------------------
 * Visitor.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.5
 * Produced : Sat Aug 25 23:27:45 EEST 2018
 *
 * -----------------------------------------------------------------------------
 */

package jp.co.soramitsu.sora.sdk.did.parser.generated;

public interface Visitor {

  public Object visit(Rule_did_reference rule);

  public Object visit(Rule_scheme rule);

  public Object visit(Rule_did rule);

  public Object visit(Rule_method rule);

  public Object visit(Rule_methodchar rule);

  public Object visit(Rule_specific_idstring rule);

  public Object visit(Rule_idstring rule);

  public Object visit(Rule_idchar rule);

  public Object visit(Rule_did_fragment rule);

  public Object visit(Rule_did_path rule);

  public Object visit(Rule_path_abempty rule);

  public Object visit(Rule_path_absolute rule);

  public Object visit(Rule_path_noscheme rule);

  public Object visit(Rule_path_rootless rule);

  public Object visit(Rule_path_empty rule);

  public Object visit(Rule_segment rule);

  public Object visit(Rule_segment_nz rule);

  public Object visit(Rule_segment_nz_nc rule);

  public Object visit(Rule_pchar rule);

  public Object visit(Rule_pct_encoded rule);

  public Object visit(Rule_unreserved rule);

  public Object visit(Rule_reserved rule);

  public Object visit(Rule_gen_delims rule);

  public Object visit(Rule_sub_delims rule);

  public Object visit(Rule_ALPHA rule);

  public Object visit(Rule_HEXDIG rule);

  public Object visit(Rule_DIGIT rule);

  public Object visit(Rule_CHAR rule);

  public Object visit(Terminal_StringValue value);

  public Object visit(Terminal_NumericValue value);
}

/* -----------------------------------------------------------------------------
 * eof
 * -----------------------------------------------------------------------------
 */
