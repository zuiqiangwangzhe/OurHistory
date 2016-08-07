package ourhistory.hj.com.ourhistory.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import ourhistory.hj.com.ourhistory.R;

/**
 * Created by Administrator on 2016/8/7.
 */
public class WelcomeActivity extends Activity {
    AlphaAnimation alphaAnimation;
    ImageView img;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_layout);
        img= (ImageView) findViewById(R.id.welcome_img);
        textView= (TextView) findViewById(R.id.welcome_text);
        alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(2500);
        img.startAnimation(alphaAnimation);
        textView.startAnimation(alphaAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                WelcomeActivity.this.startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
            }
        },2500);
    }
}
