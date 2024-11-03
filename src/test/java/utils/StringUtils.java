package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

  public static long extractVariantIDFromString(String string) {
    Matcher m = Pattern.compile("\\d{14}").matcher(string);
    if (m.find()) {
      return Long.parseLong(m.group(0));
    } else {
      throw new IllegalArgumentException("14 digit Variant ID not found within string [" + string + "]");
    }
  }

}
