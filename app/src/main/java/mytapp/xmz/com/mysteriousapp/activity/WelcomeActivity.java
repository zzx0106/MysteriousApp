package mytapp.xmz.com.mysteriousapp.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import mytapp.xmz.com.mysteriousapp.R;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView icon;
    private TextView text1;
    private TextView text2;

    private ImageView bigImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initEvent();
    }

    private void initEvent() {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0);
        animation.setDuration(1000);
        animation.setInterpolator(new DecelerateInterpolator());//加入渲染效果（超速）
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                startLogoAnimation();
                startSecondAnimation();
                icon.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //second开始
             //   startSecondAnimation();


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        text1.startAnimation(animation);



    }

    private void startLogoAnimation() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(this,"xxx",0,1).setDuration(2000);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();


                icon.setAlpha((int)(255*progress));
                if(progress == 1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bigImg.setVisibility(View.VISIBLE);
                            startBigImgAnimation();
                            text1.setVisibility(View.GONE);
                            text2.setVisibility(View.GONE);
                            icon.setVisibility(View.GONE);

                        }
                    }, 1000);
                }
            }
        });
        animation.start();


    }

    private void startBigImgAnimation() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(this,"xxx",0,1).setDuration(1000);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                bigImg.setAlpha((int)(255*progress));
                if(progress == 1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startSmallAnimation();
                        }
                    }, 1000);
                }
            }
        });
        animation.start();

    }

    private void startSmallAnimation() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(this,"xxx",1,0).setDuration(1000);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                bigImg.setAlpha((int)(255*progress));
                if(progress == 0){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            JumpHomeActivity();
                        }
                    }, 100);
                }
            }
        });
        animation.start();
    }


    private void startSecondAnimation() {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0);
        animation.setDuration(1000);
        animation.setInterpolator(new DecelerateInterpolator());//加入渲染效果（超速）
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        text2.startAnimation(animation);
    }


    private void init() {
        icon = (ImageView) findViewById(R.id.welcome_img);
        text1 = (TextView) findViewById(R.id.welcome_text1);
        text2 = (TextView) findViewById(R.id.welcome_text2);
        bigImg = (ImageView) findViewById(R.id.welcome_bigimg);

    }

    public void JumpHomeActivity(){
        Intent jump = new Intent(this,HomeActivity.class);
        startActivity(jump);
        finish();
    }
}
