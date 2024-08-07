package music.sologram.app;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;
import music.sologram.app.data.HibernateConfig;
import music.sologram.app.windows.installation.InstallationWindow;

import static music.sologram.app.settings.SettingChecker.shouldInstallApp;
import static music.sologram.app.utils.Utils.*;

public class SologramApplication extends Application {
  public static void main(String[] args) {
    launch();
  }

  private final BooleanProperty database = new SimpleBooleanProperty(false);

  private void initDb(){
    HibernateConfig.init();
    asyncUiThread(() -> database.set(true));
  }

  @Override
  public void start(Stage stage){
    Runnable runnableStage = () -> {
      executeInNewThread(this::initDb);
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