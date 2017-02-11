package mytapp.xmz.com.mysteriousapp.modules.tuji.dao;

import java.util.HashMap;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;
import mytapp.xmz.com.mysteriousapp.modules.tuji.bean.TujiInfo;
import mytapp.xmz.com.mysteriousapp.network.HttpUtil;
import mytapp.xmz.com.mysteriousapp.modules.tuji.utils.TujiJsonUtil;

/**
 * Created by Administrator on 2016/11/12 0012.
 */
public class TujiDao {

    public static void requestTujiInfo(int page, final BaseCallBack callBack){
        String url = "http://api.data.jun360.com/api/list";
        HashMap<String,String> map = new HashMap();

        map.put("appnavId","97");
        map.put("now_page",page+"");
        map.put("articleType","2");
        map.put("plat","android");
        map.put("appId","6");
        HttpUtil.doHttpReqeust("POST", url, map, new BaseCallBack() {
            @Override
            public void success(Object data) {
                if(callBack!=null){
                    List<TujiInfo> list = TujiJsonUtil.JsonFormat(data.toString());
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
