package ro.fortech.betting.exception;


public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2806054652471997886L;

	public NotFoundException() {
        super();
    }

    public NotFoundException(final String message) {
        super(message);
    }

}
