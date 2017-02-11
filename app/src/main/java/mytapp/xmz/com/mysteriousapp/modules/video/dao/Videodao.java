package mytapp.xmz.com.mysteriousapp.modules.video.dao;

import java.util.HashMap;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;
import mytapp.xmz.com.mysteriousapp.modules.video.bean.VideoInfo;
import mytapp.xmz.com.mysteriousapp.modules.video.utils.VideojsonUtil;
import mytapp.xmz.com.mysteriousapp.network.HttpUtil;

/**
 * Created by Administrator on 2016/11/11.
 */
    //http://api.data.jun360.com/api/list
    //appnavId=26&now_page=1&plat=android&appId=6&apiCode=3&imei=000000000000000&version=20161025
public class Videodao {
    public static void getVideoJson(String now_page, final BaseCallBack callBack){
        String method="POST";
        String url="http://api.data.jun360.com/api/list";
        HashMap<String, String> params = new HashMap<>();
        params.put("appnavId","115");
        params.put("now_page",now_page);
        params.put("plat","android");
        params.put("appId","6");
        params.put("apiCode","3");
        params.put("imei","000000000000000");
        params.put("version","20161025");

        HttpUtil.doHttpReqeust(method, url, params, new BaseCallBack() {
            @Override
            public void success(Object data) {
                if(callBack !=null){
                    List<VideoInfo> list = VideojsonUtil.getVideoJsonList(data.toString());
                    callBack.success(list);
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
