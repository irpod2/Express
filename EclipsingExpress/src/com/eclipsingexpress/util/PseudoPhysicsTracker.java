
package com.eclipsingexpress.util;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.eclipsingexpress.object.Ship;

public class PseudoPhysicsTracker implements IAccelerationListener,
		IUpdateHandler
{
	public static final float MAX_SPEED = 25.0f;
	private static final int ACCEL_TO_PIXEL = 16384;
	private static final float ACCEL_TO_METER = ACCEL_TO_PIXEL
			/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
	private Body shipBody;
	private Vector2 acceleration;
	private Vector2 impulse;

	public PseudoPhysicsTracker(Ship shipSprite)
	{
		shipBody = shipSprite.getBody();
		acceleration = new Vector2(0, 0);
		impulse = new Vector2(0, 0);
	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData)
	{}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData)
	{
		acceleration.x = pAccelerationData.getX() * ACCEL_TO_METER;
		acceleration.y = pAccelerationData.getY() * ACCEL_TO_METER;
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
