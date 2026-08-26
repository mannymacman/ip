public class InvalidEventDateException extends Exception {
    public InvalidEventDateException() {
        super("Event start DateTo,e must be before its end DateTime");
    }
}