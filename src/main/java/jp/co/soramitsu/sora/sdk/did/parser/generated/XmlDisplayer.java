/* -----------------------------------------------------------------------------
 * XmlDisplayer.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.5
 * Produced : Sat Aug 25 23:27:45 EEST 2018
 *
 * -----------------------------------------------------------------------------
 */

package jp.co.soramitsu.sora.sdk.did.parser.generated;

import java.util.ArrayList;

public class XmlDisplayer implements Visitor {

  private boolean terminal = true;

  public Object visit(Rule_did_reference rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<did-reference>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</did-reference>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_scheme rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<scheme>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</scheme>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_did rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<did>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</did>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_method rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<method>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</method>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_methodchar rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<methodchar>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</methodchar>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_specific_idstring rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<specific-idstring>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</specific-idstring>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_idstring rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<idstring>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</idstring>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_idchar rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<idchar>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</idchar>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_did_fragment rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<did-fragment>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</did-fragment>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_did_path rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<did-path>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</did-path>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_path_abempty rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<path-abempty>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</path-abempty>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_path_absolute rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<path-absolute>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</path-absolute>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_path_noscheme rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<path-noscheme>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</path-noscheme>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_path_rootless rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<path-rootless>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</path-rootless>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_path_empty rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<path-empty>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</path-empty>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_segment rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<segment>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</segment>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_segment_nz rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<segment-nz>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</segment-nz>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_segment_nz_nc rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<segment-nz-nc>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</segment-nz-nc>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_pchar rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<pchar>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</pchar>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_pct_encoded rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<pct-encoded>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</pct-encoded>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_unreserved rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<unreserved>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</unreserved>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_reserved rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<reserved>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</reserved>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_gen_delims rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<gen-delims>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</gen-delims>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_sub_delims rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<sub-delims>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</sub-delims>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_ALPHA rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<ALPHA>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</ALPHA>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_HEXDIG rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<HEXDIG>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</HEXDIG>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_DIGIT rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<DIGIT>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</DIGIT>");
    terminal = false;
    return null;
  }

  public Object visit(Rule_CHAR rule) {
    if (!terminal) {
      System.out.println();
    }
    System.out.print("<CHAR>");
    terminal = false;
    visitRules(rule.rules);
    if (!terminal) {
      System.out.println();
    }
    System.out.print("</CHAR>");
    terminal = false;
    return null;
  }

  public Object visit(Terminal_StringValue value) {
    System.out.print(value.spelling);
    terminal = true;
    return null;
  }

  public Object visit(Terminal_NumericValue value) {
    System.out.print(value.spelling);
    terminal = true;
    return null;
  }

  private Boolean visitRules(ArrayList<Rule> rules) {
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
