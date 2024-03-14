package game.exception;

/**
 * > 숫자의 범위 넘겼을 때 보내는 예외
 */
public class RangeException extends Exception{
    public RangeException(String message) {
        super(message);
    }
}
