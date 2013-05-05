
package com.eclipsingexpress.object;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;

public class Ship extends Sprite
{
	private PhysicsConnector physicsConnector;
	private Body body;

	public Ship(float pX, float pY, ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager)
	{
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
	}

	public float getCenterX()
	{
		return mX + mWidth / 2;
	}

	public float getCenterY()
	{
		return mY + mHeight / 2;
	}

	public float getVelocityX()
	{
		return body.getLinearVelocity().x;
	}

	public float getVelocityY()
	{
		return body.getLinearVelocity().y;
	}

	public Body getBody()
	{
		return body;
	}

	public void setShipBody(Body sb)
	{
		body = sb;
	}

	public PhysicsConnector getPhysicsConnector()
	{
		return physicsConnector;
	}

	public void setPhysicsConnector(PhysicsConnector pc)
	{
		physicsConnector = pc;
	}
}
