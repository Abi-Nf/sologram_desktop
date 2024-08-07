package music.sologram.app.lib;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import music.sologram.app.data.models.Album;
import music.sologram.app.data.models.Artist;
import music.sologram.app.data.models.Genre;
import music.sologram.app.data.models.Track;
import music.sologram.app.data.utils.TrackRange;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.Collectors;

import static music.sologram.app.utils.Utils.executeInNewThread;

public class FileTagReader {
  public static Track readFile(File file, File imageFolder) throws Exception {
    return get(file, new Mp3File(file), imageFolder);
  }

  public static Track readFile(String file, File imageFolder) throws Exception {
    return get(new File(file), new Mp3File(file), imageFolder);
  }

  private static Track get(File file, Mp3File audioFile, File imageFolder){
    var tags = audioFile.hasId3v2Tag() ? audioFile.getId3v2Tag() : audioFile.getId3v1Tag();
    var trackBuilder = Track.builder()
      .source(file.getAbsolutePath())
      .title(tags.getTitle())
      .duration(audioFile.getLengthInMilliseconds());

    var track = TrackRange.fromString(tags.getTrack());
    trackBuilder.trackOf(track.of()).trackNumber(track.number());

    var year = tags.getYear();
    var albumBuilder = Album.builder()
      .name(tags.getAlbum())
      .year(year != null ? Integer.valueOf(year) : null);

    if(tags instanceof ID3v2 v2){
      trackBuilder.lyrics(v2.getLyrics());
      var artist = v2.getArtist();
      if(artist != null){
        var artists = Arrays.stream(artist.split("/"))
          .map(FileTagReader::getArtist)
          .collect(Collectors.toList());
        if(!artists.isEmpty()){
          albumBuilder.artist(artists.removeFirst());
          albumBuilder.featArtists(artists);
        }
      }

      var genre = v2.getGenreDescription();
      if(genre != null){
        var genres = Arrays.stream(genre.split("/"))
          .map(FileTagReader::getGenre)
          .toList();
        albumBuilder.genres(genres);
      }

      var image = v2.getAlbumImage();
      if(image != null && image.length > 0){
        var fileName = (artist + "_" + v2.getAlbum())
          .replaceAll("=", "equal")
          .replaceAll("\\+", "plus")
          .replaceAll("\\*", "times")
          .replaceAll("\\W", "_");
        var imagePath = new File(imageFolder, fileName);
        albumBuilder.art(imagePath.getAbsolutePath());
        saveFile(imagePath, image);
      }
    }

    trackBuilder.album(albumBuilder.build());
    return trackBuilder.build();
  }

  private static void saveFile(File file, byte[] bytes){
    executeInNewThread(() -> {
      try {
        Files.write(file.toPath(), bytes);
      } catch (IOException _) {}
    });
  }

  private static Genre getGenre(String name){
    return Genre.builder().name(name).build();
  }

  private static Artist getArtist(String name){
    return Artist.builder().name(name).build();
  }
}
