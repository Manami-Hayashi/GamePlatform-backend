package be.kdg.prog6.common.exceptions;

public class GameSessionNotReadyException extends RuntimeException {
    public GameSessionNotReadyException(String message) {
        super(message);
    }
}