package mytapp.xmz.com.mysteriousapp.modules.tuji.dao;

import java.util.HashMap;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;
import mytapp.xmz.com.mysteriousapp.modules.tuji.bean.TujichildInfo;
import mytapp.xmz.com.mysteriousapp.modules.tuji.utils.TujiChildJsonUtil;
import mytapp.xmz.com.mysteriousapp.network.HttpUtil;

/**
 * Created by Administrator on 2001/1/1 0001.
 */
public class TujiChildDao {
    public static void requestTujiChildInfo(String id, final BaseCallBack callBack){
        String url = "http://api.data.jun360.com/api/show";
        HashMap<String,String> map = new HashMap();

        map.put("articleId",id);
        map.put("appnavId","97");
        map.put("articleType","2");
        map.put("adId","");
        map.put("plat","android");
        map.put("appId","6");
        HttpUtil.doHttpReqeust("POST", url, map, new BaseCallBack() {
            @Override
            public void success(Object data) {
                if(callBack!=null){
                    List<TujichildInfo> list = TujiChildJsonUtil.JsonFormat(data.toString());
                    callBack.success(list);
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                if(callBack!=null){
                    callBack.failed(errorCode,data);
                }
            }
        });
    }
}
