
package com.eclipsingexpress.object;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Ship extends Sprite
{
	private PhysicsConnector physicsConnector;
	private Body shipBody;

	public Ship(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager)
	{
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);

	}

	public Body getBody()
	{
		return shipBody;
	}

	private void setShipBody(Body sb)
	{
		shipBody = sb;
	}

	public PhysicsConnector getPhysicsConnector()
	{
		return physicsConnector;
	}

	public void setPhysicsConnector(PhysicsConnector pc)
	{
		physicsConnector = pc;
	}

	public static Ship createShip(float pX, float pY,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager,
			PhysicsWorld physicsWorld)
	{
		Ship ship = new Ship(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		ship.setScale(0.25f, 0.25f);
		FixtureDef shipFixtureDef = PhysicsFactory.createFixtureDef(20.0f,
				1.0f, 0.2f);
		Body shipBody = PhysicsFactory.createBoxBody(physicsWorld, ship,
				BodyType.DynamicBody, shipFixtureDef);
		shipBody.setTransform(pX
				/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pY
				/ PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 0.0f);
		ship.setShipBody(shipBody);
		ship.setPhysicsConnector(new PhysicsConnector(ship, shipBody));
		physicsWorld.registerPhysicsConnector(ship.getPhysicsConnector());

		return ship;
	}
}
