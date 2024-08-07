package music.sologram.app.data.models;

import jakarta.persistence.*;
import javafx.util.Duration;
import lombok.*;
import music.sologram.app.data.utils.DurationFormater;
import music.sologram.app.data.utils.TrackRange;

import java.io.File;
import java.time.Instant;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Track {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String source;

  private String title;

  @ManyToOne
  @JoinColumn(name = "album")
  private Album album;

  private Long duration;

  @Column(name = "track_of")
  private Integer trackOf;

  @Column(name = "track_number")
  private Integer trackNumber;

  private String lyrics;

  @Column(name = "added_at")
  private String addedAt;

  public Instant getAddedAt() {
    return Instant.parse(addedAt);
  }

  public String getStyledDuration() {
    return DurationFormater.format(Duration.millis(duration));
  }

  public TrackRange getTrack() {
    return new TrackRange(trackNumber, trackOf);
  }

  public File getSource() {
    return new File(source);
  }

  @PostPersist
  private void onCreate() {
    this.addedAt = Instant.now().toString();
  }
}
