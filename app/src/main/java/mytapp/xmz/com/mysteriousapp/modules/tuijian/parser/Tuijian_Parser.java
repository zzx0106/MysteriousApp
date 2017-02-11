package mytapp.xmz.com.mysteriousapp.modules.tuijian.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.modules.tuijian.bean.Tuijian;

/**
 *  解析来自网络上的数据
 */
public class Tuijian_Parser {
    private List<Tuijian> lists;

    private OnCompleteParser completeParser;

    // 接口，用来回调数据
    public interface OnCompleteParser{
        void onCompleteData(List<Tuijian> lists_complete);
    }

    public void setOnCompleteParser(OnCompleteParser completeParser){
        completeParser.onCompleteData(lists);
    }

    public void parser(String jsonString){
        try {
            lists = new ArrayList<Tuijian>();

            JSONObject object = new JSONObject(jsonString);
            String data = object.getString("data");

            // 再次解析
            parserData(data);

            // 当数据解析完了就把List集合返回给调用者
            if(completeParser != null){
                completeParser.onCompleteData(lists);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 解析data，获取需要的数据
    private void parserData(String data){
        try {
            JSONObject object1 = new JSONObject(data);
            JSONArray jsonArray = object1.getJSONArray("new_list");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject object2 = jsonArray.getJSONObject(i);
                String article_id = object2.getString("article_id");
                String article_title = object2.getString("article_title");
                JSONArray article_thumb_array = object2.getJSONArray("article_thumb");
                String article_thumb = article_thumb_array.getString(0);
                String datetime = object2.getString("datetime");
                String comment_sum = object2.getString("comment_sum");

                // 讲数据保存到List集合中
                if(article_id.equals("0")){
                    continue;
                }
                else{
                    lists.add(new Tuijian(article_title, article_thumb, datetime, comment_sum, article_id));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
