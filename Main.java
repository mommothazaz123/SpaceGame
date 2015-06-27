/**
 * TODO: Hyperspace
 */

/**
 * @author Andrew
 *
 */
import java.awt.event.KeyEvent;

import acm.program.*;
import acm.graphics.*;

@SuppressWarnings("serial")
public class Main extends GraphicsProgram {
	public static final int APPLICATION_WIDTH = 720;
	public static final int APPLICATION_HEIGHT = 720;

	public static final double GRAVITY = 0.0002;// default 0.0002
	public static final int GAME_SPEED = 25;// default 25
	public static final double THRUST = 0.01;// default 0.01

	public Player p1 = new Player(560, 360);
	public Player p2 = new Player(160, 360);

	public GOval star = new GOval(360, 360, 20, 20);

	public boolean p1Right = false, p1Left = false, p1Thrust = false,
			p2Right = false, p2Left = false, p2Thrust = false;

	public void init() {
		add(p1);
		add(p2);
		p1.rotate(180);
		p1.angle = -90;
		p1.dY = -p1.dY;
		p2.dX = -p2.dX;
		p1.fuelLabel = new GLabel("P1 Fuel: " + p1.fuel, 10, 10);
		p2.fuelLabel = new GLabel("P2 Fuel: " + p2.fuel, 550, 10);
		p1.ammoLabel = new GLabel("P1 Ammo: 0 0 0 0 0 0 0 0 0 0", 10, 650);
		p2.ammoLabel = new GLabel("P2 Ammo: 0 0 0 0 0 0 0 0 0 0", 550, 650);
		add(star);
		add(p1.fuelLabel);
		add(p2.fuelLabel);
		add(p1.ammoLabel);
		add(p2.ammoLabel);
		addKeyListeners();
	}

	public void run() {
		while (true) {
			doGravityTick();
			doRotation();
			doMovement();
			moveBullets();
			checkCollisions();
			updateLabels();
			pause(GAME_SPEED);

		}
	}

	public void updateLabels() {
		p1.fuelLabel.setLabel("P1 fuel: " + p1.fuel);
		p2.fuelLabel.setLabel("P2 fuel: " + p2.fuel);
		p1.ammostring = "";
		p2.ammostring = "";
		for (int i = 0; i < p1.ammo; i++) {
			p1.ammostring = p1.ammostring + "0 ";
		}
		for (int i = 0; i < p2.ammo; i++) {
			p2.ammostring = p2.ammostring + "0 ";
		}
		p1.ammoLabel.setLabel("P1 Ammo: " + p1.ammostring);
		p2.ammoLabel.setLabel("P2 Ammo: " + p2.ammostring);
	}

	public void doRotation() {
		if (p1Left) {
			p1.rotate(2);
			p1.angle -= 2;
		} else if (p1Right) {
			p1.rotate(-2);
			p1.angle += 2;
		}

		if (p2Left) {
			p2.rotate(2);
			p2.angle -= 2;
		} else if (p2Right) {
			p2.rotate(-2);
			p2.angle += 2;
		}
	}

	public void doMovement() {
		if (p1Thrust && p1.fuel > 0) {
			p1.dX += THRUST * Math.cos(Math.toRadians(p1.getAngle()));
			p1.dY += THRUST * Math.sin(Math.toRadians(p1.getAngle()));
			p1.fuel--;
		}
		if (p2Thrust && p2.fuel > 0) {
			p2.dX += THRUST * Math.cos(Math.toRadians(p2.getAngle()));
			p2.dY += THRUST * Math.sin(Math.toRadians(p2.getAngle()));
			p2.fuel--;
		}
		p1.move(p1.dX, p1.dY);
		p2.move(p2.dX, p2.dY);
		// warping
		if (p1.getX() <= 0) {
			p1.setLocation(719, p1.getY());
		}
		if (p1.getX() >= 720) {
			p1.setLocation(1, p1.getY());
		}
		if (p1.getY() <= 0) {
			p1.setLocation(p1.getX(), 719);
		}
		if (p1.getY() >= 720) {
			p1.setLocation(p1.getX(), 1);
		}
		if (p2.getX() <= 0) {
			p2.setLocation(719, p2.getY());
		}
		if (p2.getX() >= 720) {
			p2.setLocation(1, p2.getY());
		}
		if (p2.getY() <= 0) {
			p2.setLocation(p2.getX(), 719);
		}
		if (p2.getY() >= 720) {
			p2.setLocation(p2.getX(), 1);
		}
	}

	public void moveBullets() {
		for (int i = 0; i < p1.bulletlist.size(); i++) {
			add(p1.bulletlist.get(i).shot);
			p1.bulletlist.get(i).move();
		}
		for (int i = 0; i < p2.bulletlist.size(); i++) {
			add(p2.bulletlist.get(i).shot);
			p2.bulletlist.get(i).move();
		}
	}

	public void checkCollisions() {
		// p1, shots
		for (int i = 0; i < p2.bulletlist.size(); i++) {
			if (p1.getBounds()
					.contains(p2.bulletlist.get(i).shot.getLocation())) {
				remove(p1);
			}
		}

		// p2, shots
		for (int i = 0; i < p1.bulletlist.size(); i++) {
			if (p2.getBounds()
					.contains(p1.bulletlist.get(i).shot.getLocation())) {
				remove(p2);
			}
		}
		// star
		if (star.contains(p1.getLocation())) {
			remove(p1);
		}
		if (star.contains(p2.getLocation())) {
			remove(p2);
		}
	}

	public void doGravityTick() {
		p1.dX -= GRAVITY * Math.toDegrees(Math.cos(calcGravityVector(1)));
		p1.dY -= GRAVITY * Math.toDegrees(Math.sin(calcGravityVector(1)));
		p2.dX -= GRAVITY * Math.toDegrees(Math.cos(calcGravityVector(2)));
		p2.dY -= GRAVITY * Math.toDegrees(Math.sin(calcGravityVector(2)));
	}

	public double calcGravityVector(int p) {
		if (p == 1) {
			return Math.atan2((p1.getY() - 360), (p1.getX() - 360));
		} else {
			return Math.atan2((p2.getY() - 360), (p2.getX() - 360));
		}
	}

	public void keyPressed(KeyEvent k) {
		if (k.getKeyCode() == KeyEvent.VK_LEFT) {
			p2Left = true;
		} else if (k.getKeyCode() == KeyEvent.VK_RIGHT) {
			p2Right = true;
		}

		if (k.getKeyCode() == KeyEvent.VK_UP) {
			p2Thrust = true;
		}

		if (k.getKeyCode() == KeyEvent.VK_A) {
			p1Left = true;
		} else if (k.getKeyCode() == KeyEvent.VK_D) {
			p1Right = true;
		}

		if (k.getKeyCode() == KeyEvent.VK_W) {
			p1Thrust = true;
		}

		if (k.getKeyCode() == KeyEvent.VK_S) {
			p1.fire();
		}
		if (k.getKeyCode() == KeyEvent.VK_DOWN) {
			p2.fire();
		}
	}

	public void keyReleased(KeyEvent k) {
		if (k.getKeyCode() == KeyEvent.VK_LEFT) {
			p2Left = false;
		} else if (k.getKeyCode() == KeyEvent.VK_RIGHT) {
			p2Right = false;
		}

		if (k.getKeyCode() == KeyEvent.VK_UP) {
			p2Thrust = false;
		}

		if (k.getKeyCode() == KeyEvent.VK_A) {
			p1Left = false;
		} else if (k.getKeyCode() == KeyEvent.VK_D) {
			p1Right = false;
		}

		if (k.getKeyCode() == KeyEvent.VK_W) {
			p1Thrust = false;
		}
	}
}
