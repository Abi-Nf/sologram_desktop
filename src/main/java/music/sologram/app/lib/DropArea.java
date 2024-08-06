package music.sologram.app.lib;

import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.util.function.Consumer;

public class DropArea {
  public static void folderDropArea(
    Node node,
    Consumer<Dragboard> onSuccess,
    Runnable dragOverHandler,
    Runnable dragExitHandler
  ) {
    node.setOnDragExited(_ -> {
      node.getStyleClass().remove("drag-over");
      if (dragExitHandler != null) dragExitHandler.run();
    });

    node.setOnDragOver(event -> {
      var db = event.getDragboard();
      if (
        event.getGestureSource() != node &&
          db.hasFiles() &&
          db.getFiles().stream().allMatch(File::isDirectory)
      ) {
        event.acceptTransferModes(TransferMode.COPY);
        var styleClass = node.getStyleClass();
        if(!styleClass.contains("drag-over")){
          node.getStyleClass().add("drag-over");
          if(dragOverHandler != null) dragOverHandler.run();
        }
      }
      event.consume();
    });

    node.setOnDragDropped(event -> {
      var db = event.getDragboard();
      var success = db.hasFiles();
      if(success) onSuccess.accept(db);
      if (dragExitHandler != null) dragExitHandler.run();
      event.setDropCompleted(success);
      event.consume();
      node.getStyleClass().remove("drag-over");
    });
  }
}
