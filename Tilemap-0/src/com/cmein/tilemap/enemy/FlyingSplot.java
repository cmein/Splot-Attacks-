package com.cmein.tilemap.enemy;

import com.cmein.tilemap.Art;
import com.cmein.tilemap.bullet.Bullet;
import com.cmein.tilemap.bullet.ThoriumBullet;
import com.cmein.tilemap.path.Path;
import com.cmein.tilemap.tower.Tower;

public class FlyingSplot extends Enemy {

	static final float VELOCITY = 75;
	private int width;
	private int height;
	private float startingHeight;
	private float x;
	private float y;
	private Path path;
	private static int damage=50;
	private static int pay=5;

	public FlyingSplot(float x, float y, Path path, DIR startingDir) {
		super(x, y, false, false, 30, (float) Math.random(), startingDir, path, 0, damage, pay);
		this.startingHeight = y;
		width = 32;
		height = 16;
		this.path = path;
	}

	public void update(float deltaTime) {

		stateTime += deltaTime;

		if (!Art.currentTowers.isEmpty()) {
			for (Tower tower : Art.currentTowers) {
				if (this == tower.currentTarget()) {
					tower.setShotInterval(deltaTime);
				}
				if (tower.inRange((int) pos.x + width, (int) pos.y + height,
						tower.getX() * 32 + 16, tower.getY() * 32 + 16)) {
					if (!tower.hasTarget()) {
						tower.setTarget(this);
					}
					if (this == tower.currentTarget()
							&& tower.getShotInterval() >= tower.getAttackRate()) {
						x = pos.x;
						y = pos.y;
						Bullet bullet = new ThoriumBullet(
								tower.getX() * 32 + 16, tower.getY() * 32 + 16,
								this, tower);
						Art.currentBullets.add(bullet);
						// health -= tower.getDamage();
						tower.resetShotInterval();
					}

				} else {
					if (this == tower.currentTarget()) {
						tower.removeTarget(this);
					}
				}
				if (this.health <= 0 && this == tower.currentTarget()) {
					tower.removeTarget(this);
					isDead = true;
				}
			}
		}

		decideDirection(path, this.getCurrentPointIndex(), deltaTime,
				getDir());
		if (getDir() == DIR.RIGHT) {
			pos.x += VELOCITY * deltaTime;
		}
		if (getDir() == DIR.UP) {
			pos.y -= VELOCITY * deltaTime;
		}
		if (getDir() == DIR.DOWN) {
			pos.y += VELOCITY * deltaTime;
		}
		if (getDir() == DIR.LEFT) {
			pos.x -= VELOCITY * deltaTime;
		}
	}
}