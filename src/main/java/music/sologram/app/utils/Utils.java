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
    runLater(runnable);
  }

  public static boolean pathExists(String path){
    return new File(path).exists();
  }
}
