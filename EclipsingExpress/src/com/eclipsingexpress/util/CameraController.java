
package com.eclipsingexpress.util;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;

import com.eclipsingexpress.object.Ship;

public class CameraController implements IUpdateHandler
{
	private Ship ship;
	private Camera camera;

	public CameraController(Ship s, Camera cam)
	{
		ship = s;
		camera = cam;
	}

	@Override
	public void onUpdate(float pSecondsElapsed)
	{
		float offsetX = camera.getCenterX() - ship.getX()
				+ ship.getWidthScaled() / 2;
		float offsetY = camera.getCenterY() - ship.getY()
				+ ship.getHeightScaled() / 2;

		camera.offsetCenter(-offsetX, -offsetY);
	}

	@Override
	public void reset()
	{
		camera.reset();
	}

}
