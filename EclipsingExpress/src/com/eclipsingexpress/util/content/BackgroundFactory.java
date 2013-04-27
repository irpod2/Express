
package com.eclipsingexpress.util.content;

import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;

public class BackgroundFactory extends SpriteFactory
{
	private static IBitmapTextureAtlasSource starAtlasSource;

	public static void loadContent()
	{
		starAtlasSource = AssetBitmapTextureAtlasSource.create(
				activity.getAssets(), "sprites/starscape.png");
	}

	public static RepeatingSpriteBackground createStarscape()
	{
		RepeatingSpriteBackground starbg = new RepeatingSpriteBackground(
				cameraWidth, cameraHeight, activity.getTextureManager(),
				starAtlasSource, 1.0f, activity.getVertexBufferObjectManager());
		return starbg;
	}
}
