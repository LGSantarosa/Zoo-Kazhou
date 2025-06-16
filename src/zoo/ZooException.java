package src.zoo;

public class ZooException extends Exception {
  public ZooException(String message) {
    super(message);
  }

  public ZooException(String message, Throwable cause) {
    super(message, cause);
  }
}