package com.example.jeongjimin.mysubway;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/*메인화면의 배경화면을 보여줄 view*/

public class BackgroundView extends View {

    private Drawable SubtitleImage;
    private Bitmap BackgroundImage;
    private int mCanvasHeight = 1;
    private int mCanvasWidth = 1;
    private int x = 0;
    private int y = 0;

    public BackgroundView(Context context) {
        super(context);
        init(context);
    }

    public BackgroundView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        init(context);
    }

    private void init(Context context) {
        Resources res = context.getResources();
        SubtitleImage = context.getResources().getDrawable(R.drawable.subway_subtitle_2);
        BackgroundImage = BitmapFactory.decodeResource(res,R.drawable.subway_background);
    }

    public void onDraw(Canvas canvas){

        /*배경 이미지 bitmap 형식으로 캔버스에 넣기*/
        mCanvasHeight = getHeight();
        mCanvasWidth = getWidth();
        if(BackgroundImage.getWidth() != mCanvasWidth || BackgroundImage.getHeight() != mCanvasHeight){
            BackgroundImage = BackgroundImage.createScaledBitmap(BackgroundImage,mCanvasWidth,mCanvasHeight,true);
        }
        canvas.drawBitmap(BackgroundImage,0,0,null);

        /*타이틀 이미지 생성*/
        SubtitleImage.setBounds(x + mCanvasWidth * 1/8, y + mCanvasHeight * 2/16,
                x + mCanvasWidth * 7/8,y + mCanvasHeight * 4/16);
        SubtitleImage.draw(canvas);
    }

}
