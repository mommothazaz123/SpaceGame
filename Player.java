import java.util.ArrayList;

import acm.graphics.*;

@SuppressWarnings("serial")
public class Player extends GPolygon {

	private static final double BULLET_SPEED = 4;
	private static final int FUEL_AMOUNT = 500;
	private static final int AMMO_AMOUNT = 10;

	public double dX = -.05, dY = -.5;
	public int angle = 90;
	public ArrayList<Bullet> bulletlist = new ArrayList<Bullet>();
	public int fuel = FUEL_AMOUNT;
	public int ammo = AMMO_AMOUNT;
	public GLabel fuelLabel;
	public GLabel ammoLabel;
	public String ammostring;

	public Player(int x, int y) {
		super(x, y);
		super.addVertex(-5, -10);
		super.addVertex(5, -10);
		super.addVertex(0, 10);

	}

	public void rotate(double theta) {
		super.rotate(theta);
	}

	public int getAngle() {
		return angle;
	}

	public void fire() {
		if (this.ammo > 0) {
			this.bulletlist.add(new Bullet(this.getX(), this.getY(),
					BULLET_SPEED * Math.cos(Math.toRadians(this.angle)),
					BULLET_SPEED * Math.sin(Math.toRadians(this.angle))));
			this.ammo--;
		}
	}
}
