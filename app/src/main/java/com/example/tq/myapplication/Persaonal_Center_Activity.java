package com.example.tq.myapplication;

import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.tq.myapplication.Utils.AppManager;

public class Persaonal_Center_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persaonal__center);

        //加入到管理类
        AppManager.getAppManager().addActivity(this);

        Toolbar toolbar = findViewById(R.id.toolbar_personal_center);
        toolbar.setTitle("个人中心");
        toolbar.setTitleTextColor(getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(R.drawable.ic_back);//设置导航栏图标
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }
}
