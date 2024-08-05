package music.sologram.app.data;

import music.sologram.app.data.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import static music.sologram.app.settings.DefaultSettings.getDbPath;

public class HibernateConfig {
  public static SessionFactory SESSION;

  public static void init() {
    var configuration = new Configuration();
    configuration.setProperty("hibernate.connection.url", "jdbc:sqlite:" + getDbPath());
    configuration.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
    configuration.setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
    loadEntityClasses(configuration);
    SESSION = configuration.buildSessionFactory();
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
