package music.sologram.app.data;

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
    SESSION = configuration.buildSessionFactory();
  }

  public static Session getSession() {
    return SESSION.openSession();
  }
}
