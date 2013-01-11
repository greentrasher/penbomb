package com.penbomb.game;

import com.penbomb.game.model.Boom;
import com.penbomb.game.model.Droid;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.GestureDetector.OnGestureListener;
import android.widget.ImageView;

public class PenBombPanel extends SurfaceView implements
	  SurfaceHolder.Callback, OnGestureListener  {
	private static final String TAG = MainThread.class.getSimpleName();
	private MainThread thread;
	private Droid droid;
	private Boom boom;
	 private GestureDetector gDetector;
	 
	 public PenBombPanel(Context context) {
	  super(context);
	  // adding the callback (this) to the surface holder to intercept events
	  getHolder().addCallback(this);
	  
	  // create droid and load bitmap
	  droid = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), 50, 50);
	  boom = new Boom(BitmapFactory.decodeResource(getResources(), R.drawable.boom_1), 50, 50);
	  gDetector = new GestureDetector(this);
	  
	// create the game loop thread
	  thread = new MainThread(getHolder(), this);
	  
	  // make the GamePanel focusable so it can handle events
	  setFocusable(true);
	 }
	
	 @Override
	 public boolean onDown(MotionEvent arg0) {
		 return false;
	 }

	 @Override
	 public boolean onFling(MotionEvent start, MotionEvent finish, float xVelocity, float yVelocity) {
	    	Log.d(TAG, "onFling: x=" + start.getRawX() + ",y=" + start.getRawY());
	    	Log.d(TAG, "onFling: xvelocity=" + xVelocity + ",yvelocity=" + yVelocity);
	    	if (start.getAction() == MotionEvent.ACTION_MOVE) {
				// the gestures
				if (droid.isTouched()) {
				    // the droid was picked up and is being dragged
				    droid.setX((int)start.getRawX());
				    droid.setY((int)start.getRawY());
				}
				 Log.d(TAG, "onFlingCoords: x=" + start.getRawX() + ",y=" + start.getRawY());
			}
			
			if (finish.getAction() == MotionEvent.ACTION_UP) {
				// touch was released
				if (droid.isTouched()) {
					droid.setTouched(false);
					Log.d(TAG, "droid is touched");
				}
				Log.d(TAG, "Finissh: x=" + finish.getRawX() + ",y=" + finish.getRawY());
				
				 boom.setX((int)finish.getX());
				    boom.setY((int)finish.getY());
				droid.setDropped(true); // its dropped
			}
	           

			return true;

	    }

	    @Override
	    public void onLongPress(MotionEvent arg0) {

	            // TODO Auto-generated method stub

	    }

	    @Override
	    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {
	    	Log.d(TAG, "onScroll: x=" + arg0.getRawX() + ",y=" + arg1.getRawY());
	        return false;

	    }

	    @Override
	    public void onShowPress(MotionEvent arg0) {

	            // TODO Auto-generated method stub

	    }

	    @Override
	    public boolean onSingleTapUp(MotionEvent arg0) {

	            // TODO Auto-generated method stub

	            return false;

	    }

	 @Override
	 public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	 }

	 @Override
	 public void surfaceCreated(SurfaceHolder holder) {
		 thread.setRunning(true);
		  thread.start();
	 }

	 @Override
	 public void surfaceDestroyed(SurfaceHolder holder) {
		 boolean retry = true;
		  while (retry) {
		   try {
		    thread.join();
		    retry = false;
		   } catch (InterruptedException e) {
		    // try again shutting down the thread
		   }
		  }
		  Log.d(TAG, "Thread was shut down cleanly");
	 }

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// delegating event handling to the droid
			droid.handleActionDown((int)event.getX(), (int)event.getY());
			
			// check if in the lower part of the screen we exit
			if (event.getY() > getHeight() - 50) {
				thread.setRunning(false);
			    ((Activity)getContext()).finish();
			} else {
			    Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		} 
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// the gestures
			if (droid.isTouched()) {
			    // the droid was picked up and is being dragged
			    droid.setX((int)event.getX());
			    droid.setY((int)event.getY());
			    
			}
			Log.d(TAG, "Coords:ACTION_MOVE x=" + event.getX() + ",y=" + event.getY());
		}
		
		if (event.getAction() == MotionEvent.ACTION_UP) {
			// touch was released
			if (droid.isTouched()) {
				droid.setTouched(false);
			}
			 Log.d(TAG, "Coords:ACTION_UP x=" + event.getX() + ",y=" + event.getY());
		}
		gDetector.onTouchEvent(event);
		return true;
	}
	
	protected void onDraw(Canvas canvas) {
		 //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), 10, 10, null);
		// fills the canvas with black
		  canvas.drawColor(Color.BLACK);
		  if(droid.isDropped()){
			  // this should display a boom image
			  boom.draw(canvas); // this should be droid.boom(canvas) - this will display a boom image.
			  
		  }else {
			  droid.draw(canvas);
		  }
	}
}
