package music.sologram.app.data.utils;

import javafx.util.Duration;

import static music.sologram.app.utils.Utils.formatSeconds;


public class DurationFormater {
  public static String format(Duration duration){
    if(duration == null) return "00:00";
    return formatSeconds(duration.toSeconds());
  }
}
