package music.sologram.app;

import javafx.application.Application;
import javafx.stage.Stage;
import music.sologram.app.data.HibernateConfig;
import music.sologram.app.windows.installation.InstallationWindow;

import static music.sologram.app.settings.SettingChecker.shouldInstallApp;
import static music.sologram.app.utils.Utils.getAppLogo;

public class SologramApplication extends Application {
  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage){
    Runnable runnableStage = () -> {
      HibernateConfig.init();
      stage.setMinWidth(600);
      stage.setMinHeight(500);
      stage.centerOnScreen();
      stage.getIcons().add(getAppLogo());
      stage.show();
    };
    if(shouldInstallApp()){
      var installation = new InstallationWindow(runnableStage);
      installation.show();
    }else {
      runnableStage.run();
    }
  }
}