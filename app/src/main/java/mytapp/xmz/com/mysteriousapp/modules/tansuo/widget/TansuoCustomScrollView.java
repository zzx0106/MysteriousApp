package mytapp.xmz.com.mysteriousapp.modules.tansuo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/11/17.
 */
public class TansuoCustomScrollView extends ScrollView {

    private TansuoCustomScrollViewChangeListener listener;

    public TansuoCustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public  interface TansuoCustomScrollViewChangeListener{
        void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

    public  void  setChangeListener(TansuoCustomScrollViewChangeListener listener){
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if(listener!=null){
            listener.onScrollChange(l,t,oldl,oldt);
        }
    }
}
