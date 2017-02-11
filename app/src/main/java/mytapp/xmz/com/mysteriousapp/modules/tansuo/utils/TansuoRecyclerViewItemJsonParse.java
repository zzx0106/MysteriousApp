package mytapp.xmz.com.mysteriousapp.modules.tansuo.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoBaseInfo;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoOneImageInfo;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoRecyclerViewItemInfo;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoThreeImageInfo;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoZeroImageInfo;

/**
 * Created by Administrator on 2016/11/12.
 */
public class TansuoRecyclerViewItemJsonParse {
    public static TansuoRecyclerViewItemInfo doParse(String jsonDate){
        List<TansuoBaseInfo> list =new ArrayList<>();
        TansuoRecyclerViewItemInfo tansuoRecyclerViewItemInfo =new TansuoRecyclerViewItemInfo();
        TansuoBaseInfo tansuoBaseInfo = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonDate);
            String data = jsonObject.getString("data");
            jsonObject = new JSONObject(data);

            String source = jsonObject.getString("source");
            String datetime = jsonObject.getString("datetime");
            String click = jsonObject.getString("click");
            String comment_sum = jsonObject.getString("comment_sum");
            String title = jsonObject.getString("title");
            String share_url = jsonObject.getString("share_url");
            String share_img = jsonObject.getString("share_img");
            String content = jsonObject.getString("content");
            String share_wx_url = jsonObject.getString("share_wx_url");

            tansuoRecyclerViewItemInfo.setShare_wx_url(share_wx_url);
            tansuoRecyclerViewItemInfo.setClick(click);
            tansuoRecyclerViewItemInfo.setContent(content);
            tansuoRecyclerViewItemInfo.setTitle(title);
            tansuoRecyclerViewItemInfo.setComment_sum(comment_sum);
            tansuoRecyclerViewItemInfo.setDatetime(datetime);
            tansuoRecyclerViewItemInfo.setSource(source);
            tansuoRecyclerViewItemInfo.setShare_img(share_img);
            tansuoRecyclerViewItemInfo.setShare_url(share_url);

            JSONArray jsonArray = jsonObject.getJSONArray("new_list");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    JSONArray article_thumb = object.getJSONArray("article_thumb");
                    if(article_thumb.length()==0){
                        tansuoBaseInfo = new TansuoZeroImageInfo();
                        TansuoZeroImageInfo zeroImageInfo = (TansuoZeroImageInfo) tansuoBaseInfo;
                        zeroImageInfo.setArticle_id(object.getString("article_id"));
                        zeroImageInfo.setArticle_title(object.getString("article_title"));
                        zeroImageInfo.setComment_sum(object.getString("comment_sum"));
                        zeroImageInfo.setItem_type(TansuoBaseInfo.Type.ZEROIMAGETYPE);
                        zeroImageInfo.setDatetime(object.getString("datetime"));
                        list.add(zeroImageInfo);
                    }else if(article_thumb.length()<3 &&article_thumb.length()>0){
                        tansuoBaseInfo = new TansuoOneImageInfo();
                        TansuoOneImageInfo oneImageInfo = (TansuoOneImageInfo) tansuoBaseInfo;
                        oneImageInfo.setArticle_id(object.getString("article_id"));
                        oneImageInfo.setArticle_title(object.getString("article_title"));
                        oneImageInfo.setComment_sum(object.getString("comment_sum"));
                        oneImageInfo.setItem_type(TansuoBaseInfo.Type.ONEIMAGETYPE);
                        oneImageInfo.setDatetime(object.getString("datetime"));
                        oneImageInfo.setImageUrl(article_thumb.getString(0));
                        list.add(oneImageInfo);
                    }else {
                        tansuoBaseInfo = new TansuoThreeImageInfo();
                        TansuoThreeImageInfo threeImageInfo = (TansuoThreeImageInfo) tansuoBaseInfo;
                        threeImageInfo.setArticle_id(object.getString("article_id"));
                        threeImageInfo.setArticle_title(object.getString("article_title"));
                        threeImageInfo.setComment_sum(object.getString("comment_sum"));
                        threeImageInfo.setItem_type(TansuoBaseInfo.Type.THREEIMAGETYPE);
                        threeImageInfo.setDatetime(object.getString("datetime"));
                        threeImageInfo.setCenterImageUrl(article_thumb.getString(1));
                        threeImageInfo.setLeftImageUrl(article_thumb.getString(0));
                        threeImageInfo.setRightImageUrl(article_thumb.getString(2));
                        list.add(threeImageInfo);
                    }

                }catch (Exception e){

                }
            }

            tansuoRecyclerViewItemInfo.setTansuoBaseInfoList(list);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return tansuoRecyclerViewItemInfo;
    }
}
