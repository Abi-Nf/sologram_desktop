package music.sologram.app.data.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;

@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Folder {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String folder;

  public Folder(long id, String folder){
    this(folder);
    this.id = id;
  }

  public Path toPath(){
    return Path.of(folder);
  }

  public Folder(String folder){
    this.folder = folder;
  }
}
