package com.ken.customview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView rv;
    private List<ItemBean> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        data = new ArrayList<>();
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new SuperDividerItemDecoration.Builder(this)
                .build());
        data.add(new ItemBean("蜘蛛视图",PathActivity.class));
        data.add(new ItemBean("旋转飞机",PlaneActivity.class));
        data.add(new ItemBean("搜索动画",SearchActivity.class));
        data.add(new ItemBean("点击成功按钮动画",SuccessActivity.class));
        data.add(new ItemBean("贝塞尔曲线-美团下单动画",FoodActivity.class));
        data.add(new ItemBean("Lottie动画",LottieActivity.class));
        data.add(new ItemBean("水波扩散",RippleActivity.class));
        data.add(new ItemBean("特殊控件的事件处理",RemoteControlMenuActivity.class));
        data.add(new ItemBean("畸形进度条",LoadingActivity.class));
        setAdatapter();
    }

    private void setAdatapter() {
        rv.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new RecyclerView.ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_text,viewGroup,false)) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                ((TextView) viewHolder.itemView.findViewById(R.id.text)).setText(data.get(i).name);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(v.getContext(),data.get(i).clazz));
                    }
                });
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        });
    }
}
