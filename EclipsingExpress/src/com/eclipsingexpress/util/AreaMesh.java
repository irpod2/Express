
package com.eclipsingexpress.util;

import org.andengine.entity.primitive.DrawMode;
import org.andengine.entity.primitive.Mesh;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class AreaMesh extends Mesh implements IAreaShape
{
	private float width;
	private float height;

	public AreaMesh(float pX, float pY, float[] pBufferData, int pVertexCount,
			DrawMode pDrawMode,
			VertexBufferObjectManager pVertexBufferObjectManager)
	{
		super(pX, pY, pBufferData, pVertexCount, pDrawMode,
				pVertexBufferObjectManager);
		width = 0;
		height = 0;
	}

	@Override
	public float getWidth()
	{
		return width;
	}

	@Override
	public float getHeight()
	{
		return height;
	}

	@Override
	public float getWidthScaled()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getHeightScaled()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setHeight(float pHeight)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setWidth(float pWidth)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setSize(float pWidth, float pHeight)
	{
		// TODO Auto-generated method stub

	}

}
