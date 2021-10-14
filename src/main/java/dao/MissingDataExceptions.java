package dao;

public class MissingDataExceptions extends RuntimeException{


	private static final long serialVersionUID = 1L;
		
	public MissingDataExceptions (Exception e) {
		super(e);
	}
	public MissingDataExceptions (Exception e, String message) {
		super(e);
		System.out.println(message);
	}
}
