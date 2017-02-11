package mytapp.xmz.com.mysteriousapp.modules.tuji.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mytapp.xmz.com.mysteriousapp.R;

/**
 * Created by Administrator on 2001/1/2 0002.
 */
public class MyTujiToast {
    private MyTujiToast(){

    }

    private static MyTujiToast myTujiToast;

    private Toast toast;

    public static MyTujiToast createToast(){
        if(myTujiToast==null){
            myTujiToast = new MyTujiToast();
        }
        return myTujiToast;
    }

    public void ShowMyToast(Context context, ViewGroup root,
                            String text, boolean imgshow, int imgid){
        View view = LayoutInflater.from(context).inflate(R.layout.tuji_toast_layout,root);
        ImageView imageView = (ImageView) view.findViewById(R.id.tuji_toast_img);
        TextView textView = (TextView) view.findViewById(R.id.tuji_toast_text);
        if(imgshow){
            imageView.setImageResource(imgid);
        }else {
            imageView.setVisibility(View.GONE);
        }
        textView.setText(text);
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
