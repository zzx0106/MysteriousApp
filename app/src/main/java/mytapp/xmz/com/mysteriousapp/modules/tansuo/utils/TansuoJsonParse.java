package mytapp.xmz.com.mysteriousapp.modules.tansuo.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoBaseInfo;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoOneImageInfo;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoThreeImageInfo;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoZeroImageInfo;

/**
 * Created by Administrator on 2016/11/12.
 */
public class TansuoJsonParse {
    public static List<TansuoBaseInfo> doParse(String jsonDate){
        List<TansuoBaseInfo> list =new ArrayList<>();
        TansuoBaseInfo info =null;
        try {
            JSONObject jsonObject = new JSONObject(jsonDate);
            String data = jsonObject.getString("data");
            jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("new_list");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    JSONArray article_thumb = object.getJSONArray("article_thumb");
                    //String article_thumb = object.getString("article_thumb");
                    //String[] split = article_thumb.split(",");
                   // Log.e("................", split[0]+".........");
                    if(article_thumb.length()==0){
                        info = new TansuoZeroImageInfo();
                        TansuoZeroImageInfo zeroImageInfo = (TansuoZeroImageInfo) info;
                        zeroImageInfo.setArticle_id(object.getString("article_id"));
                        zeroImageInfo.setArticle_title(object.getString("article_title"));
                        zeroImageInfo.setComment_sum(object.getString("comment_sum"));
                        zeroImageInfo.setItem_type(TansuoBaseInfo.Type.ZEROIMAGETYPE);
                        zeroImageInfo.setDatetime(object.getString("datetime"));
                        list.add(zeroImageInfo);
                    }else if(article_thumb.length()<3 &&article_thumb.length()>0){
                        info = new TansuoOneImageInfo();
                        TansuoOneImageInfo oneImageInfo = (TansuoOneImageInfo) info;
                        oneImageInfo.setArticle_id(object.getString("article_id"));
                        oneImageInfo.setArticle_title(object.getString("article_title"));
                        oneImageInfo.setComment_sum(object.getString("comment_sum"));
                        oneImageInfo.setItem_type(TansuoBaseInfo.Type.ONEIMAGETYPE);
                        oneImageInfo.setDatetime(object.getString("datetime"));

                        oneImageInfo.setImageUrl(article_thumb.getString(0));
                        list.add(oneImageInfo);
                    }else {
                        info = new TansuoThreeImageInfo();
                        TansuoThreeImageInfo threeImageInfo = (TansuoThreeImageInfo) info;
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


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }
}
