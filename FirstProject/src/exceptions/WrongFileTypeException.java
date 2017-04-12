package exceptions;

public class WrongFileTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongFileTypeException() {
		// TODO Auto-generated constructor stub
	}

	public WrongFileTypeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WrongFileTypeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public WrongFileTypeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WrongFileTypeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
