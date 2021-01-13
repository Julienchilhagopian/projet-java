package Model;

public class Point {
	private int x;
	private int y;
	private Boolean isActive;
	private int num;

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.isActive = false;
		this.num=0;
	}
	
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", isActive=" + isActive + ", num=" + num + "]";
	}
	public Boolean isActive() {
		return isActive;
	}

	public void setActive(Boolean active) {
		isActive = active;
	}
	
	public int getNum() {
		return num;
	}
	public void pointNum(int count) {
		this.num = count;
	}

}
