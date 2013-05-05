
package com.eclipsingexpress.util.content;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.TextureRegion;

import com.eclipsingexpress.object.Ship;
import com.eclipsingexpress.util.smartscene.Layer;
import com.eclipsingexpress.util.smartscene.Layer.LayerType;
import com.eclipsingexpress.util.smartscene.SmartScene;

public class BackgroundFactory extends SpriteFactory
{
	private static IBitmapTextureAtlasSource starAtlasSource;
	private static TextureRegion starRegion;

	public static void loadContent()
	{
		starAtlasSource = AssetBitmapTextureAtlasSource.create(
				activity.getAssets(), "sprites/starscape.png");
		BitmapTextureAtlas starAtlas = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256);
		starRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				starAtlas, activity.getAssets(), "sprites/starscape.png", 0, 0);
		starAtlas.load();
	}

	public static Sprite createStarscapeTile(float pX, float pY)
	{
		Sprite tile = new Sprite(pX + starRegion.getWidth(), pY
				+ starRegion.getHeight(), starRegion,
				activity.getVertexBufferObjectManager());
		return tile;
	}

	public static void preloadTiles(SmartScene scene, Ship ship, Camera cam)
	{
		Layer bg = scene.getLayer(LayerType.BACKGROUND);
	}

	public static RepeatingSpriteBackground createStarscape()
	{
		RepeatingSpriteBackground starbg = new RepeatingSpriteBackground(
				cameraWidth, cameraHeight, activity.getTextureManager(),
				starAtlasSource, 1.0f, activity.getVertexBufferObjectManager());
		return starbg;
	}
}
