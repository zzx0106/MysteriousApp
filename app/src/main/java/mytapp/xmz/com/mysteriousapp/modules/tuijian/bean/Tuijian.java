package mytapp.xmz.com.mysteriousapp.modules.tuijian.bean;

/**
 *  article_title： 标题
 *  article_thumb： 图片的网址
 *  datetime：      上传的时间
 *  comment_sum：   评论总量
 *  article_id：    该条目的Id，用于进入到子界面
 */
public class Tuijian {
    private String article_title;
    private String article_thumb;
    private String datetime;
    private String comment_sum;
    private String article_id;

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_thumb() {
        return article_thumb;
    }

    public void setArticle_thumb(String article_thumb) {
        this.article_thumb = article_thumb;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getComment_sum() {
        return comment_sum;
    }

    public void setComment_sum(String comment_sum) {
        this.comment_sum = comment_sum;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public Tuijian() {
    }

    public Tuijian(String article_title, String article_thumb, String datetime, String comment_sum, String article_id) {
        this.article_title = article_title;
        this.article_thumb = article_thumb;
        this.datetime = datetime;
        this.comment_sum = comment_sum;
        this.article_id = article_id;
    }
}
