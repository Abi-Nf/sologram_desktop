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
@Table(name = "play_track_history")
public class PlayTrackHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Track track;

  @Column(name = "played_at")
  private String playedAt;

  public Instant getPlayedAt() {
    return Instant.parse(playedAt);
  }

  public PlayTrackHistory(Track track){
    this.track = track;
  }

  @PostPersist
  private void onCreate(){
    this.playedAt = Instant.now().toString();
  }
}
