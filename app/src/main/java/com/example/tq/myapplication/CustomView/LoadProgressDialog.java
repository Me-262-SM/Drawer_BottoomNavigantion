package com.example.tq.myapplication.CustomView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.tq.myapplication.R;

public class LoadProgressDialog extends Dialog {
    private String message;
    private boolean canCancel;
    private TextView textView;

    public LoadProgressDialog(Context context, String message) {
        this(context, message, true);
    }

    public LoadProgressDialog(Context context, String message, boolean canCancel) {
        super(context, R.style.LoadProgressDialog);
        this.canCancel = canCancel;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
        handler.sendEmptyMessage(0);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                textView.setText(message);
            }
        }
    };

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login_wait);
        textView = findViewById(R.id.loading_message);
        setCancelable(false);
        setCanceledOnTouchOutside(canCancel);
        textView.setText(message);
    }
}

