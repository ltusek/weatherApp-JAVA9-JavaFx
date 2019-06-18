package hr.java.vjezbe.iznimke;

public class NiskaTemperaturaException extends RuntimeException {

	private static final long serialVersionUID = 1564104739052342924L;

	public NiskaTemperaturaException() {
		super();
	}
	
	public NiskaTemperaturaException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
	
	public NiskaTemperaturaException(String arg0) {
		super(arg0);
	}
	
	public NiskaTemperaturaException(Throwable arg0) {
		super(arg0);
	}
	
}
