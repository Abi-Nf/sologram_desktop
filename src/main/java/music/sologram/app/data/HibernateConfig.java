package music.sologram.app.data;

import music.sologram.app.AppException;
import music.sologram.app.data.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.nio.file.Path;

import static java.nio.file.Files.readString;
import static java.sql.DriverManager.getConnection;
import static music.sologram.app.settings.DefaultSettings.getDbPath;
import static music.sologram.app.utils.Utils.loadResource;

public class HibernateConfig {
  public static SessionFactory SESSION;

  private static String getDbUrl(){
    return "jdbc:sqlite:" + getDbPath();
  }

  public static void execMigration(){
    try {
      var sql = readString(Path.of(
        loadResource("/data/migrations.sql").getFile()
      )).split(";");
      var connection = getConnection(getDbUrl());
      connection.setAutoCommit(false);
      for (String tableSql : sql){
        if(tableSql.trim().isEmpty()) continue;
        var statement = connection.createStatement();
        statement.execute(tableSql.trim());
      }
      connection.commit();
      connection.close();
    } catch (Exception e) {
      throw new AppException(e);
    }
  }

  public static void init() {
    if(SESSION == null){
      var configuration = new Configuration();
      configuration.setProperty("hibernate.connection.url", getDbUrl());
      configuration.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
      configuration.setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
      loadEntityClasses(configuration);
      SESSION = configuration.buildSessionFactory();
    }
  }

  private static void loadEntityClasses(Configuration conf){
    conf.addAnnotatedClass(Album.class);
    conf.addAnnotatedClass(Artist.class);
    conf.addAnnotatedClass(Folder.class);
    conf.addAnnotatedClass(Genre.class);
    conf.addAnnotatedClass(Likes.class);
    conf.addAnnotatedClass(Playlist.class);
    conf.addAnnotatedClass(PlayTrackHistory.class);
    conf.addAnnotatedClass(Track.class);
  }

  public static Session getSession() {
    return SESSION.openSession();
  }
}
