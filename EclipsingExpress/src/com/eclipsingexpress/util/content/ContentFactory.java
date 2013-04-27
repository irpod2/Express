
package com.eclipsingexpress.util.content;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.BaseGameActivity;

public class ContentFactory
{
	protected static final int FONT_SIZE = 64;
	protected static float SIZE_RATIO_X;
	protected static float SIZE_RATIO_Y;
	protected static float SIZE_RATIO_AVG;

	protected static BaseGameActivity activity;
	protected static int cameraWidth;
	protected static int cameraHeight;

	protected static Font deadHand;
	protected static PhysicsWorld physicsWorld;

	public static void init(BaseGameActivity bga, int camWidth, int camHeight,
			float ratioX, float ratioY)
	{
		activity = bga;
		FontFactory.setAssetBasePath("fonts/");

		cameraWidth = camWidth;
		cameraHeight = camHeight;

		SIZE_RATIO_X = ratioX;
		SIZE_RATIO_Y = ratioY;
		SIZE_RATIO_AVG = (SIZE_RATIO_X + SIZE_RATIO_Y) / 2.0f;

		loadContent();
	}

	public static void setPhysicsWorld(PhysicsWorld pw)
	{
		physicsWorld = pw;
	}

	public static void loadContent()
	{
		SpriteFactory.loadContent();
		BackgroundFactory.loadContent();
	}
}
