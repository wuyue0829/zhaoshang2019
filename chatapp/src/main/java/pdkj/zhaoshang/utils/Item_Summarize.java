package pdkj.zhaoshang.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.activity.AddEventActivity;
import pdkj.zhaoshang.activity.NodeDetailyActivity;
import pdkj.zhaoshang.entity.NodeEntity;
import pdkj.zhaoshang.entity.ResultEventData;
import pdkj.zhaoshang.entity.Summarize;

public class Item_Summarize extends LinearLayout {
    private Context mContext;
    private Summarize.SummarizeData data;
    private TextView tv_name;
    private TextView tv_data;
    private TextView tv_content;
    private ImageView im_photo;


    public Item_Summarize(Context context) {
        super(context);
    }


    public Item_Summarize(Context context, Summarize.SummarizeData data) {
        this(context);
        mContext = context;
        this.data = data;
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_summarize, this,true);
        tv_name = findViewById(R.id.tv_name);
        tv_data = findViewById(R.id.tv_data);
        tv_content = findViewById(R.id.tv_content);
        tv_name.setText(data.getAddUser());
        tv_data.setText(data.getAddTime());
        tv_content.setText(data.getContent());
    }
}
