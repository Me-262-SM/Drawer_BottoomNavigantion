package com.example.tq.myapplication.Fragments;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tq.myapplication.CustomView.ItemDetailsDialog;
import com.example.tq.myapplication.MainActivity;
import com.example.tq.myapplication.Model.Mission;
import com.example.tq.myapplication.Model.Prisoner;
import com.example.tq.myapplication.Model.Table_Mission;
import com.example.tq.myapplication.R;
import com.example.tq.myapplication.Utils.MyConvert;
import com.example.tq.myapplication.Utils.MyDBOpenHelper;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_new_mission extends Fragment {
    private RecyclerView recyclerView;
    private MissionAdapter adapter;
    private PullToRefreshView mPullToRefreshView;
    private ArrayList<Prisoner> list = new ArrayList<>();
    private Activity activity;
    private MyDBOpenHelper myDBOpenHelper;
    private ItemDetailsDialog itemDetailsDialog;

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==2){
                Toast.makeText(activity,"refresh",Toast.LENGTH_SHORT).show();
                Cursor cursor = Table_Mission.getTable(
                        MyDBOpenHelper.getInstance(activity,"db_test",null,1).getWritableDatabase()
                ).query("select * from "+Table_Mission.getTableName()+" WHERE status= ?"
                        ,new String[]{"0"});

                adapter.swapCursor(cursor);
            }
        }
    };



    public Fragment_new_mission() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_mission, container, false);
        //下拉刷新
        mPullToRefreshView = view.findViewById(R.id.pull_to_refresh_new);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        Prisoner test = new Prisoner("zsh",1124,201);
                        Table_Mission.getTable(
                                MyDBOpenHelper.getInstance(activity,"db_test",null,1).getWritableDatabase()
                        ).add("Ikali",0,MyConvert.ObjectToByte(test));

                        Message message = Message.obtain(handler);
                        message.what = 2;
                        handler.sendMessage(message);
                    }
                },1250);
            }
        });

        Table_Mission.getTable(
                MyDBOpenHelper.getInstance(activity,"db_test",null,1).getWritableDatabase()
        ).deleteAll();



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = view.findViewById(R.id.new_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MissionAdapter(Table_Mission.getTable(
                MyDBOpenHelper.getInstance(activity,"db_test",null,1).getWritableDatabase()
        ).query("select * from "+Table_Mission.getTableName()+" WHERE status= ?", new String[]{"0"}));
        recyclerView.setAdapter(adapter);


        return view;
    }

    /**
     * adapter class
     */
    class MissionAdapter extends RecyclerView.Adapter<MissionAdapter.ViewHolder>{
        private Cursor cursor;

        public MissionAdapter(Cursor cursor) {
            this.cursor = cursor;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_list,viewGroup,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
            if (!cursor.moveToPosition(i)){
                return;
            }

            long id = cursor.getLong(cursor.getColumnIndex("_id"));
            String info = cursor.getString(cursor.getColumnIndex("info"));
            int status = cursor.getInt(cursor.getColumnIndex("status"));
            byte[] temp= cursor.getBlob(cursor.getColumnIndex("prisoner"));
            Prisoner prisoner = MyConvert.ByteToObject(temp);

            viewHolder.tv_mission_info.setText(prisoner.getName());
            viewHolder.tv_mission_status.setText("待接受");
            viewHolder.tv_mission_status.setTextColor(getResources().getColor(R.color.colorRed));

            viewHolder.itemView.setTag(prisoner);

            final int position = i;

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Prisoner prisoner = (Prisoner)viewHolder.itemView.getTag();
                    Toast.makeText(getActivity(),""+prisoner.getPrisonid(),Toast.LENGTH_SHORT).show();
                    Mission mission = new Mission(Mission.TYPE_A,"test",Mission.STATUS_A,prisoner);
                    itemDetailsDialog = new ItemDetailsDialog(activity,mission);
                    itemDetailsDialog.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            if(cursor == null){return  0;}
            return cursor.getCount();
        }

        //更新列表
        public void swapCursor(Cursor newCursor){
            if(this.cursor!=null){
                this.cursor.close();
            }
            this.cursor = newCursor;
            if(newCursor != null){
                this.notifyDataSetChanged();
            }
        }


        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView tv_mission_info, tv_mission_status;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_mission_info = itemView.findViewById(R.id.mission_info);
                tv_mission_status = itemView.findViewById(R.id.mission_status);
            }
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
