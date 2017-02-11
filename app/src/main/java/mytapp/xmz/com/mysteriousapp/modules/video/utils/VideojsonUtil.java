package mytapp.xmz.com.mysteriousapp.modules.video.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.modules.video.bean.VideoInfo;

/**
 * Created by Administrator on 2016/11/11.
 */
public class VideojsonUtil {
    public static List<VideoInfo> getVideoJsonList(String json) {
        List<VideoInfo> list = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray new_list = data.getJSONArray("new_list");
            for (int i = 0; i < new_list.length(); i++) {
                VideoInfo videoInfo = new VideoInfo();
                JSONObject object = new_list.getJSONObject(i);
                String comment_sum = object.getString("comment_sum");
                String id = object.getString("id");
                //String app_nav_id = object.getString("app_nav_id");
                String article_thumb = object.getJSONArray("article_thumb").getString(0);
                String article_title = object.getString("article_title");
                String datetime = object.getString("datetime");
                String article_id = object.getString("article_id");

                videoInfo.setId(id);
                videoInfo.setArticle_thumb(article_thumb);
                videoInfo.setArticle_title(article_title);
                videoInfo.setDatetime(datetime);
                videoInfo.setComment_sum(comment_sum);
                videoInfo.setArticle_id(article_id);
                list.add(videoInfo);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
