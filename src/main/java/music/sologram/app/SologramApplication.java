package music.sologram.app;

import javafx.application.Application;
import javafx.stage.Stage;
import music.sologram.app.data.HibernateConfig;
import music.sologram.app.windows.installation.InstallationWindow;
import music.sologram.app.windows.splash.SplashScreen;

import static music.sologram.app.settings.SettingChecker.isHomeAppOk;
import static music.sologram.app.settings.SettingChecker.shouldInstallApp;

public class SologramApplication extends Application {
  public static void main(String[] args) {
    launch();
  }

  private SplashScreen splash;

  public SologramApplication() {
    this.splash = new SplashScreen();
    if(isHomeAppOk()) splash.show();
  }

  private void removeSplash() {
    if(splash != null){
      splash.close();
      this.splash = null;
    }
  }

  @Override
  public void start(Stage stage){
    Runnable runnableStage = () -> {
      HibernateConfig.init();
      removeSplash();
      stage.setMinWidth(600);
      stage.setMinHeight(500);
      stage.centerOnScreen();
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