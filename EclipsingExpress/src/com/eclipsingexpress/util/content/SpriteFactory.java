
package com.eclipsingexpress.util.content;

import java.util.ArrayList;
import java.util.List;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.extension.physics.box2d.util.triangulation.EarClippingTriangulator;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.color.Color;

import android.os.SystemClock;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.eclipsingexpress.object.Asteroid;
import com.eclipsingexpress.object.Ship;

public class SpriteFactory extends ContentFactory
{
	protected static TextureRegion shipRegion;
	private static final int NUM_ASTEROID_VERTICES = 12;
	private static final float ASTEROID_MIN_RADIUS = 20.0f * SIZE_RATIO_AVG;
	private static final float ASTEROID_RADIUS_RANGE = 10.0f * SIZE_RATIO_AVG;

	public static void loadContent()
	{
		BitmapTextureAtlas shipAtlas = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256);
		shipRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				shipAtlas, activity.getAssets(), "sprites/ship.png", 0, 0);
		shipAtlas.load();
	}

	public static Ship createShip()
	{
		Ship ship = Ship.createShip(cameraWidth / 2, cameraHeight / 2,
				shipRegion, activity.getVertexBufferObjectManager(),
				physicsWorld);
		return ship;
	}

	public static Asteroid createAsteroid(Vector2 position, Vector2 velocity,
			float omega)
	{
		List<Vector2> uniqueBodyVertices = new ArrayList<Vector2>();
		for (int i = 0; i < NUM_ASTEROID_VERTICES; i++)
		{
			double angle = 2 * Math.PI * i / NUM_ASTEROID_VERTICES;
			Vector2 v = new Vector2((float) (Math.cos(angle) * (Math.random()
					* ASTEROID_RADIUS_RANGE + ASTEROID_MIN_RADIUS)),
					(float) (Math.sin(angle) * (Math.random()
							* ASTEROID_RADIUS_RANGE + ASTEROID_MIN_RADIUS)));
			uniqueBodyVertices.add(v);
		}
		List<Vector2> uniqueBodyVerticesTriangulated = new EarClippingTriangulator()
				.computeTriangles(uniqueBodyVertices);
		float[] meshTriangles = new float[uniqueBodyVerticesTriangulated.size() * 3];
		for (int i = 0; i < uniqueBodyVerticesTriangulated.size(); i++)
		{
			meshTriangles[i * 3] = uniqueBodyVerticesTriangulated.get(i).x;
			meshTriangles[i * 3 + 1] = uniqueBodyVerticesTriangulated.get(i).y;
			meshTriangles[i * 3 + 2] = 0.0f;
			uniqueBodyVerticesTriangulated.get(i).mul(
					1 / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
		}
		Asteroid ast = new Asteroid(SystemClock.elapsedRealtime(), position.x
				/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, position.y
				/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, meshTriangles,
				activity.getVertexBufferObjectManager());
		ast.setColor(Color.WHITE);
		FixtureDef asteroidFixtureDef = PhysicsFactory.createFixtureDef(20f,
				0.5f, 0.5f);
		Body uniqueBody = PhysicsFactory.createTrianglulatedBody(physicsWorld,
				ast, uniqueBodyVerticesTriangulated, BodyType.DynamicBody,
				asteroidFixtureDef);
		uniqueBody.setTransform(position, 0.0f);
		uniqueBody.setLinearVelocity(velocity);
		uniqueBody.setAngularVelocity(omega);
		ast.setPhysicsConnector(new PhysicsConnector(ast, uniqueBody));
		physicsWorld.registerPhysicsConnector(ast.getPhysicsConnector());
		return ast;
	}

}
