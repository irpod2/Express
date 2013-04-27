
package com.eclipsingexpress.object;

import org.andengine.entity.primitive.DrawMode;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.eclipsingexpress.util.AreaMesh;

public class Asteroid extends AreaMesh
{
	private PhysicsConnector physicsConnector;
	private float spawnTime;

	public Asteroid(float st, float pX, float pY, float[] pBufferData,
			VertexBufferObjectManager pVertexBufferObjectManager)
	{
		super(pX, pY, pBufferData, pBufferData.length / 3, DrawMode.TRIANGLES,
				pVertexBufferObjectManager);
		spawnTime = st;
	}

	public float getSpawnTime()
	{
		return spawnTime;
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
