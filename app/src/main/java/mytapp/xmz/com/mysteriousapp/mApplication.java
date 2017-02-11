package mytapp.xmz.com.mysteriousapp;

import android.app.Application;

import org.xutils.x;

import cn.bmob.v3.Bmob;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2016/11/12.
 */
public class mApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        //bmob初始化
        Bmob.initialize(getApplicationContext(),"8dd2c5275ebe00c1400f9711742c9d6a");

        //ShareSDK初始化
        ShareSDK.initSDK(getApplicationContext());
    }
}
