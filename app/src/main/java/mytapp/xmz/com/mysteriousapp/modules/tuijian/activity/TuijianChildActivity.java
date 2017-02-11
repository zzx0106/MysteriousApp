package mytapp.xmz.com.mysteriousapp.modules.tuijian.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.activity.BaseActivity;
import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;
import mytapp.xmz.com.mysteriousapp.baseshare.BaseShareUtil;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.activity.QiWenTalkActivity;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.activity.TansuoCommentActivity;
import mytapp.xmz.com.mysteriousapp.modules.tuijian.parser.TuijianChild_Parser;
import mytapp.xmz.com.mysteriousapp.modules.tuijian.widget.MyScrollView;
import mytapp.xmz.com.mysteriousapp.network.HttpUtil;
import mytapp.xmz.com.mysteriousapp.widget.GifView;

/**
 * 子界面代码
 */
public class TuijianChildActivity extends BaseActivity {

    // 请求数据的URL
    private String url = "http://api.data.jun360.com/api/show";
    // 主界面传递过来的条目的Id，用来请求数据
    private String articleId;
    // 分享到微信的URL
    private String share_wx_url;
    // 分享到微信的标题
    private String sharetitle;
    // 返回键
    private ImageView tuijian_child_back;
    // 标题
    private TextView tuijian_child_title;
    // 作者
    private TextView tuijian_child_source;
    // 日期
    private TextView tuijian_child_datetime;
    // 点击量
    private TextView tuijian_child_click;
    private MyScrollView tuijian_child_scrollview;
    private WebView tuijian_child_webview;
    private LinearLayout tuijian_child_ll;


    // 播放帧动画的控件
    private GifView tuijian_animation;
    // 帧动画
    private AnimationDrawable animation;
    private ImageView tuijian_child_content_share;
    private Button tuijian_child_content_chat;
    private ImageView tuijian_child_item_content_customView;
    private ImageView tuijian_child__item_content_collect;
    private ImageView tuijian_child_item_shareToAll;

    @Override
    protected void findViews() {
        tuijian_child_back = (ImageView) findViewById(R.id.tuijian_child_back);
        tuijian_child_ll = (LinearLayout) findViewById(R.id.tuijian_child_ll);
        tuijian_child_title = (TextView) findViewById(R.id.tuijian_child_title);
        tuijian_child_source = (TextView) findViewById(R.id.tuijian_child_source);
        tuijian_child_datetime = (TextView) findViewById(R.id.tuijian_child_datetime);
        tuijian_child_click = (TextView) findViewById(R.id.tuijian_child_click);
        tuijian_child_scrollview = (MyScrollView) findViewById(R.id.tuijian_child_scrollview);
        tuijian_child_webview = (WebView) findViewById(R.id.tuijian_child_webview);
        tuijian_animation = (GifView) findViewById(R.id.tuijian_animation);

        tuijian_child_content_share = (ImageView) findViewById(R.id.tuijian_child_content_share);
        tuijian_child_content_chat = (Button) findViewById(R.id.tuijian_child_content_chat);
        tuijian_child_item_content_customView = (ImageView) findViewById(R.id.tuijian_child_item_content_customView);
        tuijian_child__item_content_collect = (ImageView) findViewById(R.id.tuijian_child__item_content_collect);
        tuijian_child_item_shareToAll = (ImageView) findViewById(R.id.tuijian_child_item_shareToAll);
    }

    @Override
    protected void initEvent() {
        //TODO 设置返回键的监听
        tuijian_child_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //TODO 内容进行滚动时，标题那一栏的动画效果
        // 由于：如果在惯性滚动时到达了ScrollView的顶部或者顶部，会出现回弹，所以应该设置要移动多长才进行动画
        // 只要设置的数值比回弹距离要大就可以了
        tuijian_child_scrollview.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollView scrollView, int l, int t, int oldl, int oldt) {
                // 上拉时，栏目控件向上进行平移动画，并隐藏
                if (t - oldt > 10) {
                    if (tuijian_child_ll.getVisibility() == View.VISIBLE) {
                        TranslateAnimation animation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0,
                                TranslateAnimation.RELATIVE_TO_SELF, 0,
                                TranslateAnimation.RELATIVE_TO_SELF, 0,
                                TranslateAnimation.RELATIVE_TO_SELF, -1);
                        animation.setDuration(1000);
                        animation.setFillAfter(true);
                        tuijian_child_ll.startAnimation(animation);
                        tuijian_child_ll.setVisibility(View.GONE);
                    }

                }
                // 下拉时，栏目控件向下进行平移动画，并显示
                if (t - oldt < -10) {
                    Log.e("scroll", "下拉");
                    if (tuijian_child_ll.getVisibility() == View.GONE) {
                        TranslateAnimation animation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0,
                                TranslateAnimation.RELATIVE_TO_SELF, 0,
                                TranslateAnimation.RELATIVE_TO_SELF, -1,
                                TranslateAnimation.RELATIVE_TO_SELF, 0);
                        animation.setDuration(1000);
                        animation.setFillAfter(true);
                        tuijian_child_ll.startAnimation(animation);
                        tuijian_child_ll.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        // 按钮的分享事件
        tuijian_child_content_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseShareUtil.showShare(
                        TuijianChildActivity.this,
                        sharetitle,
                        null,
                        "来自于奇闻世界的分享",
                        null,
                        share_wx_url,
                        null, null, null);
            }
        });
        tuijian_child_item_shareToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseShareUtil.showShare(
                        TuijianChildActivity.this,
                        sharetitle,
                        null,
                        "来自于奇闻世界的分享",
                        null,
                        share_wx_url,
                        null, null, null);
            }
        });

        //TODO tuijian_child_content_chat的点击跳转页面
        tuijian_child_content_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),QiWenTalkActivity.class);
                intent.putExtra("article_id",articleId);
                startActivity(intent);
            }
        });

        //TODO tuijian_child_item_content_customView的点击跳转页面
        tuijian_child_item_content_customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),TansuoCommentActivity.class);
                intent.putExtra("share_wx_url",share_wx_url);
                intent.putExtra("sharetitle",sharetitle);
                intent.putExtra("articleId",articleId);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void init() {
        //TODO 获取主界面传递过来的条目的Id
        Intent intent = getIntent();
        articleId = intent.getStringExtra("article_id");
        Log.e("articleId", articleId);

    }

    @Override
    protected void loadData() {
        initData();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.tuijian_child;
    }

    //TODO 加载推荐子界面的数据
    public void initData() {
        // articleId=1022749&
        // articleType=1&
        // adId=0&
        // appnavId=108&
        // plat=android&
        // appId=6&
        // apiCode=3&
        // imei=000000000000000&
        // version=20161025
        //TODO 进行POST请求数据
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("articleId", articleId);
        map.put("articleType", "1");
        map.put("adId", "0");
        map.put("appnavId", "108");
        map.put("plat", "android");
        map.put("appId", "6");
        map.put("apiCode", "3");
        map.put("imei", "000000000000000");
        map.put("version", "20161025");
        HttpUtil.doHttpReqeust("POST", url, map, new BaseCallBack() {
            @Override
            public void success(Object data) {
                // 从网络上下载来的数据
                String jsonString = (String) data;

                //TODO 解析数据
                TuijianChild_Parser parser = new TuijianChild_Parser();
                parser.parser(jsonString);

                //TODO 当解析完成时会调用
                parser.setOnChildCompleteParserListener(new TuijianChild_Parser.OnChildCompleteParserListener() {
                    @Override
                    public void onCompleteListener(Map<String, String> map) {
                        // 解析完成之后关闭动画，设置数据
                        tuijian_animation.setVisibility(View.GONE);

                        // 微信分享的URL
                        share_wx_url = map.get("share_wx_url");

                        tuijian_child_title.setText(map.get("title"));
                        tuijian_child_source.setText(map.get("source"));
                        SimpleDateFormat sdf = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
                        sdf.applyPattern("yyyy.MM.dd");
                        String time = sdf.format(new Date(Long.parseLong(map.get("datetime")) * 1000));
                        tuijian_child_datetime.setText(time);
                        tuijian_child_click.setText(map.get("click") + "人已阅读");
                        tuijian_child_webview.loadDataWithBaseURL(null, map.get("content"), "text/html", "utf-8", null);
                    }
                });
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(TuijianChildActivity.this, "网络不给力...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
