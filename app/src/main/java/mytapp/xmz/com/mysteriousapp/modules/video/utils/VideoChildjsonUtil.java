package mytapp.xmz.com.mysteriousapp.modules.video.utils;

import org.json.JSONException;
import org.json.JSONObject;

import mytapp.xmz.com.mysteriousapp.modules.video.bean.VideoChildInfo;

/**
 * Created by Administrator on 2016/11/11.
 *
 *
 private String title;
 private String datetime;//时间long
 private String source;//作者
 private String click;//观看人数
 private String video_url;//播放链接
 private String play_time;//时长
 */
public class VideoChildjsonUtil {
    public static VideoChildInfo getVideoChildJsonList(String json) {
        VideoChildInfo videoChildInfo = new VideoChildInfo();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject data = jsonObject.getJSONObject("data");
            String title = data.getString("title");
            String datetime = data.getString("datetime");
            String source = data.getString("source");
            String click = data.getString("click");
            String video_url = data.getString("video_url");
            String play_time = data.getString("play_time");
            String share_img = data.getString("share_img");

            videoChildInfo.setSource(source);
            videoChildInfo.setTitle(title);
            videoChildInfo.setDatetime(datetime);
            videoChildInfo.setClick(click);
            videoChildInfo.setVideo_url(video_url);
            videoChildInfo.setPlay_time(play_time);
            videoChildInfo.setShare_img(share_img);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return videoChildInfo;
    }
}
