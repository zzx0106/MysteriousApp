package mytapp.xmz.com.mysteriousapp.modules.video.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/11.
 * java.io.NotSerializableException: org.json.JSONArray
 1614-王雄伟 15:37:00
 将对象存入或者读取sd卡，需要序列化，在那个对象还有类要序列化。

 （1）如果对象里面含有JSONArray的话，存入是会报java.io.NotSerializableException: org.json.JSONArray

 两种解决方法：

 1.public class xxxx extends JSONArray implements Serializable

 2.将对象里面JSONArray装成String ，在前段获取的数据也.getString("xxx")

 （2）如果在存入时就是因为序列化的问题，读取的时候会提示：java.io.WriteAbortedException: Read an exception; java.io.NotSerializableException: org.json.JSONArray

 这句话的意思就是，你的对象里面含有JSONArray,但是JSONArray不可以序列化，此刻便可以使用之前1.2.的两种方法
 */
public class VideoInfo implements Serializable{
    private String id;
    private String article_id;//子界面连接需要
    private String article_thumb;//图片
    private String article_title;//title
    private String comment_sum;//评论数
    private String datetime;//时间10-29 04:10

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getArticle_thumb() {
        return article_thumb;
    }

    public void setArticle_thumb(String article_thumb) {
        this.article_thumb = article_thumb;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getComment_sum() {
        return comment_sum;
    }

    public void setComment_sum(String comment_sum) {
        this.comment_sum = comment_sum;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
