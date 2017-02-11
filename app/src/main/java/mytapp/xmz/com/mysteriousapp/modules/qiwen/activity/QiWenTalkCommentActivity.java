package mytapp.xmz.com.mysteriousapp.modules.qiwen.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.activity.BaseActivity;
import mytapp.xmz.com.mysteriousapp.baseshare.BaseShareUtil;
import mytapp.xmz.com.mysteriousapp.bmobbean.TalkBean;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.adapter.CommentAdapter;

/**
 * Created by Administrator on 2016/11/16.
 */
public class QiWenTalkCommentActivity extends BaseActivity {

    private ListView listView;
    private List<TalkBean> list;
    private CommentAdapter adapter;
    private String article_id;
    private ImageView qiwen_child_comment_share;
    private String title;
    private String article_thumb;
    private String share_wx_url;
    private ImageView qiwen_comment_back;
    private TextView qiwen_child_comment_talk;
    @Override
    protected void findViews() {
        listView = (ListView) findViewById(R.id.qiwen_child_comment_list);
        qiwen_child_comment_share = (ImageView) findViewById(R.id.qiwen_child_comment_share);
        qiwen_comment_back = (ImageView) findViewById(R.id.qiwen_comment_back);
        qiwen_child_comment_talk = (TextView) findViewById(R.id.qiwen_child_comment_talk);
    }

    @Override
    protected void initEvent() {
        Intent intent = getIntent();
        article_id = intent.getStringExtra("article_id");
        title = intent.getStringExtra("title");
        article_thumb = intent.getStringExtra("article_thumb");
        share_wx_url = intent.getStringExtra("share_wx_url");

        qiwen_child_comment_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseShareUtil.showShare(QiWenTalkCommentActivity.this,
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
        qiwen_comment_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        qiwen_child_comment_talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent jumptoqwt = new Intent(QiWenTalkCommentActivity.this,QiWenTalkActivity.class);
                jumptoqwt.putExtra("article_id",article_id);
                startActivity(jumptoqwt);
                finish();
            }
        });
    }

    @Override
    protected void init() {

        list = new ArrayList<>();
        adapter = new CommentAdapter(this, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void loadData() {
        BmobQuery<TalkBean> query = new BmobQuery<>();
        query.addWhereEqualTo("article_id",article_id);//获取数据类型为1的所有值
        query.setLimit(100);//最多获取100条
        query.findObjects(new FindListener<TalkBean>() {
            @Override
            public void done(List<TalkBean> templist, BmobException e) {
              if(e==null){
                  list.addAll(templist);
                  adapter.notifyDataSetChanged();
              }else {
                  Toast.makeText(QiWenTalkCommentActivity.this,"数据获取失败，请检查网络",Toast.LENGTH_LONG).show();
              }
            }
        });

    }

    @Override
    protected int setLayoutId() {
        return R.layout.qiwen_child_comment_layout;
    }
}
