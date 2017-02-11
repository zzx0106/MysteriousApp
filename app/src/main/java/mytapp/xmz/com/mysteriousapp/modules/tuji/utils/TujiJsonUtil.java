package mytapp.xmz.com.mysteriousapp.modules.tuji.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.modules.tuji.bean.TujiInfo;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class TujiJsonUtil {
    public static List<TujiInfo> JsonFormat(String json){
        List<TujiInfo> list = new ArrayList<>();
        try {
            TujiInfo info = null;
            JSONObject object = new JSONObject(json);
            JSONObject object1 = object.getJSONObject("data");
            JSONArray array = object1.getJSONArray("new_list");
            for (int i = 0; i < array.length(); i++) {
                info = new TujiInfo();
                JSONObject infoJson = array.getJSONObject(i);
                info.setArticle_thumb_sum(infoJson.getString("article_thumb_sum"));
                info.setArticle_title(infoJson.getString("article_title"));
                info.setComment_sum(infoJson.getString("comment_sum"));

                JSONArray array2 = infoJson.getJSONArray("article_thumb");
                info.setArticle_thumb(array2.getString(0));
                info.setArticle_id(infoJson.getString("article_id"));

                list.add(info);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
