package com.penbomb.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	 private static final String TAG = MainThread.class.getSimpleName();
	private SurfaceHolder surfaceHolder;
	private PenBombPanel gamePanel;
	
	
	public MainThread(SurfaceHolder surfaceHolder, PenBombPanel gamePanel) {
		 super();
		 this.surfaceHolder = surfaceHolder;
		 this.gamePanel = gamePanel;
	}
	 // flag to hold game state
	 private boolean running;
	 public void setRunning(boolean running) {
	  this.running = running;
	 }

	 public void run() {
		  Canvas canvas;
		  Log.d(TAG, "Starting game loop");
		  while (running) {
		   canvas = null;
		   // try locking the canvas for exclusive pixel editing on the surface
		   try {
		    canvas = this.surfaceHolder.lockCanvas();
		    synchronized (surfaceHolder) {
		     // update game state
		     // draws the canvas on the panel
		    	this.gamePanel.onDraw(canvas);
		    	
		    }
		   } finally {
		    // in case of an exception the surface is not left in
		    // an inconsistent state
		    if (canvas != null) {
		     surfaceHolder.unlockCanvasAndPost(canvas);
		    }
		   } // end finally
		  }
	}
}