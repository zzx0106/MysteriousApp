package mytapp.xmz.com.mysteriousapp.modules.qiwen.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
 * Created by Administrator on 2016/11/16.
 */
public class QiWenTalkActivity extends BaseActivity {

    private ImageView qiwen_child_talk_back;
    private EditText qiwen_child_talk_edit;
    private TextView qiwen_child_talk_send;
    private String article_id;
    @Override
    protected void findViews() {
        qiwen_child_talk_back = (ImageView) findViewById(R.id.qiwen_child_talk_back);
        qiwen_child_talk_edit = (EditText) findViewById(R.id.qiwen_child_talk_edit);
        qiwen_child_talk_send = (TextView) findViewById(R.id.qiwen_child_talk_send);

        //edit弹出对话框属性
        qiwen_child_talk_edit.setFocusable(true);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void initEvent() {
        Intent intent = getIntent();
        article_id = intent.getStringExtra("article_id");

        qiwen_child_talk_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser currentUser = BmobUser.getCurrentUser();
                if(currentUser!=null){
                    String username = currentUser.getUsername();
                    String talkThings = qiwen_child_talk_edit.getText().toString();
                    if(!talkThings.equals("")){
                        TalkBean talkBean = new TalkBean();
                        talkBean.setUsername(username);
                        talkBean.setTalk(talkThings);
                        talkBean.setArticle_id(article_id);//设置新闻唯一指定id
                        talkBean.setTime(String.valueOf(new SimpleDateFormat("yyyy-mm-dd").
                                format(new Date(System.currentTimeMillis()))));
                        talkBean.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e==null){
                                    Toast.makeText(QiWenTalkActivity.this,"发送成功",Toast.LENGTH_LONG).show();
                                    finish();
                                    Intent jumptoqwtc = new Intent(QiWenTalkActivity.this, QiWenTalkCommentActivity.class);
                                    jumptoqwtc.putExtra("article_id",article_id);
                                    startActivity(jumptoqwtc);
                                }else {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }else {
                        Toast.makeText(QiWenTalkActivity.this,"内容不能为空",Toast.LENGTH_LONG).show();

                    }

                }else {
                    Toast.makeText(QiWenTalkActivity.this,"请先登录才能发言",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void init() {
        qiwen_child_talk_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.qiwen_child_talk_layout;
    }
}
