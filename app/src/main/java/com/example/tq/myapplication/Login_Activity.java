package com.example.tq.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.tq.myapplication.CustomView.LoadProgressDialog;
import com.example.tq.myapplication.Utils.AppManager;
import com.jaeger.library.StatusBarUtil;

public class Login_Activity extends AppCompatActivity {
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //加入到管理类
        AppManager.getAppManager().addActivity(this);

        //使状态栏透明(图片作为背景时需将其填充到状态栏)
        StatusBarUtil.setTransparent(this);
        btn_login = findViewById(R.id.btn_login);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 登录
     * @param view
     */
    public void btn_Login(View view){
        btn_login.setClickable(false);
        final LoadProgressDialog loadProgressDialog=new LoadProgressDialog(this,"登录中……",false);
        loadProgressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadProgressDialog.dismiss();
                Intent intent = new Intent(Login_Activity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);
                btn_login.setClickable(false);
            }
        },2000);
    }

    /**
     *  以popupWindow弹出等待框（由于特殊原因不建议使用）
     */
    @Deprecated
    public void showPopup(){
        //btn_login.setClickable(false);
        final PopupWindow popupWindow = new PopupWindow();
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        View popup = LayoutInflater.from(this).inflate(R.layout.dialog_login_wait,null);
        popupWindow.setContentView(popup);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0,0);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        }); //将返回值设置为true，在popupWindow外部点击不会dismiss，但是按住返回键仍然会dismiss

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Login_Activity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in,R.anim.zoom_out);
                popupWindow.dismiss();
            }
        },2000);
    }

}
