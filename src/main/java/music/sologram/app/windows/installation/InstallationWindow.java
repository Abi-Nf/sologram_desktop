package music.sologram.app.windows.installation;

import javafx.application.ColorScheme;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import music.sologram.app.data.HibernateConfig;
import music.sologram.app.settings.DefaultSettings;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;

import java.io.File;
import java.util.function.Consumer;

import static javafx.application.Platform.getPreferences;
import static music.sologram.app.lib.DropArea.folderDropArea;
import static music.sologram.app.lib.FolderScanner.scanFolder;
import static music.sologram.app.utils.DialogUtil.selectAFolder;
import static music.sologram.app.utils.Utils.*;

public class InstallationWindow extends Stage {
  private final Runnable afterInstallation;
  private final BooleanProperty loading = new SimpleBooleanProperty(false);

  public InstallationWindow(Runnable afterInstallation) {
    executeInNewThread(() -> {
      DefaultSettings.resetFactory();
      HibernateConfig.init();
    });
    this.afterInstallation = afterInstallation;
    setWidth(500);
    setHeight(400);
    centerOnScreen();
    setTitle("Installation");
    setScene(createScene());
    getIcons().add(getAppLogo());
    initStyle(StageStyle.TRANSPARENT);
    initModality(Modality.APPLICATION_MODAL);
  }

  private Scene createScene(){
    var scene = new Scene(contentScene());
    scene.getStylesheets().add(loadCssFile("/styles/windows/installation.css"));
    return scene;
  }

  private Parent contentScene(){
    var imageView = new ImageView(getAppLogo());
    var imageContainer = new BorderPane(imageView);
    imageContainer.getStyleClass().add("image-container");
    var label = new Label("Listen to your music like ever");
    var button = new Button("Add folder", new FontIcon(MaterialDesignF.FOLDER_PLUS));

    var box = new VBox(imageContainer, label, button);
    var style = box.getStyleClass();
    style.addAll("box");
    setThemeClass(style);

    loading.addListener((_, _, isLoading) -> {
      if (isLoading) {
        label.setText("Scanning folder");
        var progress = new ProgressBar();
        box.getChildren().setAll(label, progress);
      }else {
        box.getChildren().clear();
      }
    });

    button.setOnAction(_ -> {
      var folder = selectAFolder(this);
      folder.ifPresent(this::scanUserFolder);
    });

    folderDropArea(
      box,
      this::handleDropFolder,
      () -> imageContainer.setCenter(new FontIcon(MaterialDesignF.FOLDER_OPEN)),
      () -> imageContainer.setCenter(imageView)
    );
    return box;
  }

  private void setThemeClass(ObservableList<String> styleClasses){
    var themeMode = getPreferences().colorSchemeProperty();
    Consumer<ColorScheme> handler = (mode) -> {
      var className = mode == ColorScheme.DARK ? "dark-theme" : "light-theme";
      styleClasses.removeAll("dark-theme", "light-theme");
      styleClasses.add(className);
    };
    handler.accept(themeMode.get());
    themeMode.addListener((_, _, mode) -> handler.accept(mode));
  }

  private void handleDropFolder(Dragboard board){
    if(!loading.get()){
      var folder = board.getFiles().getFirst();
      scanUserFolder(folder);
    }
  }

  private void scanUserFolder(File folder) {
    loading.set(true);
    executeInNewThread(() -> {
      scanFolder(folder);
      asyncUiThread(() -> {
        loading.set(false);
        afterInstallation.run();
        close();
      });
    });
  }
}
