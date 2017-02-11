package mytapp.xmz.com.mysteriousapp.modules.tansuo.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
public class TansuoRecyclerViewItemInfo {
    private String source;
    private String datetime;
    private String click;
    private String collect_sum;
    private String content;
    private String title;
    private String share_url;
    private String share_img;
    private String comment_sum;
    private String share_wx_url;

    public String getShare_wx_url() {
        return share_wx_url;
    }

    public void setShare_wx_url(String share_wx_url) {
        this.share_wx_url = share_wx_url;
    }

    public String getComment_sum() {
        return comment_sum;
    }

    public void setComment_sum(String comment_sum) {
        this.comment_sum = comment_sum;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getShare_img() {
        return share_img;
    }

    public void setShare_img(String share_img) {
        this.share_img = share_img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // private String new_list;
    private List<TansuoBaseInfo> tansuoBaseInfoList;

    public List<TansuoBaseInfo> getTansuoBaseInfoList() {
        return tansuoBaseInfoList;
    }

    public void setTansuoBaseInfoList(List<TansuoBaseInfo> tansuoBaseInfoList) {
        this.tansuoBaseInfoList = tansuoBaseInfoList;
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

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getCollect_sum() {
        return collect_sum;
    }

    public void setCollect_sum(String collect_sum) {
        this.collect_sum = collect_sum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
