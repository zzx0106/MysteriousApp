package mytapp.xmz.com.mysteriousapp.modules.tuijian.bean;

/**
 * Created by WXW20147854 on 2016/11/18.
 */
public class ViewpagerData {
    private String view_title;
    private String view_image;

    public String getView_title() {
        return view_title;
    }

    public void setView_title(String view_title) {
        this.view_title = view_title;
    }

    public String getView_image() {
        return view_image;
    }

    public void setView_image(String view_image) {
        this.view_image = view_image;
    }

    public ViewpagerData() {
    }

    public ViewpagerData(String view_title, String view_image) {
        this.view_title = view_title;
        this.view_image = view_image;
    }
}
