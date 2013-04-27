
package com.eclipsingexpress.util;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;

import android.os.SystemClock;

import com.badlogic.gdx.math.Vector2;
import com.eclipsingexpress.object.Asteroid;
import com.eclipsingexpress.object.Ship;
import com.eclipsingexpress.util.content.SpriteFactory;

public class AsteroidSpawner implements IUpdateHandler
{
	private final static int MAX_ASTEROIDS = 100;
	private final static float COLLISION_TIME_MIN = 6.0f;
	private final static float COLLISION_TIME_VARIATION = 4.0f;
	private final static float SPAWN_THRESHOLD = COLLISION_TIME_MIN;
	private final static float ROTATION_MAX = (float) (Math.PI * 2.0d);
	private final float SPAWN_MIN_TIME;
	private final float SPAWN_VARIATION;
	private final float SPAWN_DIST;
	private final float CAMERA_WIDTH;
	private final float CAMERA_HEIGHT;
	private float timeSinceLastSpawn;
	private float nextSpawnTime;
	private Ship ship;
	private ArrayList<Asteroid> asteroids;
	private Scene scene;

	public AsteroidSpawner(Ship playerShip, Scene scn, float spawnMinTime,
			float spawnVariationTime, float cameraWidth, float cameraHeight)
	{
		SPAWN_MIN_TIME = spawnMinTime;
		SPAWN_VARIATION = spawnVariationTime;
		CAMERA_WIDTH = cameraWidth;
		CAMERA_HEIGHT = cameraHeight;
		SPAWN_DIST = (float) Math.hypot(CAMERA_WIDTH, CAMERA_HEIGHT) / 2.0f
				/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		ship = playerShip;
		scene = scn;
		timeSinceLastSpawn = 0.0f;
		nextSpawnTime = SPAWN_MIN_TIME;
		asteroids = new ArrayList<Asteroid>();
	}


	@Override
	public void onUpdate(float pSecondsElapsed)
	{
		timeSinceLastSpawn += pSecondsElapsed;
		if (timeSinceLastSpawn >= nextSpawnTime)
		{
			timeSinceLastSpawn = 0.0f;
			spawnAsteroid();
			nextSpawnTime = SPAWN_MIN_TIME + (float) Math.random()
					* SPAWN_VARIATION;
		}
		cleanUpAsteroids();
	}

	private void spawnAsteroid()
	{
		if (asteroids.size() < MAX_ASTEROIDS)
		{
			double angle = Math.random() * Math.PI * 2.0d;
			Vector2 position = new Vector2(ship.getX()
					/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT
					+ SPAWN_DIST * (float) Math.cos(angle), ship.getY()
					/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT
					+ SPAWN_DIST * (float) Math.sin(angle));
			float collisionTime = COLLISION_TIME_MIN
					+ (float) (Math.random() * COLLISION_TIME_VARIATION);
			Vector2 velocity = new Vector2(ship.getBody().getPosition());
			velocity.sub(position);
			velocity.mul(1 / collisionTime);
			velocity.add(ship.getBody().getLinearVelocity());
			float omega = (float) (Math.sqrt(Math.random()) * ROTATION_MAX);
			omega *= Math.pow(-1, (int) (Math.random() + 0.5d));
			Asteroid next = SpriteFactory.createAsteroid(position, velocity,
					omega);
			asteroids.add(next);
			scene.attachChild(next);
		}
	}

	private void cleanUpAsteroids()
	{
		float spawnBoundary = SystemClock.elapsedRealtime() - SPAWN_THRESHOLD;
		ArrayList<Asteroid> toRemove = new ArrayList<Asteroid>();
		for (Asteroid a : asteroids)
		{
			if (a.getSpawnTime() > spawnBoundary && isOutOfBounds(a))
			{
				toRemove.add(a);
				scene.detachChild(a);
			}
		}
		for (Asteroid a : toRemove)
		{
			asteroids.remove(a);
		}
	}

	private boolean isOutOfBounds(Asteroid a)
	{
		return !((-a.getWidthScaled() < a.getX() && a.getX() < CAMERA_WIDTH) && (-a
				.getHeightScaled() < a.getY() && a.getY() < CAMERA_HEIGHT));
	}

	@Override
	public void reset()
	{}

}
