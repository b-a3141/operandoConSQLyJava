package dao;

public class TypePromotionError extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	public TypePromotionError(Exception e) {
		super(e);
	}
}
