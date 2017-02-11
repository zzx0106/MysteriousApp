package mytapp.xmz.com.mysteriousapp.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.bmobbean.UserBean;

/**
 * Created by Administrator on 2016/11/15.
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener {

    private EditText username, password, repassword, email;
    private  BmobUser bmobUser;
    private TextView exit;
    private TextView submit;

    @Override
    protected void findViews() {
        username = (EditText) findViewById(R.id.regist_username);
        password = (EditText) findViewById(R.id.regist_password);
        repassword = (EditText) findViewById(R.id.regist_repassword);
        email = (EditText) findViewById(R.id.regist_email);
        submit = (TextView) findViewById(R.id.regist_submit);
        exit = (TextView) findViewById(R.id.regist_exit);
        bmobUser = new UserBean();
    }

    @Override
    protected void initEvent() {
        submit.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.regist_submit:
                String name = username.getText().toString();
                String pas = password.getText().toString();
                String repas = repassword.getText().toString();
                String eml = email.getText().toString();
                if(name.equals("") || pas.equals("") || repas.equals("")){
                    Toast.makeText(RegistActivity.this,"账号或者密码不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                if(eml.equals("")){
                    Toast.makeText(RegistActivity.this,"邮箱不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!pas.equals(repas)){
                    Toast.makeText(RegistActivity.this,"两次输入密码不一致",Toast.LENGTH_LONG).show();
                    return;
                }
                bmobUser.setUsername(name);
                bmobUser.setPassword(pas);
                bmobUser.setEmail(eml);
                bmobUser.signUp(new SaveListener<UserBean>() {
                    @Override
                    public void done(UserBean userBean, BmobException e) {
                        if(e==null){
                            Toast.makeText(RegistActivity.this,"注册成功,请您在邮箱中进行确认！",Toast.LENGTH_LONG).show();
                            finish();
                        }else {
                            Toast.makeText(RegistActivity.this,"注册失败！该用户或者邮箱已被注册",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.regist_exit:
                finish();
                break;
        }
    }
}
