package mytapp.xmz.com.mysteriousapp.modules.qiwen.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.modules.qiwen.bean.QWItemInfo;

/**
 * Created by Administrator on 2016/11/11.
 */
public class QWjsonUtil {
    public static List<QWItemInfo> getQWJsonList(String json) {
        List<QWItemInfo> list = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray new_list = data.getJSONArray("new_list");
            for (int i = 0; i < new_list.length(); i++) {
                QWItemInfo qwItemInfo = new QWItemInfo();
                JSONObject object = new_list.getJSONObject(i);
                String comment_sum = object.getString("comment_sum");
                String id = object.getString("id");
                String app_nav_id = object.getString("app_nav_id");
                JSONArray article_thumb = object.getJSONArray("article_thumb");
                String article_title = object.getString("article_title");
                String datetime = object.getString("datetime");
                String article_id = object.getString("article_id");
                String article_type = object.getString("article_type");


                if (article_thumb.length() < 3) {
                    qwItemInfo.setId(id);
                    qwItemInfo.setApp_id("6");
                    qwItemInfo.setApp_nav_id("26");
                    qwItemInfo.setArticle_thumb(article_thumb);
                    qwItemInfo.setArticle_title(article_title);
                    qwItemInfo.setDatetime(datetime);
                    qwItemInfo.setComment_sum(comment_sum);
                    qwItemInfo.setType(0);
                    qwItemInfo.setArticle_id(article_id);
                    list.add(qwItemInfo);
                } else {
                    qwItemInfo.setId(id);
                    qwItemInfo.setApp_id("6");
                    qwItemInfo.setApp_nav_id("26");
                    qwItemInfo.setArticle_thumb(article_thumb);
                    qwItemInfo.setArticle_title(article_title);
                    qwItemInfo.setDatetime(datetime);
                    qwItemInfo.setComment_sum(comment_sum);
                    qwItemInfo.setType(1);
                    qwItemInfo.setArticle_id(article_id);
                    list.add(qwItemInfo);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
