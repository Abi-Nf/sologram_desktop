package music.sologram.app;

import javafx.application.Application;
import javafx.stage.Stage;

public class SologramApplication extends Application {
  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage){
    stage.setMinWidth(600);
    stage.setMinHeight(500);
    stage.centerOnScreen();
    stage.show();
  }
}