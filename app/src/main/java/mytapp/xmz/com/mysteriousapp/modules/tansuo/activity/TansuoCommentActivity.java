package mytapp.xmz.com.mysteriousapp.modules.tansuo.activity;


import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.activity.BaseActivity;
import mytapp.xmz.com.mysteriousapp.baseshare.BaseShareUtil;
import mytapp.xmz.com.mysteriousapp.bmobbean.TalkBean;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.activity.QiWenTalkActivity;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.adapter.CommentAdapter;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.widget.TansuoRecyclerViewCommentNumCustomView;
import mytapp.xmz.com.mysteriousapp.widget.GifView;

public class TansuoCommentActivity extends BaseActivity {

    private List<TalkBean> list;
    private CommentAdapter adapter;

    private ImageButton tansuo_comment_back;
    private ListView tansuo_comment_content;
    private Button tansuo_comment_chat;
    private TansuoRecyclerViewCommentNumCustomView tansuo_comment_customView;
   // private ImageView tansuo_comment_collect;
    private ImageButton tansuo_comment_shareToAll;
    private GifView tansuo_comment_imageView_anim;
    private TextView tansuo_comment_listView_empty;
    private String articleId;
    private String sharetitle;
    private String share_wx_url;

    @Override
    protected void findViews() {
        tansuo_comment_back = (ImageButton) findViewById(R.id.tansuo_comment_back);
        tansuo_comment_content = (ListView) findViewById(R.id.tansuo_comment_content);
        tansuo_comment_chat = (Button) findViewById(R.id.tansuo_comment_chat);
        tansuo_comment_customView = (TansuoRecyclerViewCommentNumCustomView) findViewById(R.id.tansuo_comment_customView);
      //  tansuo_comment_collect = (ImageView) findViewById(R.id.tansuo_comment_collect);
        tansuo_comment_shareToAll = (ImageButton) findViewById(R.id.tansuo_comment_shareToAll);
        tansuo_comment_imageView_anim = (GifView) findViewById(R.id.tansuo_comment_imageView_anim);
        tansuo_comment_listView_empty = (TextView) findViewById(R.id.tansuo_comment_listView_empty);

    }

    @Override
    protected void initEvent() {
        //TODO tansuo_comment_back的回退时间
        tansuo_comment_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //TODO tansuo_comment_customView的刷新数据功能
        tansuo_comment_customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        //TODO tansuo_comment_chat的发表功能
        tansuo_comment_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),QiWenTalkActivity.class);
                intent.putExtra("article_id",articleId);
                //启动有返回值的activity
                startActivityForResult(intent,10086);
               /* Log.e("............", "onClick: "+"aaaaaaaaaaaaaa");
                loadData();*/
            }
        });
        tansuo_comment_shareToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseShareUtil.showShare(TansuoCommentActivity.this,
                        sharetitle,
                        null,
                        "来自于奇闻世界的分享",
                        null,
                        share_wx_url,
                        null,null,null);
            }
        });
    }

    @Override
    protected void init() {
        /*Drawable drawable = tansuo_comment_imageView_anim.getDrawable();
        AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
        animationDrawable.start();*/

        list = new ArrayList<>();
        adapter = new CommentAdapter(this, list);
        tansuo_comment_content.setAdapter(adapter);

        Intent intent = getIntent();
        articleId = intent.getStringExtra("articleId");
        share_wx_url = intent.getStringExtra("share_wx_url");
        sharetitle = intent.getStringExtra("sharetitle");


    }
    //延时操作
    Handler handler = new Handler();
    @Override
    protected void loadData() {
        BmobQuery<TalkBean> query = new BmobQuery<>();
        query.addWhereEqualTo("article_id", articleId);//获取数据类型为1的所有值
        query.setLimit(100);//最多获取100条
        query.findObjects(new FindListener<TalkBean>() {
            @Override
            public void done(List<TalkBean> templist, BmobException e) {
                if(e==null){
                    if(templist!=null) {
                        list.clear();
                        list.addAll(templist);
                        adapter.notifyDataSetChanged();
                        //数据成功时结束加载等待时过渡动画
                        tansuo_comment_imageView_anim.setVisibility(View.GONE);
                    }
                }/*else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //数据失败时结束加载等待时过渡动画
                            tansuo_comment_imageView_anim.setImageResource(R.mipmap.no_data_biaoqing);
                        }
                    },3000);
                   }*/

                //如果list的大小为0，则弹出提示
                if(list.size()==0){
                    tansuo_comment_listView_empty.setVisibility(View.VISIBLE);
                }else {
                    tansuo_comment_listView_empty.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected int setLayoutId() {
        return R.layout.tansuo_comment_activity;
    }

    //TODO 等待传值后刷新数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
