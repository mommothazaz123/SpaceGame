import acm.graphics.*;

public class Bullet {
	public double dX, dY;
	public GOval shot;

	public Bullet(double x, double y, double dX, double dY) {
		this.dX = dX;
		this.dY = dY;
		this.shot = new GOval(x, y, 5, 5);
	}

	public void move() {
		shot.move(dX, dY);
	}
}
