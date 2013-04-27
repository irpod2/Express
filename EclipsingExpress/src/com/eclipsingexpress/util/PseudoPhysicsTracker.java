
package com.eclipsingexpress.util;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.eclipsingexpress.object.Ship;

public class PseudoPhysicsTracker implements IAccelerationListener,
		IUpdateHandler
{
	private final float MAX_SPEED = 100.0f;
	private Ship ship;
	private Body shipBody;
	private Vector2 acceleration;
	private Vector2 impulse;

	public PseudoPhysicsTracker(Ship shipSprite)
	{
		ship = shipSprite;
		shipBody = ship.getBody();
		acceleration = new Vector2(0, 0);
		impulse = new Vector2(0, 0);
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData)
	{}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData)
	{
		acceleration.x = pAccelerationData.getX() * 128.0f;
		acceleration.y = pAccelerationData.getY() * 128.0f;
		impulse.x = acceleration.x;
		impulse.y = acceleration.y;
	}

	@Override
	public void onUpdate(float pSecondsElapsed)
	{
		shipBody.applyLinearImpulse(impulse.mul(pSecondsElapsed),
				shipBody.getWorldCenter());
		Vector2 v = shipBody.getLinearVelocity();
		if (v.len() > MAX_SPEED)
			shipBody.setLinearVelocity(v.mul(MAX_SPEED / v.len()));

		shipBody.setTransform(shipBody.getPosition(),
				(float) (Math.atan2(v.y, v.x) + Math.PI / 2.0d));

		impulse.x = acceleration.x;
		impulse.y = acceleration.y;
	}

	@Override
	public void reset()
	{}
}
