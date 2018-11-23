// Copyright (c) 2014 by B.W. van Schooten, info@borisvanschooten.nl
package net.tmtg.glesjs;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;

import android.view.KeyEvent;
import android.view.MotionEvent;

import java.lang.reflect.*;

public class GameController {

	public static final int NR_PLAYERS=4;

	public static final int AXIS_RS_X = 2;
	public static final int AXIS_RS_Y = 3;
	public static final int AXIS_LS_X = 0;
	public static final int AXIS_LS_Y = 1;
	public static final int AXIS_L2 = 4;
	public static final int AXIS_R2 = 5;

	public static final int BUTTON_O = 0;
	public static final int BUTTON_U = 2;
	public static final int BUTTON_Y = 3;
	public static final int BUTTON_A = 1;
	public static final int BUTTON_L1 = 4;
	public static final int BUTTON_L2 = 6; // simulated using threshold
	public static final int BUTTON_L3 = 10;
	public static final int BUTTON_R1 = 5;
	public static final int BUTTON_R2 = 7; // simulated using threshold
	public static final int BUTTON_R3 = 11;
	public static final int BUTTON_DPAD_UP = 12;
	public static final int BUTTON_DPAD_DOWN = 13;
	public static final int BUTTON_DPAD_LEFT = 14;
	public static final int BUTTON_DPAD_RIGHT = 15;
	public static final int BUTTON_MENU = 16;

	public static float STICK_DEADZONE = 0.25f;
	public static float BUMPER_DEADZONE = 0.5f;

	static Class impl=null;
	static Method _init,keyup,keydown,motionevent,startofframe,
			controllerbyplayer;
	static Method getaxisvalue, getbutton, buttonchanged;

	public static GameController[] controllers = new GameController[NR_PLAYERS];

	boolean [] buttons = new boolean[17];
	float [] axes = new float[6];

	// instance fields
	int player;
	// null = inactive / not connected
	Object implinstance = null;

	public static void init(Activity activity) {
		System.out.println("GameController: Probing ...");
	}

	public static boolean onKeyDown(int keyCode, KeyEvent event) {
		if (impl==null) return false;
		try {
			Object ret = keydown.invoke(null,keyCode,event);
			return ((Boolean)ret).booleanValue();
		} catch (Exception e) { e.printStackTrace(); }
		return false;
	}

	public static boolean onKeyUp(int keyCode, KeyEvent event) {
		if (impl==null) return false;
		try {
			Object ret = keyup.invoke(null,keyCode,event);
			return ((Boolean)ret).booleanValue();
		} catch (Exception e) { e.printStackTrace(); }
		return false;
	}

	public static boolean onGenericMotionEvent(MotionEvent event) {
		if (impl==null) return false;
		try {
			Object ret = motionevent.invoke(null,event);
			return ((Boolean)ret).booleanValue();
		} catch (Exception e) { e.printStackTrace(); }
		return false;
	}

	public static void startOfFrame() {
		if (impl==null) return;
		try {
			startofframe.invoke(null);
		} catch (Throwable t) {

		}
	}


	public static GameController getControllerByPlayer(int player) {
		return controllers[player];
	}


	// instance

	public GameController(int player) {
		this.player = player;
	}

	private void tryGetController(int player) {
		try {
			implinstance = controllerbyplayer.invoke(null,player);
		} catch (Exception e) { e.printStackTrace(); }
	}


	public float getAxisValue(int axis) {

		return 0.0f;

	}
	public boolean getButton(int but) {

		return false;
	}

	public boolean isConnected() {
		tryGetController(player);
		return implinstance != null;
	}

	public boolean [] getButtons() {

		return buttons;
	}
	public float [] getAxes() {

		return axes;
	}

}
