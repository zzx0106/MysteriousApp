package mytapp.xmz.com.mysteriousapp.modules.tuji.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.modules.tuji.bean.TujichildInfo;

/**
 * Created by Administrator on 2001/1/1 0001.
 */
public class TujiChildJsonUtil {
    public static List<TujichildInfo> JsonFormat(String json){
        List<TujichildInfo> list = new ArrayList<>();

        try {
            TujichildInfo info = null;
            JSONObject object = new JSONObject(json);
            JSONObject object1 = object.getJSONObject("data");
            /*String share_url = object1.getString("share_url");
            String share_img = object1.getString("share_img");*/
            String share_wx_url = object1.getString("share_wx_url");
            //Log.e("print", share_url+share_img+share_wx_url );
            JSONArray array = object1.getJSONArray("img_url");
            for (int i = 0; i < array.length(); i++) {
                info = new TujichildInfo();
                JSONObject subJson = array.getJSONObject(i);
                info.setId(subJson.getString("id"));
                info.setImage_desc(subJson.getString("image_desc"));
                info.setImage_title(subJson.getString("image_title"));
                info.setImage_url(subJson.getString("image_url"));
                info.setShare_wx_url(share_wx_url);
                list.add(info);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
