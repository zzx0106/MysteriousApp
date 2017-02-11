package mytapp.xmz.com.mysteriousapp.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/16.
 */
public class TalkBean extends BmobObject {
    private String icon;
    private String username;
    private String talk;
    private String time;
    private String article_id;//根据id判断新闻唯一指定id
    public String getIcon() {
        return icon;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTalk() {
        return talk;
    }

    public void setTalk(String talk) {
        this.talk = talk;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
