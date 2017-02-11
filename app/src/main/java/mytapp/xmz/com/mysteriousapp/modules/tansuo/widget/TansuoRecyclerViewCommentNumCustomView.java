package mytapp.xmz.com.mysteriousapp.modules.tansuo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Created by Administrator on 2016/11/14.
 */
public class TansuoRecyclerViewCommentNumCustomView extends ImageButton {

    private Paint ovalPaint;
    private Paint textPaint;
    private int num=0;

    public TansuoRecyclerViewCommentNumCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        ovalPaint = new Paint();
        ovalPaint.setStyle(Paint.Style.FILL);
        ovalPaint.setDither(true);
        ovalPaint.setAntiAlias(true);
        ovalPaint.setColor(Color.RED);

        textPaint = new Paint();
        textPaint.setDither(true);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(15);
        textPaint.setFakeBoldText(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float ovalLeft = getWidth()*4/8;
        float ovalTop = 0;
        float ovalRight = getWidth();
        float ovalBottom = getHeight()*4/8;
        RectF oval = new RectF(ovalLeft,ovalTop,ovalRight,ovalBottom);
        canvas.drawOval(oval,ovalPaint);

        String text =null;
        if(num >99){
            text = "99+";
        }else {
            text = num+"";
        }

        float textX = ovalLeft*1.5f;
        float textY = (ovalBottom-ovalTop)*2/3;
        canvas.drawText(text,textX,textY,textPaint);
    }

    public void setNum(int num) {
        this.num = num;
        invalidate();
    }
}
