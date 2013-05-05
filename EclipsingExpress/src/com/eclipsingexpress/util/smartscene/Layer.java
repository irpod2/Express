
package com.eclipsingexpress.util.smartscene;

import org.andengine.entity.Entity;

public class Layer extends Entity
{
	public enum LayerType
	{
		BACKGROUND,
		LOW,
		MIDDLE,
		HIGH,
		FRONT
	}

	private LayerType type;

	public Layer(LayerType lt)
	{
		type = lt;
	}

	public LayerType getType()
	{
		return type;
	}
}
