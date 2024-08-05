module music.sologram.app {
  requires jakarta.persistence;
  requires javafx.media;
  requires static lombok;
  requires mp3agic;
  requires org.hibernate.orm.core;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.ikonli.material2;
  requires org.kordamp.ikonli.fontawesome5;
  requires org.kordamp.ikonli.materialdesign2;

  exports music.sologram.app;
  opens music.sologram.app to javafx.fxml;
}