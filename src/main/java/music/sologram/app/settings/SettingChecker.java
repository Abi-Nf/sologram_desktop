package music.sologram.app.settings;

import static music.sologram.app.settings.DefaultSettings.*;
import static music.sologram.app.utils.Utils.pathExists;

public class SettingChecker {
  public static boolean isHomeAppOk(){
    return pathExists(getAppFolder()) &&
      pathExists(getDbPath()) &&
      pathExists(getSettingPath());
  }

  public static boolean shouldInstallApp(){
    return !isHomeAppOk();
  }
}
