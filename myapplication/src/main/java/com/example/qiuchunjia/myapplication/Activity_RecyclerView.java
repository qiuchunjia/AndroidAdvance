package com.example.qiuchunjia.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by qiuchunjia on 2016/3/3.
 */
public class Activity_RecyclerView extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private HomeAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initData();
        mRecyclerView= (RecyclerView) findViewById(R.id.id_recyclerview);
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,4);
        gridLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());

    }
    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }


    class HomeAdapter extends RecyclerView.Adapter< HomeAdapter.MyViewHolder>{
            @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder=new MyViewHolder(getLayoutInflater().inflate(R.layout.item_recyclerview,parent,false));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder( HomeAdapter.MyViewHolder holder, int position) {
            holder.mTextview.getLayoutParams().height= new Random().nextInt(300);
            holder.mTextview.setText(mDatas.get(position)+"");
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView mTextview;
            public MyViewHolder(View itemView) {
                super(itemView);
                mTextview= (TextView) itemView.findViewById(R.id.id_num);
            }
        }


    }


}
