package com.kangyue.lifear2;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;

import android.view.SurfaceView;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.kangyue.lifear2.LifeAR2;

public class AndroidLauncher extends AndroidApplication {
	private int origWidth;
	private int origHeight;





	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 請求權限





		//强制竖屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//通过程序改变屏
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		//OpenGL ES 1.x support removed from libgdx(http://www.badlogicgames.com/wordpress/?p=3311)
		// cfg.useGL20 = false;//54wall old
		// we need to change the default pixel format - since it does not
		// include an alpha channel
		// we need the alpha channel so the camera preview will be seen behind
		// the GL scene
		cfg.r = 8;
		cfg.g = 8;
		cfg.b = 8;
		cfg.a = 8;
		DeviceCameraControl cameraControl = new AndroidDeviceCameraController(
				this);
		//拍照程序在AndroidDeviceCameraController，通过cameraControl传给MyGdxGame0606，进入LibGDX
		initialize(new LifeAR2(cameraControl), cfg);

		if (graphics.getView() instanceof SurfaceView) {
			SurfaceView glView = (SurfaceView) graphics.getView();
			// force alpha channel - I'm not sure we need this as the GL surface
			// is already using alpha channel
			glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		}
		// we don't want the screen to turn off during the long image saving
		// process
		graphics.getView().setKeepScreenOn(true);
		// keep the original screen size
		origWidth = graphics.getWidth();
		origHeight = graphics.getHeight();
	}

	public void post(Runnable r) {
		handler.post(r);
	}

	public void setFixedSize(int width, int height) {
		if (graphics.getView() instanceof SurfaceView) {
			SurfaceView glView = (SurfaceView) graphics.getView();
			glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
			glView.getHolder().setFixedSize(width, height);
		}
	}

	public void restoreFixedSize() {
		if (graphics.getView() instanceof SurfaceView) {
			SurfaceView glView = (SurfaceView) graphics.getView();
			glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
			glView.getHolder().setFixedSize(origWidth, origHeight);
		}
	}


}
