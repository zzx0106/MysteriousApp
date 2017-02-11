package mytapp.xmz.com.mysteriousapp.modules.tuji.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2001/1/1 0001.
 */
public class CommentButton extends ImageView {

    private Paint paint;
    private Paint tpaint;
    private int width;
    private int height;
    private int Count;

    public CommentButton(Context context) {
        this(context,null);

    }

    public CommentButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public CommentButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        paint = new Paint();
        paint.setARGB(220,213,67,0);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        tpaint = new Paint();
        tpaint.setDither(true);
        tpaint.setStrokeWidth(1);
        tpaint.setTextSize(15);
        tpaint.setAntiAlias(true);
        tpaint.setColor(Color.WHITE);
        tpaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = measureDimension(100, widthMeasureSpec);
        height = measureDimension(100, heightMeasureSpec);
        setMeasuredDimension(width, height);


    }

    private int measureDimension(int defaultSize, int measureSpec) {
        int result;

         int specMode = MeasureSpec.getMode(measureSpec);
            int specSize = MeasureSpec.getSize(measureSpec);

          if(specMode == MeasureSpec.EXACTLY){
                       result = specSize;
             }else{
                result = defaultSize;   //UNSPECIFIED
              if(specMode == MeasureSpec.AT_MOST){
                  result = Math.min(result, specSize);
              }
          }
               return result;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(Count>0) {
            canvas.drawCircle(width - 20, height / 4, 12, paint);
            if(Count<10) {
                canvas.drawText(Count+"", width - 23, height / 4 + 5, tpaint);
            }else if(Count>=10&&Count<100) {
                canvas.drawText(Count+"",width-20-7,height/4+5,tpaint);
            }else {
                canvas.drawText("99+",width-30,height/4+5,tpaint);
            }
        }

    }

    public void setCount(int Count){
        this.Count = Count;
        invalidate();
    }
}
