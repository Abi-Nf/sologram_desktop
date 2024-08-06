package music.sologram.app.settings;

import music.sologram.app.AppException;
import music.sologram.app.data.HibernateConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.lang.System.getProperty;

public class DefaultSettings {
  public static String USER_HOME = getProperty("user.home");
  public static String APP_HOME = ".sologram";

  private static final SimpleFileVisitor<Path> VISITOR = new SimpleFileVisitor<>() {
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      var result = super.visitFile(file, attrs);
      Files.deleteIfExists(file);
      return result;
    }
  };

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

  public static void resetFactory(){
    try {
      var folder = new File(getAppFolder());
      var canResetEntries = folder.exists();
      if(canResetEntries) Files.walkFileTree(folder.toPath(), VISITOR);
      if (!folder.exists()) canResetEntries = folder.mkdirs();
      if(canResetEntries){
        var db = new File(getDbPath());
        if (db.getParentFile().mkdirs() || db.createNewFile()) {
          HibernateConfig.execMigration();
        }

        var settings = new File(getSettingPath());
        if (settings.getParentFile().mkdirs() || settings.createNewFile()){
          // TODO: add default setting object in settings.json
        }

        var session = new File(getSessionPath());
        if(session.getParentFile().mkdirs() || session.createNewFile()){
          // TODO: add default session object in settings.json
        }
      }
    }catch (Exception e){
      throw new AppException(e);
    }
  }
}
