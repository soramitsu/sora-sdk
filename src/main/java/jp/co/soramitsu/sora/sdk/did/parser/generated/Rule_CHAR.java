/* -----------------------------------------------------------------------------
 * Rule_CHAR.java
 * -----------------------------------------------------------------------------
 *
 * Producer : com.parse2.aparse.Parser 2.5
 * Produced : Sat Aug 25 23:27:45 EEST 2018
 *
 * -----------------------------------------------------------------------------
 */

package jp.co.soramitsu.sora.sdk.did.parser.generated;

import java.util.ArrayList;

final public class Rule_CHAR extends Rule {

  public Rule_CHAR(String spelling, ArrayList<Rule> rules) {
    super(spelling, rules);
  }

  public static Rule_CHAR parse(ParserContext context) {
    context.push("CHAR");

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
          Rule rule = Terminal_NumericValue.parse(context, "%x01-7F", "[\\x01-\\x7F]", 1);
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
      rule = new Rule_CHAR(context.text.substring(a0.start, a0.end), a0.rules);
    } else {
      context.index = s0;
    }

    context.pop("CHAR", parsed);

    return (Rule_CHAR) rule;
  }

  public Object accept(Visitor visitor) {
    return visitor.visit(this);
  }
}

/* -----------------------------------------------------------------------------
 * eof
 * -----------------------------------------------------------------------------
 */
