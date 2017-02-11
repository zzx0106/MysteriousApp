package mytapp.xmz.com.mysteriousapp.modules.video.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;
import mytapp.xmz.com.mysteriousapp.modules.video.bean.VideoChildInfo;
import mytapp.xmz.com.mysteriousapp.modules.video.bean.VideoInfo;
import mytapp.xmz.com.mysteriousapp.modules.video.dao.VideoChilddao;

/**
 * Created by Administrator on 2016/11/14.
 */
public class Video_Adapter extends BaseAdapter {

    private final Context context;
    private final List<VideoInfo> list;
    private String url ;

    public Video_Adapter(Context context, List<VideoInfo> list) {
        this.context =context;
        this.list =list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder viewholder = null;
        if (convertView == null) {
            viewholder = new viewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.video_item_layout, parent, false);
            viewholder.video_child_content = (TextView) convertView.findViewById(R.id.video_child_content);
            viewholder.video_child_title = (TextView) convertView.findViewById(R.id.video_child_title);
            viewholder.view_child_time = (TextView) convertView.findViewById(R.id.view_child_time);
            viewholder.video_child_webview = (WebView) convertView.findViewById(R.id.video_child_webview);
            viewholder.video_child_play = (ImageView) convertView.findViewById(R.id.video_child_play);
            viewholder.video_child_image_content = (ImageView) convertView.findViewById(R.id.video_child_image_content);
//            viewholder.video_child_videoview = (VideoView) convertView.findViewById(R.id.video_child_videoview);
            convertView.setTag(viewholder);
        } else {
            viewholder = (viewHolder) convertView.getTag();
        }

        Picasso.with(context).load(list.get(position).getArticle_thumb()).into(viewholder.video_child_image_content);
        setAndlodData(viewholder,position);
        return convertView;
    }
//网络加载
    public void setAndlodData(final viewHolder vh, int position) {

        VideoChilddao.getVideoChildIJson(list.get(position).getArticle_id(), new BaseCallBack() {
            @Override
            public void success(Object data) {
                VideoChildInfo info = (VideoChildInfo) data;
                vh.video_child_title.setText(info.getTitle());
                vh.video_child_content.setText("作者："+info.getSource()+"     " +
                        "时间："+info.getDatetime()+"      "+info.getClick()+"次观看");
                vh.view_child_time.setText(info.getPlay_time());
                url = info.getVideo_url();
                vh.video_child_webview.getSettings().setJavaScriptEnabled(true);
                vh.video_child_webview.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });
            }
            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(context,"数据获取失败",Toast.LENGTH_LONG).show();
            }
        });
        vh.video_child_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.view_child_time.setVisibility(View.INVISIBLE);
                vh.video_child_image_content.setVisibility(View.GONE);
                vh.video_child_webview.setVisibility(View.VISIBLE);
                vh.video_child_play.setVisibility(View.GONE);
//                vh.video_child_videoview.setVisibility(View.VISIBLE);
//                vh.video_child_videoview.setVideoURI(Uri.parse(url));
//                vh.video_child_videoview.start();
                vh.video_child_webview.loadUrl(url);
            }
        });
    }
    class viewHolder{
        TextView video_child_title,video_child_content,view_child_time;
        ImageView video_child_play,video_child_image_content;
//        VideoView video_child_videoview;
        WebView video_child_webview;
    }
}
