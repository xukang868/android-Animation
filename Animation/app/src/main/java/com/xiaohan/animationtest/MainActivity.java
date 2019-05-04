package com.xiaohan.animationtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.view)
    Button mView;
    @BindView(R.id.frame)
    Button mFrame;
    @BindView(R.id.object)
    Button mObject;
    @BindView(R.id.summary)
    WebView mSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        mSummary.loadUrl("https://www.jianshu.com/p/bbec753757d9");
    }

    @OnClick({R.id.view, R.id.frame, R.id.object})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view:
                //activity 跳转动画 https://www.jianshu.com/p/bbec753757d9
                startActivity(new Intent(MainActivity.this, CommonActivity.class));
                overridePendingTransition(R.anim.animation_activity_enter1, R.anim.animation_popup_exit1);//A-->B  A 进入动画 B消失动画
                break;
            case R.id.frame:
                startActivity(new Intent(MainActivity.this, DrawableActivity.class));
                break;
            case R.id.object:
                //https://www.cnblogs.com/chenxibobo/p/5379319.html
                startActivity(new Intent(MainActivity.this, ObjectActivity.class));
                break;
                //MaterialAnimations https://www.jianshu.com/p/33b293eb673b
        }
    }
}
