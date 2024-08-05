package music.sologram.app.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;
import java.time.Instant;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Artist {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  private String bio;

  private String image;

  @Column(name = "added_at")
  private String addedAt;

  public Instant getAddedAt() {
    return Instant.parse(addedAt);
  }

  public Path getImage() {
    return image != null ? Path.of(image) : null;
  }

  @PostPersist
  private void onCreate(){
    this.addedAt = Instant.now().toString();
  }
}
