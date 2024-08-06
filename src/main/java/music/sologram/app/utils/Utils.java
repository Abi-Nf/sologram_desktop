package music.sologram.app.utils;

import java.io.File;
import java.net.URL;

import static java.util.Objects.requireNonNull;
import static javafx.application.Platform.runLater;

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

  public static void executeInNewThread(Runnable runnable){
    new Thread(runnable).start();
  }

  public static void asyncUiThread(Runnable runnable){
    executeInNewThread(() -> runLater(runnable));
  }

  public static boolean pathExists(String path){
    return new File(path).exists();
  }

  public static <T extends Number> String padValue(T value, int length, String padValue){
    StringBuilder s = new StringBuilder(value.toString());
    while (s.length() < length) s.insert(0, padValue);
    return s.toString();
  }

  private static <T extends Number> String padTime(T value){
    return padValue(value.longValue(), 2, "0");
  }

  public static String formatSeconds(Number rawSeconds){
    var sec = Math.floor(rawSeconds.doubleValue() % 60);
    var min = Math.floor(rawSeconds.doubleValue() / 60);
    if (min < 60) return padTime(min) + ":" + padTime(sec);
    var hour = Math.floor(min / 60);
    min = min % 60;
    return padTime(hour) + ":" + padTime(min) + ":" + padTime(sec);
  }
}
