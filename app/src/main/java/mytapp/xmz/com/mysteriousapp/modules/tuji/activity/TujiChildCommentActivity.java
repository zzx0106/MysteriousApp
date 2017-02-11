package mytapp.xmz.com.mysteriousapp.modules.tuji.activity;

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
import mytapp.xmz.com.mysteriousapp.bmobbean.TalkBean;
import mytapp.xmz.com.mysteriousapp.modules.tuji.adapter.MyCommentAdapter;

/**
 * Created by Administrator on 2001/1/1 0001.
 */
public class TujiChildCommentActivity extends BaseActivity {

    private ImageView back;
    private ListView listView;
    private TextView textView;
    private String id;
    private List<TalkBean> list;
    private MyCommentAdapter adapter;

    @Override
    protected void findViews() {
        back = (ImageView) findViewById(R.id.tuji_comment_back);
        listView = (ListView) findViewById(R.id.tuji_comment_list);
        textView = (TextView) findViewById(R.id.tuji_comment_totalk);

    }

    @Override
    protected void initEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TujiChildCommentActivity.this,TujiChildTalkActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void init() {
        /*List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }*/
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        list = new ArrayList<>();

        adapter = new MyCommentAdapter(list,TujiChildCommentActivity.this);
        listView.setAdapter(adapter);

    }

    @Override
    protected void loadData() {
        BmobQuery<TalkBean> query = new BmobQuery<>();
        query.addWhereEqualTo("article_id",id);//获取数据类型为1的所有值
        query.setLimit(100);//最多获取100条
        query.findObjects(new FindListener<TalkBean>() {
            @Override
            public void done(List<TalkBean> templist, BmobException e) {
                if(e==null){
                    list.addAll(templist);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(TujiChildCommentActivity.this,"数据获取失败，请检查网络",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected int setLayoutId() {
        return R.layout.tuji_child_comment_layout;
    }
}
