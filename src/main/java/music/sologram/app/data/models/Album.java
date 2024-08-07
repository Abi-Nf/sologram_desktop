package music.sologram.app.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Album {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne
  @JoinColumn(name = "artist")
  private Artist artist;

  @ManyToMany
  @JoinTable(
    name = "album_feat_artist",
    joinColumns = @JoinColumn(name = "album"),
    inverseJoinColumns = @JoinColumn(name = "artist")
  )
  private List<Artist> featArtists;

  private String art;

  private Integer year;

  @ManyToMany
  @JoinTable(
    name = "album_genres",
    joinColumns = @JoinColumn(name = "album"),
    inverseJoinColumns = @JoinColumn(name = "genre")
  )
  private List<Genre> genres;

  @Column(name = "added_at")
  private String addedAt;

  public Instant getAddedAt() {
    return Instant.parse(addedAt);
  }

  public String getRawArt() {
    return art;
  }

  public Long getArtistId() {
    if (artist == null) return null;
    return artist.getId();
  }

  public Path getArt() {
    return art != null ? Path.of(art) : null;
  }

  @PostPersist
  private void onCreate() {
    this.addedAt = Instant.now().toString();
  }
}
