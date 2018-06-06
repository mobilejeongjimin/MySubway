package com.example.jeongjimin.mysubway;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class SubwayMapView extends View {

    private Bitmap SubwayMap;
    private int mCanvasHeight = 1;
    private int mCanvasWidth = 1;

    private ScaleGestureDetector gestureDetector;

    private float Scale = 1.0f;

    private float LastX, LastY;
    private float MoveX, MoveY;

    public SubwayMapView(Context context) {
        super(context);
        init(context);
    }

    public SubwayMapView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        init(context);
    }

    private void init(Context context) {
        gestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        Resources res = context.getResources();
        SubwayMap = BitmapFactory.decodeResource(res,R.drawable.seoulsubwaymap);
    }

    public void onDraw(Canvas canvas){
        mCanvasWidth = getWidth();
        mCanvasHeight = getHeight();

        if(SubwayMap.getHeight() != mCanvasHeight || SubwayMap.getWidth() != mCanvasWidth){
            SubwayMap = SubwayMap.createScaledBitmap(SubwayMap,mCanvasWidth,mCanvasHeight,true);
        }
        canvas.save();
        canvas.scale(Scale,Scale);
        canvas.drawBitmap(SubwayMap, 0 + MoveX, 0 + MoveY, null);
        canvas.restore();
    }

    public boolean onTouchEvent(MotionEvent event){
        gestureDetector.onTouchEvent(event);

        final int Action = MotionEventCompat.getActionMasked(event);

        switch(Action){
            case MotionEvent.ACTION_DOWN:{

                final float x = event.getX();
                final float y = event.getY();

                LastX = x;
                LastY = y;
                Log.i("TEST", String.valueOf(x));
                Log.i("TEST2", String.valueOf(y));

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final float x = event.getX();
                final float y = event.getY();

                final float dx = x - LastX;
                final float dy = y - LastY;

                MoveX += dx/2;
                Log.i("TEST3", String.valueOf(MoveX));
                if(MoveX >= 0){ MoveX = 0; }
                if(MoveX <= -mCanvasWidth/2){ MoveX = -mCanvasWidth/2; }


                MoveY += dy/2;
                Log.i("TEST4", String.valueOf(MoveY));
                if(MoveY >= 0){ MoveY = 0; }
                if(MoveY <= -mCanvasHeight/2){ MoveY = -mCanvasHeight/2; }

                LastX = x;
                LastY = y;
            }
        }
        invalidate();
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{

        public boolean onScale(ScaleGestureDetector detector){

            Scale *= detector.getScaleFactor();
            if(Scale < 1.0f) Scale = 1.0f;
            if(Scale > 8.0f) Scale = 8.0f;

            invalidate();
            return true;
        }
    }
}


