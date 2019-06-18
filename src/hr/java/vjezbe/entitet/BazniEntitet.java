package hr.java.vjezbe.entitet;

public abstract class BazniEntitet {
	
	private int id;

	public BazniEntitet(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
