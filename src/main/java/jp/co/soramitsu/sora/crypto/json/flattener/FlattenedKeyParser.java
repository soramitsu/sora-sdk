package jp.co.soramitsu.sora.crypto.json.flattener;

import static jp.co.soramitsu.sora.crypto.json.flattener.Flattener.ARRAY_DELIMITER;
import static jp.co.soramitsu.sora.crypto.json.flattener.Flattener.DICT_DELIMITER;
import static jp.co.soramitsu.sora.crypto.json.flattener.KeyTypeEnum.ARRAY;
import static jp.co.soramitsu.sora.crypto.json.flattener.KeyTypeEnum.DICT;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FlattenedKeyParser {

  @NonNull
  private String stream;

  private String consumeString(int length) throws ParsingException {
    return consume(
        Pattern.compile(
            String.format(".{%d}", length)
        )
    );
  }

  private Integer consumeInt() throws ParsingException {
    try {
      Pattern pattern = TokenType.NUMBER.getPattern();
      String consumed = consume(pattern);
      return Integer.parseUnsignedInt(consumed);

    } catch (NumberFormatException e) {
      throw new ParsingException(e);
    }
  }

  private String consumeToken(TokenType token) throws ParsingException {
    return consume(token.getPattern());
  }

  private String parseDictKey() throws ParsingException {
    consumeToken(TokenType.DICT_DELIMITER);
    Integer len = consumeInt();
    consumeToken(TokenType.COLON);
    return consumeString(len);
  }

  private Integer parseArrayKey() throws ParsingException {
    consumeToken(TokenType.ARRAY_DELIMITER);
    return consumeInt();
  }

  private String consume(Pattern pattern) throws ParsingException {
    Matcher m = pattern.matcher(stream);
    if (m.find(0)) {
      int start = m.start();
      int end = m.end();

      if (start != 0) {
        // there are some tokens before match
        throw new ParsingException(
            String.format(
                "expected %s, got %s",
                pattern.pattern(),
                stream.substring(0, start)
            )
        );
      }

      String token = stream.substring(start, end);
      stream = stream.substring(end);
      return token;
    }

    throw new ParsingException(
        String.format("missing expected token: %s", pattern.pattern())
    );
  }

  public void parse(DeflattenVisitor visitor) throws ParsingException {
    do {
      if (stream.startsWith(DICT_DELIMITER)) {
        String parsed = parseDictKey();
        visitor.visitDictKey(parsed);
      } else if (stream.startsWith(ARRAY_DELIMITER)) {
        Integer parsed = parseArrayKey();
        visitor.visitArrayKey(parsed);
      } else {
        throw new ParsingException(
            String.format(
                "'%s' does not start with %s",
                stream,
                DICT_DELIMITER
            )
        );
      }
    } while (!stream.isEmpty());
  }

  public List<Token> parse() throws ParsingException {
    LinkedList<Token> tokens = new LinkedList<>();

    parse(new LambdaDeflattenVisitor(
        k -> tokens.addLast(new Token(DICT, k)),
        k -> tokens.addLast(new Token(ARRAY, k))
    ));

    return tokens;
  }

}
