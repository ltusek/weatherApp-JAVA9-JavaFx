package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class GeografskaTocka extends BazniEntitet{
	private BigDecimal x;
	private BigDecimal y;
	
	public GeografskaTocka(BigDecimal x, BigDecimal y, int id) {
		super(id);
		this.x = x;
		this.y = y;
	}
	
	public GeografskaTocka(BigDecimal x, BigDecimal y) {
		super(0);
		this.x = x;
		this.y = y;
	}
	
	public BigDecimal getX() {
		return x;
	}
	
	public BigDecimal getY() {
		return y;
	}
	
	public void setX(BigDecimal x) {
		this.x = x;
	}
	
	public void setY(BigDecimal y) {
		this.y = y;
	}
	
	
}
