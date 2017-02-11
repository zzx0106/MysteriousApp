package mytapp.xmz.com.mysteriousapp.network;

import android.os.Handler;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;

/**
 * Created by se7en on 16/2/29. 网络请求的三个要素: 1.请求方式(GET,POST) 2.请求的url 3.参数
 */
public class HttpUtil {

    private static Handler mHandler;

    static {
        mHandler = new Handler();
    }

    /**
     * 执行网络请求操作
     *
     * @param method 请求方式(需要传入String类型的参数:"GET","POST")
     * @param url    请求的url
     * @param params 请求的参数
     */
    public static void doHttpReqeust(final String method, final String url,
                                     final HashMap<String, String> params, final BaseCallBack callBack) {
        HttpMethod m = HttpMethod.GET;
        RequestParams requestParams = new RequestParams(url);
        if ("POST".equals(method)) {
            m = HttpMethod.POST;
            Set<Map.Entry<String, String>> sets = params.entrySet();
            // 将Hashmap转换为string
            for (Map.Entry<String, String> entry : sets) {
                requestParams.addBodyParameter(entry.getKey(),entry.getValue());
            }
        }
        x.http().request(m, requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    callBack.success(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                postFailed(callBack);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    private static void postFailed(final BaseCallBack callBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(callBack!=null) {
                    callBack.failed(0, "请检查网络连接");
                }
            }
        });
    }


}
