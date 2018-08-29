/* -----------------------------------------------------------------------------
 * Rule_did_path.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.5
 * Produced : Sat Aug 25 23:27:45 EEST 2018
 *
 * -----------------------------------------------------------------------------
 */

package jp.co.soramitsu.sora.sdk.did.parser.generated;

import java.util.ArrayList;

final public class Rule_did_path extends Rule {

  public Rule_did_path(String spelling, ArrayList<Rule> rules) {
    super(spelling, rules);
  }

  public static Rule_did_path parse(ParserContext context) {
    context.push("did-path");

    boolean parsed = true;
    int s0 = context.index;
    ParserAlternative a0 = new ParserAlternative(s0);

    ArrayList<ParserAlternative> as1 = new ArrayList<ParserAlternative>();
    parsed = false;
    {
      int s1 = context.index;
      ParserAlternative a1 = new ParserAlternative(s1);
      parsed = true;
      if (parsed) {
        boolean f1 = true;
        int c1 = 0;
        for (int i1 = 0; i1 < 1 && f1; i1++) {
          Rule rule = Rule_path_abempty.parse(context);
          if ((f1 = rule != null)) {
            a1.add(rule, context.index);
            c1++;
          }
        }
        parsed = c1 == 1;
      }
      if (parsed) {
        as1.add(a1);
      }
      context.index = s1;
    }
    {
      int s1 = context.index;
      ParserAlternative a1 = new ParserAlternative(s1);
      parsed = true;
      if (parsed) {
        boolean f1 = true;
        int c1 = 0;
        for (int i1 = 0; i1 < 1 && f1; i1++) {
          Rule rule = Rule_path_absolute.parse(context);
          if ((f1 = rule != null)) {
            a1.add(rule, context.index);
            c1++;
          }
        }
        parsed = c1 == 1;
      }
      if (parsed) {
        as1.add(a1);
      }
      context.index = s1;
    }
    {
      int s1 = context.index;
      ParserAlternative a1 = new ParserAlternative(s1);
      parsed = true;
      if (parsed) {
        boolean f1 = true;
        int c1 = 0;
        for (int i1 = 0; i1 < 1 && f1; i1++) {
          Rule rule = Rule_path_noscheme.parse(context);
          if ((f1 = rule != null)) {
            a1.add(rule, context.index);
            c1++;
          }
        }
        parsed = c1 == 1;
      }
      if (parsed) {
        as1.add(a1);
      }
      context.index = s1;
    }
    {
      int s1 = context.index;
      ParserAlternative a1 = new ParserAlternative(s1);
      parsed = true;
      if (parsed) {
        boolean f1 = true;
        int c1 = 0;
        for (int i1 = 0; i1 < 1 && f1; i1++) {
          Rule rule = Rule_path_rootless.parse(context);
          if ((f1 = rule != null)) {
            a1.add(rule, context.index);
            c1++;
          }
        }
        parsed = c1 == 1;
      }
      if (parsed) {
        as1.add(a1);
      }
      context.index = s1;
    }
    {
      int s1 = context.index;
      ParserAlternative a1 = new ParserAlternative(s1);
      parsed = true;
      if (parsed) {
        boolean f1 = true;
        int c1 = 0;
        for (int i1 = 0; i1 < 1 && f1; i1++) {
          Rule rule = Rule_path_empty.parse(context);
          if ((f1 = rule != null)) {
            a1.add(rule, context.index);
            c1++;
          }
        }
        parsed = c1 == 1;
      }
      if (parsed) {
        as1.add(a1);
      }
      context.index = s1;
    }

    ParserAlternative b = ParserAlternative.getBest(as1);

    parsed = b != null;

    if (parsed) {
      a0.add(b.rules, b.end);
      context.index = b.end;
    }

    Rule rule = null;
    if (parsed) {
      rule = new Rule_did_path(context.text.substring(a0.start, a0.end), a0.rules);
    } else {
      context.index = s0;
    }

    context.pop("did-path", parsed);

    return (Rule_did_path) rule;
  }

  public Object accept(Visitor visitor) {
    return visitor.visit(this);
  }
}

/* -----------------------------------------------------------------------------
 * eof
 * -----------------------------------------------------------------------------
 */
