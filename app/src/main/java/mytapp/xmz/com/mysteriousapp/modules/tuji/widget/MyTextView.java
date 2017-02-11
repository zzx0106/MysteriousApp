package mytapp.xmz.com.mysteriousapp.modules.tuji.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/12 0012.
 */
public class MyTextView extends TextView {

    private float downY;
    private float FACTOR;

    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHeight(200);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float totalY = getHeight();
        FACTOR = totalY - 20;
        switch (event.getAction()) {
            case  MotionEvent.ACTION_DOWN:
                downY = event.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:
                float nowY = event.getRawY();
                float changeY = nowY - downY;
                downY = nowY;
                if ( -changeY+getHeight()>340) {
                    changeY = 0;
                }
                if ( -changeY+getHeight()<50){
                    changeY = 0;
                }

                setHeight((int) -changeY+getHeight());

                break;



        }
        return true;
    }

}
