package mytapp.xmz.com.mysteriousapp.modules.video.dao;

import java.util.HashMap;

import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;
import mytapp.xmz.com.mysteriousapp.modules.video.bean.VideoChildInfo;
import mytapp.xmz.com.mysteriousapp.modules.video.utils.VideoChildjsonUtil;
import mytapp.xmz.com.mysteriousapp.network.HttpUtil;

/**
 * Created by Administrator on 2016/11/11.
 */
    //http://api.data.jun360.com/api/list
    //appnavId=26&now_page=1&plat=android&appId=6&apiCode=3&imei=000000000000000&version=20161025
public class VideoChilddao {
    public static void getVideoChildIJson(String article_id, final BaseCallBack callBack){
        String method="POST";
        String url="http://api.data.jun360.com/api/show";
        HashMap<String, String> params = new HashMap<>();
        params.put("articleId",article_id);
        params.put("articleType","3");
        params.put("adId","0");
        params.put("appnavId","115");
        params.put("plat","android");
        params.put("appId","6");
        params.put("apiCode","3");
        params.put("imei","000000000000000");
        params.put("version","20161025");

        HttpUtil.doHttpReqeust(method, url, params, new BaseCallBack() {
            @Override
            public void success(Object data) {
                if(callBack !=null){
                    VideoChildInfo info =  VideoChildjsonUtil.getVideoChildJsonList(data.toString());
                    callBack.success(info);
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                if(callBack!=null) {
                    callBack.failed(errorCode,data);
                }
            }
        });
    }
}
