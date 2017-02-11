package mytapp.xmz.com.mysteriousapp.widget;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/11/14.
 */
public class MyDrawerLayout extends DrawerLayout {


    public interface getPagerPosition{
        int getPager();
    }


    public void setGetPosition(getPagerPosition getPosition) {
        this.getPosition = getPosition;
    }

    private getPagerPosition getPosition;

    public MyDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean flage = listener.flage();
        if(flage){
            return super.onInterceptTouchEvent(ev);
        }else if(getPosition!=null && getPosition.getPager()==0 ){
            return super.onInterceptTouchEvent(ev);
        }else {
            return flage;
        }

    }

    public interface onStateEvemtListener{
        boolean flage();
    }

    public void setOnStateEvemtListener(onStateEvemtListener listener) {
        this.listener = listener;
    }

    private onStateEvemtListener listener;

}