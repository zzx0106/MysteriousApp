package mytapp.xmz.com.mysteriousapp.modules.tuji.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.activity.BaseActivity;
import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;
import mytapp.xmz.com.mysteriousapp.baseshare.BaseShareUtil;
import mytapp.xmz.com.mysteriousapp.modules.tuji.adapter.MyPagerAdapter;
import mytapp.xmz.com.mysteriousapp.modules.tuji.bean.TujichildInfo;
import mytapp.xmz.com.mysteriousapp.modules.tuji.dao.TujiChildDao;
import mytapp.xmz.com.mysteriousapp.modules.tuji.utils.CallBackBundle;
import mytapp.xmz.com.mysteriousapp.modules.tuji.utils.LoadingImageToSdcard;
import mytapp.xmz.com.mysteriousapp.modules.tuji.utils.OpenFileDialog;
import mytapp.xmz.com.mysteriousapp.modules.tuji.widget.CommentButton;
import mytapp.xmz.com.mysteriousapp.modules.tuji.widget.MyTextView;
import mytapp.xmz.com.mysteriousapp.modules.tuji.widget.MyTujiToast;

/**
 * Created by Administrator on 2016/11/12 0012.
 */
public class TujiChildActivity extends BaseActivity {


    private MyTextView textView1;
    private String id;
    private List<TujichildInfo> list;
    private ViewPager pager;
    private List<ImageView> images = new ArrayList<>();
    private MyPagerAdapter adapter;
    private ImageView backimg;
    private String comment;
    private CommentButton btn;
    private ImageView shoucang;
    private boolean flag = true;
    private ImageButton download;
    //完成分享的按钮
    private ImageView share;
    private TextView talk;
    private static int openfileDialogId = 0;
    private String myPath;
    private MyHandler handler;
    private String share_url;

    @Override
    protected void findViews() {
        textView1 = (MyTextView) findViewById(R.id.myText);
        pager = (ViewPager) findViewById(R.id.tuji_viewpager);
        backimg = (ImageView) findViewById(R.id.tuji_child_back);
        btn = (CommentButton) findViewById(R.id.tuji_my_comment_num);
        shoucang = (ImageView) findViewById(R.id.tuji_child_shoucang);
        download = (ImageButton) findViewById(R.id.tuji_download);
        share = (ImageView) findViewById(R.id.tuji_child_share);
        talk = (TextView) findViewById(R.id.tuji_child_talk);


    }

    @Override
    protected void initEvent() {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textView1.setText(position+1+""+"/"+list.size()+"\t"+list.get(position).getImage_title()+"\n"+
                list.get(position).getImage_desc());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    shoucang.setImageResource(R.mipmap.tuji_like_h);
                    //toast需要自己定义一个
                    MyTujiToast toast = MyTujiToast.createToast();
                    toast.ShowMyToast(TujiChildActivity.this,(ViewGroup)findViewById(R.id.tuji_toast_root),
                            "收藏成功",true,R.mipmap.ic_toast_fav);

                    flag = false;
                }else {
                    shoucang.setImageResource(R.mipmap.tuji_like_white);
                    MyTujiToast toast = MyTujiToast.createToast();
                    toast.ShowMyToast(TujiChildActivity.this,(ViewGroup)findViewById(R.id.tuji_toast_root),
                        "取消收藏",true,R.mipmap.ic_toast_unfav);
                    flag = true;
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TujiChildActivity.this,TujiChildCommentActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //此处完成下载至内存卡中的操作

               showDialog(openfileDialogId);

            }
        });
        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //需要完成界面跳转时从下往上的动画
                Intent intent = new Intent(TujiChildActivity.this,TujiChildTalkActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseShareUtil.showShare(TujiChildActivity.this,list.get(1).getImage_title(),
                        null,"来自奇闻世界的分享",null,share_url,null,null,null);
            }
        });
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        comment = intent.getStringExtra("comment");

        textView1.setText("网络不稳,请稍等······");
        btn.setCount(Integer.parseInt(comment));


        adapter = new MyPagerAdapter(images);

        handler = new MyHandler();

    }

    @Override
    protected void loadData() {
        TujiChildDao.requestTujiChildInfo(id, new BaseCallBack() {
            @Override
            public void success(Object data) {
                list = (List<TujichildInfo>) data;

                for (int i = 0; i < list.size(); i++) {
                    TujichildInfo info = list.get(i);
                    ImageView imageView = new ImageView(TujiChildActivity.this);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Picasso.with(TujiChildActivity.this).load(info.getImage_url()).into(imageView);
                    images.add(imageView);
                    if(i == 1) {
                        share_url = info.getShare_wx_url();
                    }
                }
                pager.setAdapter(adapter);
                textView1.setText(1+""+"/"+list.size()+"\t"+list.get(1).getImage_title()+"\n"
                +list.get(1).getImage_desc());

            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(TujiChildActivity.this, data.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.tuji_child_layout;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == openfileDialogId) {
            Map<String, Integer> images = new HashMap<>();
            images.put(OpenFileDialog.sRoot, R.mipmap.tuji_file);
            images.put(OpenFileDialog.sParent, R.mipmap.tuji_file);
            images.put(OpenFileDialog.sFolder, R.mipmap.tuji_file);
            images.put("wav", R.mipmap.tuji_file2);

            Dialog dialog = OpenFileDialog.createDialog(id, this, "选择文件夹", new CallBackBundle() {

                @Override
                public void callback(Bundle bundle) {
                    String path = bundle.getString("path");
                    myPath = path;
                    /*Toast.makeText(TujiChildActivity.this,list.get(pager.getCurrentItem()).getImage_url()+""+myPath,
                            Toast.LENGTH_LONG).show();*/
                    final LoadingImageToSdcard load = new LoadingImageToSdcard(TujiChildActivity.this, new LoadingImageToSdcard.OnBackMessage() {
                        @Override
                        public void backMessage(String message) {
                            if(message == null){
                                message = "未知错误";
                            }
                            Message msg = Message.obtain();
                            msg.what = 1;
                            msg.obj = message;
                            msg.setTarget(handler);
                            msg.sendToTarget();
                        }
                    });
                    boolean iswifi = load.isWiFi();
                    if(iswifi){
                        load.loading(list.get(pager.getCurrentItem()).getImage_url(),myPath);

                    }else{
                        AlertDialog.Builder dialog = new AlertDialog.Builder(TujiChildActivity.this);
                        dialog.setTitle("是否确定下载");
                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                load.loading(list.get(pager.getCurrentItem()).getImage_url(),myPath);
                            }
                        });

                    }

                }
            }, ".wav", images);

            return dialog;
        }
        return null;
    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1) {
                String message = (String) msg.obj;
                MyTujiToast toast = MyTujiToast.createToast();
                toast.ShowMyToast(TujiChildActivity.this, (ViewGroup) findViewById(R.id.tuji_toast_root),
                        message, false, 0);
            }
        }
    }
}
