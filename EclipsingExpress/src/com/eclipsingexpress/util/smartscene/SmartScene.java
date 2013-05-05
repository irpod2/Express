
package com.eclipsingexpress.util.smartscene;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.scene.Scene;

import com.eclipsingexpress.util.smartscene.Layer.LayerType;

public class SmartScene extends Scene
{
	private ArrayList<Layer> layers;

	public SmartScene(boolean inGame)
	{
		layers = new ArrayList<Layer>();
		LayerType[] types = LayerType.values();
		int start = 0;
		if (inGame)
		{
			start = 1;
			Layer bg = new StarBackgroundLayer();
			layers.add(bg);
			attachChild(bg);
		}
		for (int i = start; i < types.length; i++)
		{
			Layer l = new Layer(types[i]);
			layers.add(l);
			attachChild(l);
		}
	}

	public void attachChild(IEntity child, LayerType layer)
	{
		layers.get(layer.ordinal()).attachChild(child);
	}

	public boolean detachChild(IEntity child, LayerType layer)
	{
		return layers.get(layer.ordinal()).detachChild(child);
	}

	public Layer getLayer(LayerType lt)
	{
		return layers.get(lt.ordinal());
	}
}
