package mytapp.xmz.com.mysteriousapp.modules.qiwen.utils;

import org.json.JSONException;
import org.json.JSONObject;

import mytapp.xmz.com.mysteriousapp.modules.qiwen.bean.QWItemChildInfo;

/**
 * Created by Administrator on 2016/11/11.
 *
 *
 private String title;
 private String source;//用户昵称
 private long datetime;//时间
 private String laud;//喜欢
 private String stamp;//踩
 private String collect_sum;//阅读人数
 private String share_wx_url;//webview链接
 */
public class QWChildjsonUtil {
    public static QWItemChildInfo getQWChildJsonList(String json) {
        QWItemChildInfo qwItemChildInfo = new QWItemChildInfo();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.getJSONObject("data");
            String title = data.getString("title");
            String source = data.getString("source");
            String datetime = data.getString("datetime");
            String laud = data.getString("laud");
            String stamp = data.getString("stamp");
            String collect_sum = data.getString("collect_sum");
            String share_wx_url = data.getString("share_wx_url");
            String content = data.getString("content");
            String article_id = data.getString("article_id");

            qwItemChildInfo.setTitle(title);
            qwItemChildInfo.setCollect_sum(collect_sum);
            qwItemChildInfo.setDatetime(datetime);
            qwItemChildInfo.setLaud(laud);
            qwItemChildInfo.setSource(source);
            qwItemChildInfo.setShare_wx_url(share_wx_url);
            qwItemChildInfo.setStamp(stamp);
            qwItemChildInfo.setContent(content);
            qwItemChildInfo.setArticle_id(article_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return qwItemChildInfo;
    }
}
