package application.exceptions;

public class InvalidUsernameException extends Exception{

	public InvalidUsernameException() {
		
		super("Username alrealdy exists.");
		
	}
	
	public InvalidUsernameException(String message) {
		
		super(message);
		
	}
}
