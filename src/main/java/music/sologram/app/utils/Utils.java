package music.sologram.app.utils;

import java.net.URL;

import static java.util.Objects.requireNonNull;

public class Utils {
  public static URL loadResource(String name){
    return requireNonNull(
      Utils.class.getResource(name),
      "Resource " + name + " not found"
    );
  }

  public static String loadCssFile(String name){
    return loadResource(name).toExternalForm();
  }
}
