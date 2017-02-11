package mytapp.xmz.com.mysteriousapp.modules.tuji.bean;

/**
 * Created by Administrator on 2001/1/1 0001.
 */
public class TujichildInfo {
    /**
     * "data": {
     "img_url": [
     {
     "id": "103421",
     "image_url": "http://image.pool.jun360.com/uploads/month_1605/20160524011801670.jpg",
     "image_title": "一家三口河边捡神秘箱：撬开后吓一跳",
     "image_desc": "　　罗宾和他的两个孩子发现了这个宝箱",
     "order": "1",
     "download_count": "0"
     },
     */
    private String id;
    private String image_url;
    private String image_title;
    private String image_desc;
    private String share_wx_url;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String image_title) {
        this.image_title = image_title;
    }

    public String getImage_desc() {
        return image_desc;
    }

    public void setImage_desc(String image_desc) {
        this.image_desc = image_desc;
    }

    public String getShare_wx_url() {
        return share_wx_url;
    }

    public void setShare_wx_url(String share_wx_url) {
        this.share_wx_url = share_wx_url;
    }
}
