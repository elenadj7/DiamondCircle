package application.exceptions;

public class InvalidMatrixDimensionException extends Exception {

	public InvalidMatrixDimensionException() {
		
		super("Matrix dimension must be between 7 and 10.");
		
	}
	
	public InvalidMatrixDimensionException(String message) {
		
		super(message);
		
	}
}
