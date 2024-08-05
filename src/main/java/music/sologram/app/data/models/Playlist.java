package music.sologram.app.data.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Playlist {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String image;

  private String description;

  @ManyToMany
  @JoinTable(name = "playlist_track")
  private List<Track> tracks;

  @Column(name = "created_at")
  private String createdAt;

  public Instant getCreatedAt() {
    return Instant.parse(createdAt);
  }

  public Playlist(String name){
    this.name = name;
  }

  public Playlist(String name, Path image){
    this(name);
    this.image = image.toAbsolutePath().toString();
  }

  public Playlist(String name, String description, Path image){
    this(name, image);
    this.description = description;
  }

  public Path getImage() {
    return Path.of(image);
  }

  @PostPersist
  private void onCreate(){
    this.createdAt = Instant.now().toString();
  }
}
