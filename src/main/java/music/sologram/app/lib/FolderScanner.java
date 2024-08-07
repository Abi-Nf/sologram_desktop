package music.sologram.app.lib;

import music.sologram.app.AppException;
import music.sologram.app.data.services.TrackFolderService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.createDirectories;
import static java.nio.file.Files.walk;
import static music.sologram.app.settings.DefaultSettings.getImageFolder;

public class FolderScanner {
  private static final String[] SUPPORTED_FILES = {"mp3"};

  private static boolean isSupportedFile(Path path) {
    var file = path.toString();
    for (String extension : SUPPORTED_FILES) {
      if (file.endsWith("." + extension)) return true;
    }
    return false;
  }

  private static File imageFolder(){
    try {
      var imagePath = Path.of(getImageFolder()).toFile();
      if(imagePath.exists()) return imagePath;
      createDirectories(imagePath.toPath());
      return imagePath;
    }catch (IOException e){
      throw new AppException(e);
    }
  }

  public static void scanFolder(File folder){
    try(var fileWalker = walk(folder.toPath())) {
      var service = new TrackFolderService(imageFolder());
      service.saveFolder(folder);
      service.startTrackTransaction();
      fileWalker
        .filter(FolderScanner::isSupportedFile)
        .forEach(service::saveTrackFile);
      service.endedTrackTransaction();
      System.gc();
    }catch (Exception e){
      throw new AppException(e);
    }
  }
}
