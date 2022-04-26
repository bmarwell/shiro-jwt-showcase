///usr/bin/env jbang "$0" "$@" ; exit $?

import java.nio.charset.StandardCharsets;
import java.util.Base64;

class base64url {
  public static void main(String... args) throws Exception {
    if (args.length < 2) {
      return;
    }

    if ("-d".equals(args[0])) {
      decode(args[1]);
    } else if ("-e".equals(args[0])) {
      encode(args[1]);
    }
  }

  public static void decode(String arg) {
    Base64.Decoder decoder = Base64.getUrlDecoder();
    String dStr = new String(decoder.decode(arg.getBytes(StandardCharsets.UTF_8)));
    System.out.println(dStr);
  }

  public static void encode(String arg) {
    Base64.Encoder encoder = Base64.getUrlEncoder();
    String eStr = encoder.encodeToString(arg.getBytes(StandardCharsets.UTF_8));
    System.out.println(eStr);
  }

}
