package music.sologram.app.data.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Likes {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Track track;

  @Column(name = "liked_at")
  private String likedAt;

  public Instant getLikedAt() {
    return Instant.parse(likedAt);
  }

  public Likes(Track track){
    this.track = track;
  }

  @PostPersist
  private void onCreate(){
    this.likedAt = Instant.now().toString();
  }
}
