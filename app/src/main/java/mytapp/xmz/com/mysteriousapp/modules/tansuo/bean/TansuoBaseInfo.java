package mytapp.xmz.com.mysteriousapp.modules.tansuo.bean;

/**
 * Created by Administrator on 2016/11/12.
 */
public class TansuoBaseInfo {

    /*
    *  {
                "id": "121666",
                "app_id": "6",
                "app_nav_id": "25",
                "app_nav_name": "探索",
                "article_id": "3964164",
                "source": "Kailey",
                "mark": "",
                "article_type": "1",
                "ad_id": "0",
                "article_title": "搞笑GIF：木乃伊的制作方式",
                "article_thumb": [
                    "http://pic.jun360.com/uploads/month_1611/20161112001802341.jpg@!collect_list_style"
                ],
                "article_thumb_sum": "",
                "description": "不要和妹纸玩真格的▼摩托司机这么狠▼你看清楚了么▼木乃伊的制作方式▼这下子就尴尬了▼看到你这样，我就放心了▼这要是突然刹车呢▼难上课不让带手",
                "datetime": "9小时前",
                "article_order": "0",
                "comment_sum": "0",
                "laud": "764",
                "stamp": "23",
                "collect_sum": "753",
                "share_abstract": "",
                "share_url": "",
                "share_img": "",
                "share_wx_url": "",
                "click": "1363",
                "jump_url": "",
                "shareMoney": "0.01",
                "shareCount": 469328
            }
    * */
    //类型
    private int item_type;
    //请求内容id号
    private String article_id;
    //标题
    private String article_title;
    //时间
    private String datetime;
    //评论总数
    private String comment_sum;



    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }


    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
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

    public int getItem_type() {
        return item_type;
    }

    public void setItem_type(int item_type) {
        this.item_type = item_type;
    }

   public interface Type{
        int ONEIMAGETYPE=1;
        int THREEIMAGETYPE=3;
        int ZEROIMAGETYPE=0;
        int FOOTERTYPE=6;
    }
}
