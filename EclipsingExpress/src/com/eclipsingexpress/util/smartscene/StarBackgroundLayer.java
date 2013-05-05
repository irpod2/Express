
package com.eclipsingexpress.util.smartscene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;

import com.eclipsingexpress.util.content.BackgroundFactory;

public class StarBackgroundLayer extends Layer
{
	private static final int TILE_WIDTH = 256;
	private static final int ROWS = 3;
	private static final int COLS = 5;

	private Sprite[][] tiles;
	private Sprite[] tempCol;
	private Sprite[] tempRow;

	public StarBackgroundLayer()
	{
		super(LayerType.BACKGROUND);
		tiles = new Sprite[ROWS][COLS];
		tempCol = new Sprite[ROWS];
		tempRow = new Sprite[COLS];
		for (int i = 0; i < ROWS; i++)
		{
			for (int j = 0; j < COLS; j++)
			{
				tiles[i][j] = BackgroundFactory.createStarscapeTile((j - 2)
						* TILE_WIDTH, (i - 2) * TILE_WIDTH);
				attachChild(tiles[i][j]);
			}
		}
	}

	public void onCameraChanged(Camera camera)
	{
		boolean reassign = false;
		float xMin = tiles[0][0].getX();
		float yMin = tiles[0][0].getY();
		// Left column out of range
		if (xMin > camera.getXMin())
		{
			xMin -= TILE_WIDTH;
			reassign = true;
		}
		// Right column out of range
		else if ((xMin + COLS * TILE_WIDTH) < camera.getXMax())
		{
			xMin += TILE_WIDTH;
			reassign = true;
		}
		// Top row out of range
		if (yMin > camera.getYMin())
		{
			yMin -= TILE_WIDTH;
			reassign = true;
		}
		// Bottom row out of range
		else if ((yMin + ROWS * TILE_WIDTH) < camera.getYMax())
		{
			yMin += TILE_WIDTH;
			reassign = true;
		}

		if (reassign)
		{
			// Reassign positions of tiles if any went out of range
			for (int i = 0; i < ROWS; i++)
			{
				for (int j = 0; j < COLS; j++)
				{
					tiles[i][j].setPosition(xMin + j * TILE_WIDTH, yMin + i
							* TILE_WIDTH);
				}
			}
		}
	}
}
