package music.sologram.app.windows.splash;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static music.sologram.app.utils.Utils.getAppLogo;
import static music.sologram.app.utils.Utils.loadCssFile;
import static music.sologram.app.utils.Utils.loadResource;

public class SplashScreen extends Stage {
  public SplashScreen() {
    setWidth(500);
    setHeight(400);
    centerOnScreen();
    setResizable(false);
    setScene(createScene());
    setTitle("Splash");
    getIcons().add(getAppLogo());
    initModality(Modality.WINDOW_MODAL);
    initStyle(StageStyle.TRANSPARENT);
  }

  private Scene createScene(){
    var scene = new Scene(createParent());
    scene.getStylesheets().add(loadCssFile("/styles/windows/splash.css"));
    return scene;
  }

  private Parent createParent(){
    var logo = new ImageView(loadResource("/icons/logo.png").toExternalForm());
    var appName = new Label("Sologram");
    var box = new VBox(logo, appName);
    box.getStyleClass().add("box");
    return box;
  }
}
