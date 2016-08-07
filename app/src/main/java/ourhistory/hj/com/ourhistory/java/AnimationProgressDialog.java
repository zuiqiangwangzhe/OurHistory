package ourhistory.hj.com.ourhistory.java;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import ourhistory.hj.com.ourhistory.R;

/**
 * Created by Administrator on 2016/8/7.
 */
public class AnimationProgressDialog extends ProgressDialog {
    AnimationDrawable animationDrawable;
    ImageView imageView;

    public AnimationProgressDialog(Context context) {
        super(context);
        setCanceledOnTouchOutside(true);//点击可以停止动画
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_layout);
        imageView= (ImageView) findViewById(R.id.progress_img);
        imageView.setBackgroundResource(R.drawable.anim);
        animationDrawable= (AnimationDrawable) imageView.getBackground();
        imageView.post(new Runnable() {
            @Override
            public void run() {
                animationDrawable.start();
            }
        });
    }
}
