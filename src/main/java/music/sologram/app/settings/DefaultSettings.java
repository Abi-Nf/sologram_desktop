package music.sologram.app.settings;

import static java.lang.System.getProperty;

public class DefaultSettings {
  public static String USER_HOME = getProperty("user.home");
  public static String APP_HOME = ".sologram";

  public static String getAppFolder() {
    return String.join("/", USER_HOME, APP_HOME);
  }

  public static String loadAppPath(String... names){
    return String.join("/", USER_HOME, APP_HOME, String.join("/", names));
  }

  public static String getDbPath(){
    return loadAppPath("data", "sologram.db");
  }

  public static String getSettingPath(){
    return loadAppPath("settings.json");
  }

  public static String getSessionPath(){
    return loadAppPath("session.json");
  }
}
