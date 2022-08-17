package application.exceptions;

public class InvalidConfigFileException extends Exception{

	public InvalidConfigFileException() {
		
		super("the configuration file must be in the format:\n"
				+ "matrix dimension\n"
				+ "number of players\n"
				+ "player names\n"
				+ "number of holes");
	}
	
	public InvalidConfigFileException(String message) {
		
		super(message);
		
	}
}
