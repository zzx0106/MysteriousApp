package mytapp.xmz.com.mysteriousapp.modules.qiwen.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.activity.BaseActivity;
import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;
import mytapp.xmz.com.mysteriousapp.baseshare.BaseShareUtil;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.bean.QWItemChildInfo;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.dao.QWChilddao;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.widget.MyScrollview;
import mytapp.xmz.com.mysteriousapp.widget.GifView;

/**
 * Created by Administrator on 2016/11/12.
 */
public class QiWenChildActivity extends BaseActivity {

    private FrameLayout frameLayout;
    private TextView qiwen_child_title,
            qiwen_child_comment_sum,
            qiwen_child_content;
    private String app_nav_id;
    private String article_id;
    private String article_type;

    private TextView qiwen_child_jumpto_talk;
    private ImageView qiwen_child_jumpto_talk_list;
    private ImageButton qiwen_child_back;
    private MyScrollview qiwen_child_scrollview;
    private RelativeLayout qiwen_child_relativelayout;
    private WebView qiwen_child_webview;

    private boolean flage =false;
    private ImageButton qiwen_child_share;
    private ImageView qiwen_child_image_share;
    private String article_thumb;
    private String title;
    private String share_wx_url;
    private String source;
    private String collect_sum;
    private String datetime;
    private RelativeLayout qiwen_child_contentrelatielayout;
    private GifView gif;
    private RelativeLayout qiwen_child_includerelative;

    @Override
    protected void findViews() {
        qiwen_child_title = (TextView) findViewById(R.id.qiwen_child_title);
        qiwen_child_comment_sum = (TextView) findViewById(R.id.qiwen_child_comment_sum);
        qiwen_child_content = (TextView) findViewById(R.id.qiwen_child_content);
        qiwen_child_jumpto_talk = (TextView) findViewById(R.id.qiwen_child_jumpto_talk);
        qiwen_child_jumpto_talk_list = (ImageView) findViewById(R.id.qiwen_child_jumpto_talk_list);
        qiwen_child_back = (ImageButton) findViewById(R.id.qiwen_child_back);
        qiwen_child_scrollview = (MyScrollview) findViewById(R.id.qiwen_child_scrollview);
        qiwen_child_relativelayout = (RelativeLayout) findViewById(R.id.qiwen_child_relativelayout);
        qiwen_child_webview = (WebView) findViewById(R.id.qiwen_child_webview);
        qiwen_child_share = (ImageButton) findViewById(R.id.qiwen_child_share);
        qiwen_child_image_share = (ImageView) findViewById(R.id.qiwen_child_image_share);
        gif = (GifView) findViewById(R.id.qiwen_child_gif);
        qiwen_child_contentrelatielayout = (RelativeLayout) findViewById(R.id.qiwen_child_contentrelatielayout);
        qiwen_child_includerelative = (RelativeLayout) findViewById(R.id.qiwen_child_includerelative);
    }



    @Override
    protected void initEvent() {

        Intent intent = getIntent();
        app_nav_id = intent.getStringExtra("app_nav_id");
        article_id = intent.getStringExtra("article_id");
        article_type = intent.getStringExtra("article_type");
        article_thumb = intent.getStringExtra("article_thumb");

        qiwen_child_webview.setWebViewClient(new WebViewClient(){

        });
        WebSettings settings = qiwen_child_webview.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    @Override
    protected void init() {
        qiwen_child_jumpto_talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jumptalk = new Intent(QiWenChildActivity.this, QiWenTalkActivity.class);
                jumptalk.putExtra("title",title);
                jumptalk.putExtra("share_wx_url",share_wx_url);
                jumptalk.putExtra("article_id",article_id);

                startActivity(jumptalk);
            }
        });
        qiwen_child_jumpto_talk_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jumpcomment = new Intent(QiWenChildActivity.this, QiWenTalkCommentActivity.class);
                jumpcomment.putExtra("article_id",article_id);
                startActivity(jumpcomment);

            }
        });
        qiwen_child_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        qiwen_child_image_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseShareUtil.showShare(QiWenChildActivity.this,
                         title,
                         null,
                        "来自神奇世界客户端的分享",
                         article_thumb,
                         share_wx_url,
                         null,
                         null,
                         null);
            }
        });
        qiwen_child_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseShareUtil.showShare(QiWenChildActivity.this,
                        title,
                        null,
                        "来自神奇世界客户端的分享",
                        article_thumb,
                        share_wx_url,
                        null,
                        null,
                        null);
            }
        });
    }

    @Override
    protected void loadData() {
        QWChilddao.getQWChildIJson(app_nav_id, article_type, article_id, new BaseCallBack() {
            @Override
            public void success(Object data) {
                QWItemChildInfo qwItemChildInfo = (QWItemChildInfo) data;
                gif.setVisibility(View.GONE);
                qiwen_child_includerelative.setVisibility(View.GONE);
                qiwen_child_contentrelatielayout.setVisibility(View.VISIBLE);
                datetime = qwItemChildInfo.getDatetime();
                collect_sum = qwItemChildInfo.getCollect_sum();
                share_wx_url = qwItemChildInfo.getShare_wx_url();
                source = qwItemChildInfo.getSource();
                title = qwItemChildInfo.getTitle();
                article_id =qwItemChildInfo.getArticle_id();
               // String stamp = qwItemChildInfo.getStamp();
                String content = qwItemChildInfo.getContent();
                qiwen_child_title.setText(title);
                String format = new SimpleDateFormat("yyyy-MM-dd")
                        .format(new Date(Long.valueOf(datetime) * 1000));
                qiwen_child_content.setText("作者：" + source + " 日期" + format);
                qiwen_child_comment_sum.setText(collect_sum + "次阅读");
                qiwen_child_webview.loadDataWithBaseURL(null,content,"text/html","utf-8",null);
                qiwen_child_scrollview.setOnMyScrollChangedListener(new MyScrollview.OnMyScrollChangedListener() {
                    @Override
                    public void onscrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        if(scrollY-oldScrollY>=8){
                            if(!flage) {
                                ObjectAnimator animator = ObjectAnimator
                                        .ofFloat(QiWenChildActivity.this, "XXX", 0, -1).setDuration(250);
                                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        float prgoress = (float) animation.getAnimatedValue();
                                        qiwen_child_relativelayout.setTranslationY(qiwen_child_relativelayout.getHeight()*prgoress);
                                    }
                                });
                                animator.start();
                            }
                            flage=true;
                        }
                        if(scrollY-oldScrollY<=-10){
                            if(flage) {
                                ObjectAnimator animator = ObjectAnimator
                                        .ofFloat(QiWenChildActivity.this, "XXX", -1, 0).setDuration(250);
                                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        float prgoress = (float) animation.getAnimatedValue();
                                        qiwen_child_relativelayout.setTranslationY(qiwen_child_relativelayout.getHeight()*prgoress);
                                    }
                                });
                                animator.start();
                            }
                            flage=false;
                        }
                    }
                });
            }
            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(QiWenChildActivity.this, "加载失败" + errorCode + ":" + data.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected int setLayoutId() {
        return R.layout.qiwen_child_layout;
    }
}
