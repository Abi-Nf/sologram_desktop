package music.sologram.app.data.utils;

public record TrackRange(Integer number, Integer of) {
  private static final TrackRange EMPTY = new TrackRange(null, null);

  public static TrackRange empty(){
    return EMPTY;
  }

  public static TrackRange fromString(String value){
    if(value == null) return EMPTY;
    var values = value.split("/");
    if(values.length < 2) return EMPTY;
    var number = values[0];
    var total = values[1];
    return new TrackRange(Integer.valueOf(number), Integer.valueOf(total));
  }

  private String threadNull(Integer value){
    if(value != null) return value.toString();
    return "N/A";
  }

  @Override
  public String toString() {
    return "{no=" +  threadNull(number) + ",of=" + threadNull(of) + "}";
  }
}
