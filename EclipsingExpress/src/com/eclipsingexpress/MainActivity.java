
package com.eclipsingexpress;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.ui.activity.BaseGameActivity;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;
import android.view.WindowManager;

import com.eclipsingexpress.scenario.GameplayScenario;
import com.eclipsingexpress.scenario.IScenario;
import com.eclipsingexpress.util.content.ContentFactory;

public class MainActivity extends BaseGameActivity
{
	public final int DEFAULT_CAMERA_WIDTH = 800;
	public final int DEFAULT_CAMERA_HEIGHT = 480;
	public int cameraWidth = DEFAULT_CAMERA_WIDTH;
	public int cameraHeight = DEFAULT_CAMERA_HEIGHT;
	private IScenario currentScenario;
	// private IScenario nextScenario;
	private IAccelerationListener accelListener;
	private Scene gameScene;
	private Camera camera;

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*
	 * @Override public Engine onCreateEngine(final EngineOptions
	 * pEngineOptions) { return new FixedStepEngine(pEngineOptions, 30); }
	 */

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	public EngineOptions onCreateEngineOptions()
	{
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
				.getDefaultDisplay();

		// For Android API 13+ getWidth() and getHeight() are deprecated
		if (android.os.Build.VERSION.SDK_INT >= 13)
		{
			Point size = new Point();
			display.getSize(size);
			cameraWidth = size.x;
			cameraHeight = size.y;
		}
		// For APIs < 13, getSize(Point) doesn't exist. Nice forethought,
		// Android.
		else
		{
			cameraWidth = display.getWidth();
			cameraHeight = display.getHeight();
		}
		if (cameraWidth < cameraHeight)
		{
			int temp = cameraWidth;
			cameraWidth = cameraHeight;
			cameraHeight = temp;
		}

		// Initialize the camera
		camera = new Camera(0, 0, cameraWidth, cameraHeight);

		// Create the engine options
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(cameraWidth, cameraHeight), camera);
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception
	{
		ContentFactory.init(this, cameraWidth, cameraHeight,
				DEFAULT_CAMERA_WIDTH / cameraWidth, DEFAULT_CAMERA_HEIGHT
						/ cameraHeight);
		ContentFactory.loadContent();

		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception
	{
		gameScene = new Scene();

		pOnCreateSceneCallback.onCreateSceneFinished(gameScene);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception
	{
		GameplayScenario gps = new GameplayScenario(this, camera, gameScene);
		currentScenario = gps;
		accelListener = gps.getAccelListener();
		currentScenario.prepareStart();
		currentScenario.start();

		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	@Override
	public void onResumeGame()
	{
		super.onResumeGame();
		this.enableAccelerationSensor(accelListener);
	}

	@Override
	public void onPauseGame()
	{
		super.onPauseGame();
		this.disableAccelerationSensor();
	}
}
