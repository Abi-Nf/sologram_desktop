package music.sologram.app;

public class AppException extends RuntimeException {
  public AppException(String message) {
    super(message);
  }

  public AppException(Throwable cause) {
    super(cause);
  }
}
