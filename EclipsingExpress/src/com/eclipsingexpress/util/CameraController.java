
package com.eclipsingexpress.util;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;

import com.eclipsingexpress.object.Ship;
import com.eclipsingexpress.util.smartscene.StarBackgroundLayer;

public class CameraController implements IUpdateHandler
{
	private final float MAX_DISPLACEMENT_FACTOR_X;
	private final float MAX_DISPLACEMENT_FACTOR_Y;
	private Ship ship;
	private Camera camera;
	private StarBackgroundLayer bgLayer;

	public CameraController(Ship s, Camera cam, StarBackgroundLayer sbgl)
	{
		ship = s;
		camera = cam;
		bgLayer = sbgl;
		MAX_DISPLACEMENT_FACTOR_X = camera.getWidth() * 0.2f
				/ PseudoPhysicsTracker.MAX_SPEED;
		MAX_DISPLACEMENT_FACTOR_Y = camera.getHeight() * 0.2f
				/ PseudoPhysicsTracker.MAX_SPEED;
		// camera.setChaseEntity(ship);
	}

	@Override
	public void onUpdate(float pSecondsElapsed)
	{
		float centerX = ship.getCenterX() + ship.getVelocityX()
				* MAX_DISPLACEMENT_FACTOR_X;
		float centerY = ship.getCenterY() + ship.getVelocityY()
				* MAX_DISPLACEMENT_FACTOR_Y;

		camera.setCenter(centerX, centerY);

		bgLayer.onCameraChanged(camera);
	}

	@Override
	public void reset()
	{
		camera.reset();
	}

}
