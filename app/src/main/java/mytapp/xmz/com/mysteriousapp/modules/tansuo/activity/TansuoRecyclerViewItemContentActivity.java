package mytapp.xmz.com.mysteriousapp.modules.tansuo.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.activity.BaseActivity;
import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;
import mytapp.xmz.com.mysteriousapp.baseshare.BaseShareUtil;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.activity.QiWenTalkActivity;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.adapter.TansuoRecyclerViewAdapter;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoBaseInfo;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoRecyclerViewItemInfo;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.utils.TansuoRecyclerViewItemJsonParse;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.widget.TansuoCustomScrollView;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.widget.TansuoRecyclerViewCommentNumCustomView;
import mytapp.xmz.com.mysteriousapp.network.HttpUtil;
import mytapp.xmz.com.mysteriousapp.widget.GifView;

public class TansuoRecyclerViewItemContentActivity extends BaseActivity {

    private ImageButton tansuo_recyclerView_item_content_back;
    private ImageButton tansuo_recyclerView_item_content_share;
    private RelativeLayout tansuo_recyclerView_item_content_head;
    private TextView tansuo_recyclerView_item_content_title;
    private TextView tansuo_recyclerView_item_content_writer;
    private TextView tansuo_recyclerView_item_content_clik;
    private WebView tansuo_recyclerView_item_content_webView;
    private Button tansuo_recyclerView_item_content_chat;
    private TansuoRecyclerViewCommentNumCustomView tansuo_recyclerView_item_content_customView;
    private ImageView tansuo_recyclerView_item_content_collect;
    private ImageButton tansuo_recyclerview_item_shareToAll;
    private TansuoCustomScrollView tansuo_recyclerView_item_content_scrollView;
    private GifView tansuo_recyclerview_item_imageView_anim;
    private RecyclerView tansuo_recyclerView_item_content_recyclerView;
    private List<TansuoBaseInfo> list;
    private TansuoRecyclerViewAdapter adapter =null;
    private LinearLayoutManager linearLayoutManager;
    private Boolean flag = false;
    private LinearLayout tansuo_recyclerView_item_content_hot;
    private String articleId;
    private String title;
    private String share_wx_url;
    private String sharetitle;

    @Override
    protected void findViews() {
        tansuo_recyclerView_item_content_back = (ImageButton) findViewById(R.id.tansuo_recyclerView_item_content_back);
        tansuo_recyclerView_item_content_share = (ImageButton) findViewById(R.id.tansuo_recyclerView_item_content_share);
        tansuo_recyclerView_item_content_head = (RelativeLayout) findViewById(R.id.tansuo_recyclerView_item_content_head);
        tansuo_recyclerView_item_content_title = (TextView) findViewById(R.id.tansuo_recyclerView_item_content_title);
        tansuo_recyclerView_item_content_writer = (TextView) findViewById(R.id.tansuo_recyclerView_item_content_writer);
        tansuo_recyclerView_item_content_webView = (WebView) findViewById(R.id.tansuo_recyclerView_item_content_webView);
        tansuo_recyclerView_item_content_chat = (Button) findViewById(R.id.tansuo_recyclerView_item_content_chat);
        tansuo_recyclerView_item_content_customView = (TansuoRecyclerViewCommentNumCustomView) findViewById(R.id.tansuo_recyclerView_item_content_customView);
        tansuo_recyclerView_item_content_collect = (ImageView) findViewById(R.id.tansuo_recyclerView_item_content_collect);
        tansuo_recyclerview_item_shareToAll = (ImageButton) findViewById(R.id.tansuo_recyclerview_item_shareToAll);
        tansuo_recyclerView_item_content_scrollView = (TansuoCustomScrollView) findViewById(R.id.tansuo_recyclerView_item_content_scrollView);
        tansuo_recyclerview_item_imageView_anim = (GifView) findViewById(R.id.tansuo_recyclerview_item_imageView_anim);
        tansuo_recyclerView_item_content_recyclerView = (RecyclerView) findViewById(R.id.tansuo_recyclerView_item_content_recyclerView);
        tansuo_recyclerView_item_content_hot = (LinearLayout) findViewById(R.id.tansuo_recyclerView_item_content_hot);
        tansuo_recyclerView_item_content_clik= (TextView) findViewById(R.id.tansuo_recyclerView_item_content_clik);
    }

    @Override
    protected void initEvent() {
        tansuo_recyclerView_item_content_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseShareUtil.showShare(
                        TansuoRecyclerViewItemContentActivity.this,
                        sharetitle,
                        null,
                        "来自于奇闻世界的分享",
                        null,
                        share_wx_url,
                        null,null,null);
            }
        });
        tansuo_recyclerview_item_shareToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseShareUtil.showShare(
                        TansuoRecyclerViewItemContentActivity.this,
                        sharetitle,
                        null,
                        "来自于奇闻世界的分享",
                        null,
                        share_wx_url,
                        null,null,null);
            }
        });
        //TODO tansuo_recyclerView_item_content_chat的点击跳转页面
        tansuo_recyclerView_item_content_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),QiWenTalkActivity.class);
                intent.putExtra("article_id",articleId);
                startActivity(intent);
            }
        });

        //TODO tansuo_recyclerView_item_content_customView的点击跳转页面
        tansuo_recyclerView_item_content_customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),TansuoCommentActivity.class);
                intent.putExtra("share_wx_url",share_wx_url);
                intent.putExtra("sharetitle",sharetitle);
                intent.putExtra("articleId",articleId);
                startActivity(intent);
            }
        });

        //TODO　tansuo_recyclerView_item_content_back的返回点击事件
        tansuo_recyclerView_item_content_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //TODO tansuo_recyclerView_item_content_scrollView的滑动事件
        tansuo_recyclerView_item_content_scrollView.setChangeListener(new TansuoCustomScrollView.TansuoCustomScrollViewChangeListener() {
            @Override
            public void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                ObjectAnimator animator = null;
                if(scrollY-oldScrollY>10){
                    if(!flag) {
                        animator = ObjectAnimator.ofFloat(tansuo_recyclerView_item_content_head, "translationY", 0, -tansuo_recyclerView_item_content_head.getHeight());
                        animator.setDuration(500);
                        animator.start();
                        flag = true;
                    }
                }
                if(scrollY-oldScrollY<-10) {
                    if(flag) {
                        animator = ObjectAnimator.ofFloat(tansuo_recyclerView_item_content_head, "translationY", -tansuo_recyclerView_item_content_head.getHeight(), 0);
                        animator.setDuration(500);
                        animator.start();
                        flag =false;
                    }
                }
            }
        });
        //TODO　tansuo_recyclerView_item_content_webView的监听事件
        tansuo_recyclerView_item_content_webView.getSettings().setJavaScriptEnabled(true);
        tansuo_recyclerView_item_content_webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });

    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        articleId = intent.getStringExtra("articleId");
        /*Drawable drawable = tansuo_recyclerview_item_imageView_anim.getDrawable();
        AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
        animationDrawable.start();*/


        list = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        tansuo_recyclerView_item_content_recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TansuoRecyclerViewAdapter(this,list);
        tansuo_recyclerView_item_content_recyclerView.setAdapter(adapter);
        //adapter 监听事件取消脚步
        adapter.setOnCreateFooterHolderListener(new TansuoRecyclerViewAdapter.OnCreateFooterHolderListener() {
            @Override
            public void onCreateFooterHolder(View footerView) {
                ViewGroup.LayoutParams params = footerView.getLayoutParams();
                params.height = 0;
                footerView.setLayoutParams(params);
                footerView.setVisibility(View.GONE);
            }
        });

        //adapter的监听事件
        adapter.setOnItemClickListener(new TansuoRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                TansuoBaseInfo baseInfo = list.get(position);
                final int item_type = baseInfo.getItem_type();
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TansuoRecyclerViewItemContentActivity.this, TansuoRecyclerViewItemContentActivity.class);
                        intent.putExtra("articleId",list.get(position).getArticle_id());
                        startActivity(intent);
//                        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
//                        scaleAnimation.setDuration(250);
//                        scaleAnimation.setRepeatMode(Animation.REVERSE);
//                        v.startAnimation(scaleAnimation);
//
//                        //动画监听事件
//                        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
//                            @Override
//                            public void onAnimationStart(Animation animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animation animation) {
//                                //TODO 跳转页面
//                                Intent intent = new Intent(TansuoRecyclerViewItemContentActivity.this, TansuoRecyclerViewItemContentActivity.class);
//                                intent.putExtra("articleId",list.get(position).getArticle_id());
//                                startActivity(intent);
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animation animation) {
//
//                            }
//                        });
                        switch (item_type) {
                            case TansuoBaseInfo.Type.ONEIMAGETYPE:
                                TextView tansuo_rva_one_title = (TextView) v.findViewById(R.id.tansuo_rva_one_title);
                                tansuo_rva_one_title.setTextColor(Color.GRAY);
                                break;
                            case TansuoBaseInfo.Type.THREEIMAGETYPE:
                                TextView tansuo_rva_three_title = (TextView) v.findViewById(R.id.tansuo_rva_three_title);
                                tansuo_rva_three_title.setTextColor(Color.GRAY);
                                break;
                            case TansuoBaseInfo.Type.ZEROIMAGETYPE:
                                TextView tansuo_rva_zero_title = (TextView) v.findViewById(R.id.tansuo_rva_zero_title);
                                tansuo_rva_zero_title.setTextColor(Color.GRAY);
                                break;
                        }

                    }
                });

            }
        });

    }
    //延时操作
    Handler handler = new Handler();
    @Override
    protected void loadData() {
        String method = "POST";
        String url = "http://api.data.jun360.com/api/show";
        HashMap<String, String> params = new HashMap<>();
        params.put("articleId", articleId);
        params.put("articleType","1");
        params.put("plat","android");
        params.put("appId","6");
        params.put("appnavId","25");
        params.put("adId","0");
        params.put("apiCode","3");
        params.put("imei","000000000000000");
        params.put("version","20161025");

        HttpUtil.doHttpReqeust(method, url, params, new BaseCallBack() {
            @Override
            public void success(Object data) {
                if(data!=null){
                    String jsonDate = (String) data;
                    //Log.e("...........", ".............."+jsonDate);
                    TansuoRecyclerViewItemInfo tansuoRecyclerViewItemInfo = TansuoRecyclerViewItemJsonParse.doParse(jsonDate);
                    if(tansuoRecyclerViewItemInfo!=null){

                        String click = tansuoRecyclerViewItemInfo.getClick();
                        String content = tansuoRecyclerViewItemInfo.getContent();
                        String datetime = tansuoRecyclerViewItemInfo.getDatetime();
                        String source = tansuoRecyclerViewItemInfo.getSource();
                        sharetitle = tansuoRecyclerViewItemInfo.getTitle();
                        String comment_sum = tansuoRecyclerViewItemInfo.getComment_sum();
                        share_wx_url = tansuoRecyclerViewItemInfo.getShare_wx_url();
                        List<TansuoBaseInfo> tansuoBaseInfoList = tansuoRecyclerViewItemInfo.getTansuoBaseInfoList();
                        list.addAll(tansuoBaseInfoList);
                        adapter.notifyDataSetChanged();

                        //格式化时间
                        Date date =new Date(Long.parseLong(datetime)*1000);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        datetime = simpleDateFormat.format(date);

                        //数据成功时结束加载等待时过渡动画
                        tansuo_recyclerview_item_imageView_anim.setVisibility(View.GONE);

                        tansuo_recyclerView_item_content_title.setText(sharetitle);
                        tansuo_recyclerView_item_content_writer.setText(source+"\t"+datetime);
                        tansuo_recyclerView_item_content_webView.loadDataWithBaseURL(null,content,"text/html","utf-8",null);
                        tansuo_recyclerView_item_content_customView.setNum(Integer.parseInt(comment_sum));
                        tansuo_recyclerView_item_content_clik.setText(click+"人已阅读");
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                //显示热门部分内容
//                                tansuo_recyclerView_item_content_hot.setVisibility(View.VISIBLE);
//                            }
//                        },3000);

                    }

                }

            }

            @Override
            public void failed(int errorCode, Object data) {

               /* handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //数据失败时结束加载等待时过渡动画
                        tansuo_recyclerview_item_imageView_anim.setImageResource(R.mipmap.no_data_biaoqing);
                    }
                },3000);*/



            }
        });

    }

    @Override
    protected int setLayoutId() {
        return R.layout.tansuo_recycler_view_item_content_activity;
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //物理返回按键被点击
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (tansuo_recyclerView_item_content_webView.canGoBack()) {
                tansuo_recyclerView_item_content_webView.goBack();//当有上一页的时候就返回到上一页
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
