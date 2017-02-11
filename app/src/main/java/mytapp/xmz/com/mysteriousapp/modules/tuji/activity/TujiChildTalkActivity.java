package mytapp.xmz.com.mysteriousapp.modules.tuji.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.activity.BaseActivity;
import mytapp.xmz.com.mysteriousapp.bmobbean.TalkBean;

/**
 * Created by Administrator on 2001/1/1 0001.
 */
public class TujiChildTalkActivity extends BaseActivity {

    private ImageView imageView;
    private TextView textView;
    private EditText edit;
    private String id;

    @Override
    protected void findViews() {
        imageView = (ImageView) findViewById(R.id.tuji_talk_back);
        textView = (TextView) findViewById(R.id.tuji_talk_send);
        edit = (EditText) findViewById(R.id.tuji_talk_edit);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    @Override
    protected void initEvent() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser currentUser = BmobUser.getCurrentUser();
                if(currentUser!=null){
                    String username = currentUser.getUsername();
                    String talkThings = edit.getText().toString();
                    //Toast.makeText(TujiChildTalkActivity.this,edit.getText(),Toast.LENGTH_LONG).show();
                    if(!talkThings.equals("")){
                        TalkBean talkBean = new TalkBean();
                        talkBean.setUsername(username);
                        talkBean.setTalk(talkThings);
                        talkBean.setArticle_id(id);//设置新闻唯一指定id
                        talkBean.setTime(String.valueOf(new SimpleDateFormat("yyyy-mm-dd").
                                format(new Date(System.currentTimeMillis()))));
                        talkBean.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e==null){
                                    Toast.makeText(TujiChildTalkActivity.this,"发送成功",Toast.LENGTH_LONG).show();
                                    finish();
                                }else {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }else {
                        Toast.makeText(TujiChildTalkActivity.this,"内容不能为空",Toast.LENGTH_LONG).show();

                    }

                }else {
                    Toast.makeText(TujiChildTalkActivity.this,"请先登录才能发言",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.tuji_child_talk_layout;
    }
}
