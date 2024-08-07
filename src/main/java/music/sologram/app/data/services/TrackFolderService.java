package music.sologram.app.data.services;

import com.mpatric.mp3agic.InvalidDataException;
import music.sologram.app.data.HibernateConfig;
import music.sologram.app.data.models.*;
import music.sologram.app.lib.FileTagReader;
import org.hibernate.Session;

import java.io.File;
import java.nio.file.Path;

public class TrackFolderService {
  private final File imageFolder;
  private final Session session;

  public TrackFolderService(File imageFolder) {
    this.imageFolder = imageFolder;
    this.session = HibernateConfig.getSession();
  }

  public void startTrackTransaction(){
    session.beginTransaction();
  }

  public void endedTrackTransaction(){
    session.getTransaction().commit();
  }

  public void saveFolder(File file){
    session.persist(new Folder(file.getAbsolutePath()));
  }

  private Album saveIfNotExistsAlbum(Album album){
    var sql = """
    FROM Album a
    LEFT JOIN Artist ar ON a.artist = ar
    WHERE a.name = ?1 AND ar.id = ?2
    """;
    var value = session
      .createQuery(sql, Album.class)
      .setParameter(1, album.getName())
      .setParameter(2, album.getArtist().getId())
      .list();
    if(value != null && !value.isEmpty()){
      return value.getFirst();
    }
    return session.merge(album);
  }

  private Genre saveIfNoExistsGenre(Genre genre){
    var value = session
      .createQuery("FROM Genre WHERE name = ?1", Genre.class)
      .setParameter(1, genre.getName())
      .list();
    if(value != null && !value.isEmpty()){
      return value.getFirst();
    }
    return session.merge(genre);
  }

  private Artist saveIdNotExistsArtist(Artist artist){
    var name = artist.getName();
    if(name != null){
      var value = session
        .createQuery("FROM Artist where name = ?1", Artist.class)
        .setParameter(1, name)
        .list();
      if(value != null && !value.isEmpty()){
        return value.getFirst();
      }
    }
    return session.merge(artist);
  }

  private void saveIfNotExists(Track track){
    var album = track.getAlbum();
    if (album != null) {
      var feats = album.getFeatArtists();
      if (feats != null) {
        feats = feats.stream().map(this::saveIdNotExistsArtist).toList();
      }
      var genres = album.getGenres();
      if(genres != null) {
        genres = genres.stream().map(this::saveIfNoExistsGenre).toList();
      }
      var artist = album.getArtist();
      if(artist != null) artist = saveIdNotExistsArtist(artist);
      album = saveIfNotExistsAlbum(Album
        .builder()
        .genres(genres)
        .artist(artist)
        .featArtists(feats)
        .name(album.getName())
        .year(album.getYear())
        .art(album.getRawArt())
        .build());
    }
    session.persist(Track.builder()
      .album(album)
      .title(track.getTitle())
      .lyrics(track.getLyrics())
      .trackOf(track.getTrackOf())
      .duration(track.getDuration())
      .trackNumber(track.getTrackNumber())
      .source(track.getSource().toString())
      .lyrics(track.getLyrics())
      .build());
  }

  public void saveTrackFile(Path path){
    try {
      var track = FileTagReader.readFile(path.toFile(), imageFolder);
      saveIfNotExists(track);
    } catch (InvalidDataException _) {
    } catch (Exception e) {
      session.getTransaction().rollback();
      throw new RuntimeException(e);
    }
  }
}
