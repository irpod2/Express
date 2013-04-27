
package com.eclipsingexpress.scenario;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.ui.activity.BaseGameActivity;

import com.badlogic.gdx.math.Vector2;
import com.eclipsingexpress.object.Ship;
import com.eclipsingexpress.util.AsteroidSpawner;
import com.eclipsingexpress.util.CameraController;
import com.eclipsingexpress.util.PseudoPhysicsTracker;
import com.eclipsingexpress.util.content.BackgroundFactory;
import com.eclipsingexpress.util.content.ContentFactory;
import com.eclipsingexpress.util.content.SpriteFactory;

public class GameplayScenario implements IScenario
{
	private BaseGameActivity activity;
	private PseudoPhysicsTracker pseudoPhysicsWorld;
	private PhysicsWorld physicsWorld;
	private CameraController camController;
	private Camera camera;
	private Scene scene;
	private Ship ship;
	private AsteroidSpawner spawner;

	public GameplayScenario(BaseGameActivity bga, Camera cam, Scene scn)
	{
		activity = bga;
		scene = scn;
		camera = cam;
		scene.setBackground(BackgroundFactory.createStarscape());
		physicsWorld = new PhysicsWorld(new Vector2(0.0f, 0.0f), false);
		ContentFactory.setPhysicsWorld(physicsWorld);
		ship = SpriteFactory.createShip();
		pseudoPhysicsWorld = new PseudoPhysicsTracker(ship);
		spawner = new AsteroidSpawner(ship, scene, 1.0f, 2.0f,
				camera.getWidth(), camera.getHeight());
		camController = new CameraController(ship, camera);
	}

	public IAccelerationListener getAccelListener()
	{
		return pseudoPhysicsWorld;
	}

	@Override
	public void prepareStart()
	{
		scene.attachChild(ship);
	}

	@Override
	public void start()
	{
		scene.registerUpdateHandler(pseudoPhysicsWorld);
		scene.registerUpdateHandler(physicsWorld);
		scene.registerUpdateHandler(spawner);
		scene.registerUpdateHandler(camController);
	}

	@Override
	public void prepareEnd()
	{
		scene.unregisterUpdateHandler(pseudoPhysicsWorld);
		scene.unregisterUpdateHandler(physicsWorld);
		scene.unregisterUpdateHandler(spawner);
		scene.unregisterUpdateHandler(camController);
	}

	@Override
	public void end()
	{
		scene.detachChild(ship);
	}

	@Override
	public Scene getScene()
	{
		return scene;
	}

	@Override
	public boolean handleBackPress()
	{
		return false;
	}

}
