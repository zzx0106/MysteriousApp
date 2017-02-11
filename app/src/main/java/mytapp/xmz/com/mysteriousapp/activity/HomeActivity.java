package mytapp.xmz.com.mysteriousapp.activity;


import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.adapter.Home_FragmentPagerAdapter;
import mytapp.xmz.com.mysteriousapp.bmobbean.UserBean;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.fragment.QiWen_fragment;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.fragment.Tansuo_fragment;
import mytapp.xmz.com.mysteriousapp.modules.tuijian.fragment.TuiJian_fragment;
import mytapp.xmz.com.mysteriousapp.modules.tuji.fragment.TuJi_fragment;
import mytapp.xmz.com.mysteriousapp.modules.video.fragment.Video_fragment;
import mytapp.xmz.com.mysteriousapp.widget.MyDrawerLayout;

public class HomeActivity extends BaseActivity {

    private TextView home_username;
    private TextView home_changepassword;
    private TextView home_exit;
    private TextView jump;
    private TextView findpasswowrd;
    private TextView findemail;
    private TextView home_cleanuser;
    private TextView home_about;

    private View findpasView;
    private TabLayout tabLayout;
    private MyDrawerLayout myDrawerLayout;
    private ViewPager viewPager;
    private List<String> list;
    private FragmentTransaction transaction;
    private TuiJian_fragment tuijian_fragment;
    private TuJi_fragment tuJi_fragment;
    private QiWen_fragment qiWen_fragment;
    private Tansuo_fragment tansuo_fragment;
    private Fragment[] fragments;
    private int mposition = 0;
    private EditText username;
    private EditText password;
    private View loginview;
    private UserBean userBean = null;

    private RelativeLayout home_login;

    private AlertDialog.Builder builder;
    private AlertDialog.Builder findbuild;
    private AlertDialog.Builder changepasswordDialog;
    private AlertDialog.Builder aboutbuilder;
    private ImageView home_icon;
    private ImageView home_head;
    private ImageView home_set;


    private boolean changedrawerlayout = false;
    private AlertDialog dialog;
    private View changepasswordView;
    private EditText oldpwd;
    private EditText newpwd;
    private long endtime1;
    private long mExitTime=800;
    private PopupWindow popuwindow;

    @Override
    protected void findViews() {
        //关于页面
        tabLayout = (TabLayout) findViewById(R.id.home_tablayout);
        viewPager = (ViewPager) findViewById(R.id.home_viewpager);
        myDrawerLayout = (MyDrawerLayout) findViewById(R.id.home_mdrawerlayout);
        home_head = (ImageView) findViewById(R.id.home_head);
        home_set = (ImageView) findViewById(R.id.home_set);
        home_login = (RelativeLayout) findViewById(R.id.home_login);

        //关于用户登录
        loginview = LayoutInflater.from(this).inflate(R.layout.home_login_layout, null);
        username = (EditText) loginview.findViewById(R.id.login_username);
        password = (EditText) loginview.findViewById(R.id.login_password);
        jump = (TextView) loginview.findViewById(R.id.home_jump_regist);
        findpasswowrd = (TextView) loginview.findViewById(R.id.home_jump_findpassword);
        findpasView = LayoutInflater.from(HomeActivity.this).inflate(R.layout.home_findpassword_layout, null);
        findemail = (TextView) findpasView.findViewById(R.id.home_findpassword_email);

        //关于用户个人信息（侧滑块）
        home_icon = (ImageView) findViewById(R.id.home_icon);
        home_username = (TextView) findViewById(R.id.home_username);
        home_changepassword = (TextView) findViewById(R.id.home_changepassword);
        home_exit = (TextView) findViewById(R.id.home_exit);
        home_cleanuser = (TextView) findViewById(R.id.home_cleanuser);
        changepasswordView = LayoutInflater.from(this).inflate(R.layout.home_changepassword_layout, null);
        oldpwd = (EditText) changepasswordView.findViewById(R.id.home_changepassword_oldpwd);
        newpwd = (EditText) changepasswordView.findViewById(R.id.home_changepassword_newpwd);
        home_about = (TextView) findViewById(R.id.home_aboutus);
    }


    @Override
    protected void initEvent() {
        initLoge();
        initFragment();
        setList();
        setAdapter();
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        popuwindow = new PopupWindow(300 , ViewGroup.LayoutParams.WRAP_CONTENT);
        popuwindow.setAnimationStyle(R.style.popwin_anim_style);
        popuwindow.setContentView(LayoutInflater.from(HomeActivity.this).inflate(R.layout.popuwindow_layout,null));
        home_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!popuwindow.isShowing()) {
                    popuwindow.showAsDropDown(home_set, 0, 30);
                }else {
                    popuwindow.dismiss();
                }
            }
        });
    }

    private void initLoge() {


        //缓存对象，判断是否需要登录
        BmobUser bmobUser = BmobUser.getCurrentUser();
        if (bmobUser != null) {
            if (bmobUser.getEmailVerified() != null) {
                if (bmobUser.getEmailVerified() == true) {
                    home_username.setText(bmobUser.getUsername());
                    changedrawerlayout = true;
                    home_exit.setVisibility(View.VISIBLE);
                } else {
                    userLoginAndRegist();
                }
            }

        } else {
            //用户注册登录
            userLoginAndRegist();
        }
        //跳转入注册界面
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, RegistActivity.class));
            }
        });
        //跳转入找回密码界面
        findpasswowrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (findbuild == null) {
                    findbuild = new AlertDialog.Builder(HomeActivity.this);
                }
                findbuild.setNegativeButton("确认", null);
                findbuild.setPositiveButton("取消", null);
                findbuild.setView(findpasView);
                final AlertDialog finddialog = findbuild.create();
                Window window1 = finddialog.getWindow();
                window1.setGravity(Gravity.CENTER);
                window1.setWindowAnimations(R.style.dialog_style);
                finddialog.show();
                finddialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String fdeml = findemail.getText().toString();
                        userBean.resetPasswordByEmail(fdeml, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(HomeActivity.this, "邮箱已发送，请查收", Toast.LENGTH_LONG).show();
                                    finddialog.dismiss();
                                } else {
                                    e.printStackTrace();
                                    Toast.makeText(HomeActivity.this, "邮箱发送失败", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
            }
        });
        if(changedrawerlayout){
            home_changepassword.setVisibility(View.VISIBLE);
        }else {
            home_changepassword.setVisibility(View.GONE);
        }
    }

    private void userLoginAndRegist() {
        if (userBean == null) {
            userBean = new UserBean();
        }
        if (builder == null) {
            builder = new AlertDialog.Builder(this);
        }
        builder.setView(loginview);
        builder.setMessage("登录提示");
        builder.setPositiveButton("暂不登录", null);
        builder.setNegativeButton("登录", null);

        dialog = builder.create();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.dialog_style);
        dialog.show();
        //设置关闭监听
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                String psd = password.getText().toString();
                if (name.equals("") || psd.equals("")) {
                    Toast.makeText(HomeActivity.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
                } else {
                    userBean.setUsername(name);
                    userBean.setPassword(psd);
                    userBean.login(new SaveListener<UserBean>() {

                        @Override
                        public void done(UserBean userBean, BmobException e) {
                            //获取缓存对象，保存时限为1年
                            BmobUser currentUser = BmobUser.getCurrentUser(UserBean.class);
                            Boolean emailVerified = currentUser.getEmailVerified();
                            Log.e("emailVerified", emailVerified + "");
                            if (e == null || emailVerified != null) {
                                if (!emailVerified) {
                                    Toast.makeText(HomeActivity.this, "您还未进行邮箱验证，请先验证在登录", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(HomeActivity.this, "登陆成功！祝您使用愉快", Toast.LENGTH_LONG).show();
                                    home_username.setText(currentUser.getUsername());
                                    changedrawerlayout = true;
                                    home_changepassword.setVisibility(View.VISIBLE);
                                    home_exit.setVisibility(View.VISIBLE);
                                    dialog.dismiss();
                                }
                            } else {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }
        });

    }

    private void initFragment() {
        tuijian_fragment = new TuiJian_fragment();
        tuJi_fragment = new TuJi_fragment();
        qiWen_fragment = new QiWen_fragment();
        tansuo_fragment = new Tansuo_fragment();
        Video_fragment person_fragment = new Video_fragment();
        fragments = new Fragment[]{tuijian_fragment, tansuo_fragment, qiWen_fragment, tuJi_fragment, person_fragment};
    }

    private void setList() {
        list = new ArrayList<>();
        list.add("推荐");
        list.add("探索");
        list.add("奇闻");
        list.add("图集");
        list.add("视频");
    }

    private void setAdapter() {
        Home_FragmentPagerAdapter adapter = new Home_FragmentPagerAdapter(getSupportFragmentManager(), list, fragments);
        viewPager.setAdapter(adapter);
        viewPagerListener();
    }

    private void viewPagerListener() {
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(final int position) {
                mposition = position;
            }
        });

        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myDrawerLayout.isDrawerOpen(home_login)){
                    myDrawerLayout.closeDrawer(home_login);
                }
            }
        });
        myDrawerLayout.setGetPosition(new MyDrawerLayout.getPagerPosition() {
            @Override
            public int getPager() {
                return mposition;
            }
        });
        myDrawerLayout.setOnStateEvemtListener(new MyDrawerLayout.onStateEvemtListener() {
            @Override
            public boolean flage() {
                return myDrawerLayout.isDrawerOpen(home_login);
            }
        });
    }

    @Override
    protected void init() {
        home_cleanuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
            }
        });
        home_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("changedrawerlayout", changedrawerlayout + "");
                if (!changedrawerlayout) {
                    if (dialog != null) {
                        dialog.show();
                    }
                }

            }
        });

        home_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();//退出并清除缓存
                home_username.setText("点击登录");
                changedrawerlayout = false;
                home_exit.setVisibility(View.GONE);
                Toast.makeText(HomeActivity.this, "成功退出", Toast.LENGTH_LONG).show();
            }
        });

        home_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changepasswordDialog == null) {
                    changepasswordDialog = new AlertDialog.Builder(HomeActivity.this);
                }

                if (changedrawerlayout) {
                    changepasswordDialog.setTitle("修改密码");
                    changepasswordDialog.setView(changepasswordView);
                    changepasswordDialog.setPositiveButton("取消", null);
                    changepasswordDialog.setNegativeButton("确定", null);
                    final AlertDialog changepws = changepasswordDialog.create();
                    Window window = changepws.getWindow();
                    window.setGravity(Gravity.CENTER);
                    window.setWindowAnimations(R.style.dialog_style);

                    changepws.show();

                    changepws.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String old = oldpwd.getText().toString();
                            String newp = newpwd.getText().toString();
                            //此处有bug，原始密码不对照样能修改
                            userBean.updateCurrentUserPassword(old, newp, new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(HomeActivity.this, "修改成功！", Toast.LENGTH_LONG).show();
                                        changepws.dismiss();
                                    } else {
                                        Toast.makeText(HomeActivity.this, "修改失败，请检查网络等外界因素！", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

        home_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aboutbuilder == null) {
                    aboutbuilder = new AlertDialog.Builder(HomeActivity.this);
                }
                aboutbuilder.setTitle("团队简介");
                aboutbuilder.setMessage("此软件由开发组小组成员集体开发");
                aboutbuilder.setNegativeButton("确定", null);
                AlertDialog aboutdialog = aboutbuilder.create();
                Window window = aboutdialog.getWindow();
                window.setGravity(Gravity.CENTER);
                window.setWindowAnimations(R.style.dialog_style);
                aboutdialog.show();
            }
        });


        home_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myDrawerLayout.isDrawerOpen(home_login)){
                    myDrawerLayout.openDrawer(home_login);
                }

            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        endtime1 = System.currentTimeMillis();
        if(myDrawerLayout.isDrawerOpen(home_login)){
            myDrawerLayout.closeDrawer(home_login);
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() -  mExitTime) > 800) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
