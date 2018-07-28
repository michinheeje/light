package com.example.light;

import java.util.List;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity{

	private float brightness = 0;
	private String flash;

	private Camera camera;
	private Camera.Parameters param;
	private List<String> list;

	private Window win;
	private WindowManager.LayoutParams LP;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onPause(){
		rtnBrightness();
		rtnFlashMode();
		super.onPause();		
	}

	@Override
	protected void onResume(){
		setBrightness();
		setFlashMode();
		super.onResume();		
	}

	private void setBrightness(){
		win = getWindow();
		LP = getWindow().getAttributes();
		brightness = LP.screenBrightness;

		LP.screenBrightness = 1;
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		win.setAttributes(LP);
	}

	private void setFlashMode(){
		camera = Camera.open();
		param = camera.getParameters();
		list = param.getSupportedFlashModes();
		if(list != null){
			flash=param.getFlashMode();
			param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
			camera.setParameters(param);
		}
	}

	private void rtnBrightness(){
		LP.screenBrightness = brightness;
		win.setAttributes(LP);		
	}

	private void rtnFlashMode(){
		if(list != null){
			param.setFlashMode(flash);
			camera.setParameters(param);

			camera.release();    		
		}
	}
}