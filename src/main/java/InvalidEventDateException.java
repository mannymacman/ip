public class InvalidEventDateException extends Exception {
    public InvalidEventDateException() {
        super("Event start DateTime must be before its end DateTime");
    }
}