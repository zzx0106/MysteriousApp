package mytapp.xmz.com.mysteriousapp.modules.qiwen.bean;

/**
 * Created by Administrator on 2016/11/11.
 */
public class QWItemChildInfo {

    private String title;
    private String source;//用户昵称
    private String datetime;//时间
    private String laud;//喜欢
    private String stamp;//踩
    private String collect_sum;//阅读人数
    private String share_wx_url;//webview链接
    private String content;//内容
    private String article_id;

    public String getContent() {
        return content;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getLaud() {
        return laud;
    }

    public void setLaud(String laud) {
        this.laud = laud;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getCollect_sum() {
        return collect_sum;
    }

    public void setCollect_sum(String collect_sum) {
        this.collect_sum = collect_sum;
    }

    public String getShare_wx_url() {
        return share_wx_url;
    }

    public void setShare_wx_url(String share_wx_url) {
        this.share_wx_url = share_wx_url;
    }
}
