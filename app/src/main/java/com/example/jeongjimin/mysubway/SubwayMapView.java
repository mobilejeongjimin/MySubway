package com.example.jeongjimin.mysubway;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class SubwayMapView extends View {

    private Bitmap SubwayMap;
    private int mCanvasHeight = 1;
    private int mCanvasWidth = 1;

    private ScaleGestureDetector gestureDetector;
    private float Scale = 1.0f;

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
        canvas.scale(Scale,Scale);
        canvas.drawBitmap(SubwayMap,0,0,null);
    }

    public boolean onTouchEvent(MotionEvent event){
        gestureDetector.onTouchEvent(event);
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


