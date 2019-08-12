package com.example.tq.myapplication.CustomView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tq.myapplication.Model.Mission;
import com.example.tq.myapplication.R;

public class ItemDetailsDialog extends Dialog {
    private Mission message;
    private boolean canCancel;
    private TextView tv_prisoner_name
            ,tv_prisoner_id
            ,tv_prisoner_room
            ,tv_prisoner_destination
            ,tv_mission_details_type;

    private LinearLayout linearLayout_destination;


    public ItemDetailsDialog(Context context, Mission message){
        this(context,message,true);
    }

    public ItemDetailsDialog(Context context, Mission message, boolean canCancel) {
        super(context, R.style.LoadProgressDialog);
        this.canCancel = canCancel;
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        tv_prisoner_name = findViewById(R.id.prisoner_name);
        tv_prisoner_id = findViewById(R.id.prisoner_id);
        tv_prisoner_room = findViewById(R.id.prisoner_room);
        tv_mission_details_type = findViewById(R.id.mission_details_type);
        tv_prisoner_destination = findViewById(R.id.prisoner_destination);
        linearLayout_destination = findViewById(R.id.destination);

        tv_prisoner_name.setText(message.getPrisoner().getName());
        tv_prisoner_id.setText(message.getPrisoner().getPrisonid()+"");
        tv_prisoner_room.setText(message.getPrisoner().getPrisonRoomId()+"");
        tv_mission_details_type.setText(message.getType());

        findViewById(R.id.btn_details_abandon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if(message.getType().equals(Mission.TYPE_B) || message.getType().equals(Mission.TYPE_D)){
            tv_prisoner_destination.setText("TEST");
            linearLayout_destination.setVisibility(View.VISIBLE);
        }

        setCancelable(canCancel);
        setCanceledOnTouchOutside(false);
    }
}
