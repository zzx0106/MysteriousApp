package mytapp.xmz.com.mysteriousapp.modules.video.bean;

/**
 * Created by Administrator on 2016/11/11.
 */
public class VideoChildInfo {

    private String title;
    private String datetime;//时间long
    private String source;//作者
    private String click;//观看人数
    private String video_url;//播放链接
    private String play_time;//时长
    private String share_img;

    public String getTitle() {
        return title;
    }

    public String getShare_img() {
        return share_img;
    }

    public void setShare_img(String share_img) {
        this.share_img = share_img;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getClick() {
        return click;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getPlay_time() {
        return play_time;
    }

    public void setPlay_time(String play_time) {
        this.play_time = play_time;
    }
}
