package mytapp.xmz.com.mysteriousapp.modules.tuijian.parser;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *  推荐子界面的解析代码
 */
public class TuijianChild_Parser {
    // 解析出来的数据存储在Map集合中
    private Map<String, String> map;

    private OnChildCompleteParserListener listener;

    // 解析完成的回调接口
    public interface OnChildCompleteParserListener{
        void onCompleteListener(Map<String, String> map);
    }

    public void setOnChildCompleteParserListener(OnChildCompleteParserListener listener){
        listener.onCompleteListener(map);
    }

    public void parser(String jsonString){
        try {
            map = new HashMap<String, String>();

            JSONObject object1 = new JSONObject(jsonString);
            JSONObject data = object1.getJSONObject("data");

            String title = data.getString("title");
            String source = data.getString("source");
            String datetime = data.getString("datetime");
            String click = data.getString("click");
            String content = data.getString("content");
            String share_wx_url = data.getString("share_wx_url");
            Log.e("title", title);
            Log.e("source", source);
            Log.e("datetime", datetime);
            Log.e("click", click);
            Log.e("content", content);
            Log.e("share_wx_url", share_wx_url);

            map.put("title", title);
            map.put("source", source);
            map.put("datetime", datetime);
            map.put("click", click);
            map.put("content", content);
            map.put("share_wx_url", share_wx_url);

            // 解析完成之后的回调方法，通知已经解析完成
            if(listener != null){
                listener.onCompleteListener(map);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
