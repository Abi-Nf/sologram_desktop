package music.sologram.app.utils;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class DialogUtil {
  public static Optional<File> selectAFolder(Stage stage){
    var chooser = new DirectoryChooser();
    chooser.setTitle("Select a folder");
    return Optional.ofNullable(chooser.showDialog(stage));
  }
}
