package com.example.tq.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.tq.myapplication.Fragments.Fragment_finished_mission;
import com.example.tq.myapplication.Fragments.Fragment_isdoing_mission;
import com.example.tq.myapplication.Fragments.Fragment_new_mission;
import com.example.tq.myapplication.Utils.AppManager;
import com.jaeger.library.StatusBarUtil;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment fragment_finished_mission
            ,fragment_isdoing_mission
            ,fragment_new_mission;
    private Fragment currentFragment=null;
    private FragmentManager fragmentManager;
    private NotificationManager notificationManager;
    private Notification notification;
    private boolean isQuit = false;
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                Toast.makeText(MainActivity.this,"yes",Toast.LENGTH_SHORT).show();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //加入到管理类
        AppManager.getAppManager().addActivity(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        //修改状态栏颜色(包含DrawerLayout)
        StatusBarUtil.setColorForDrawerLayout(this,drawerLayout,getResources().getColor(R.color.colorBlack));

        //
        initNotification();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //左上角按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_home_menu);
        }

        fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_isdoing_mission);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        fragment_finished_mission = new Fragment_finished_mission();
        fragment_isdoing_mission = new Fragment_isdoing_mission();
        fragment_new_mission = new Fragment_new_mission();
        switchFragment(fragment_isdoing_mission);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.navigation_finished_mission:
                switchFragment(fragment_finished_mission);
                return true;
            case R.id.navigation_isdoing_mission:
                switchFragment(fragment_isdoing_mission);
                return true;
            case R.id.navigation_new_mission:
                switchFragment(fragment_new_mission);
                return true;
            case R.id.navigation_personal_center:
                //关闭侧滑菜单
                drawerLayout.closeDrawer(GravityCompat.START);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this,Persaonal_Center_Activity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
                    }
                },125);
                return true;
            case R.id.navigation_test:
                drawerLayout.closeDrawer(GravityCompat.START);
                notificationManager.notify(1,notification);
                return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            //关闭侧滑菜单
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            //按两下退出
            if (!isQuit) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                isQuit = true;
                //在两秒钟之后isQuit会变成false
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            isQuit = false;
                        }
                    }
                }).start();
            } else {
                //退出APP
                AppManager.getAppManager().AppExit(this);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 切换Fragment
     * @param fragment
     */
    public void switchFragment(Fragment fragment){
        if (currentFragment != fragment){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if(currentFragment!=null){
                transaction.hide(currentFragment);    //将原先的fragment隐藏
            }
            currentFragment = fragment; //替换当前fragment
            if(!fragment.isAdded()){
                transaction.add(R.id.frame,fragment);   //如未加入则加入
            }
            transaction.show(fragment);
            transaction.commit();
        }
    }

    /**
     *退出
     * @param view
     */
    public void btn_Exit(View view){
        //退出APP
        AppManager.getAppManager().AppExit(this);
    }

    /**
     *设置
     * @param view
     */
    public void btn_Setting(View view){
        //
    }

    /**
     * BottomNavigationView显示角标
     * @param viewIndex tab索引
     * @param showNumber 显示的数字，小于等于0时将不显示
     */
    private void showBadgeView(int viewIndex, int showNumber) {
        // 具体child的查找和view的嵌套结构请在源码中查看
        // 从bottomNavigationView中获得BottomNavigationMenuView
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        // 从BottomNavigationMenuView中获得childview, BottomNavigationItemView
        if (viewIndex < menuView.getChildCount()) {
            // 获得viewIndex对应子tab
            View view = menuView.getChildAt(viewIndex);
            // 从子tab中获得其中显示图片的ImageView
            View icon = view.findViewById(android.support.design.R.id.icon);
            // 获得图标的宽度
            int iconWidth = icon.getWidth();
            // 获得tab的宽度/2
            int tabWidth = view.getWidth() / 2;
            // 计算badge要距离右边的距离
            int spaceWidth = tabWidth - iconWidth;

            // 显示badegeview
            new QBadgeView(this).bindTarget(view).setGravityOffset(spaceWidth, 3, false).setBadgeNumber(showNumber);
        }
    }

    /**
     * Notification初始化
     */
    public void initNotification(){
        //判断API版本
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            Notification.Builder builder =new Notification.Builder(this);
            builder.setContentTitle("三天之内sa了你")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentText("玉音放送")
                    .setSubText("儒雅随和")
                    .setWhen(System.currentTimeMillis());
            notification = builder.build();
        }else {
            NotificationChannel channel = new NotificationChannel("test", "jst a test", NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLightColor(0xFFFF0000);
            channel.setVibrationPattern(new long[]{0,300});
            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"test");
            builder.setContentTitle("三天之内sa了你")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentText("玉音放送")
                    .setSubText("儒雅随和")
                    .setWhen(System.currentTimeMillis());
            notification = builder.build();
        }
    }



}
