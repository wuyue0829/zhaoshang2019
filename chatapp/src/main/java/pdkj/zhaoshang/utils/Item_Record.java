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

public class Item_Record extends LinearLayout {
    private Context mContext;
    private String title;
    private String date;
    private String type;
    private ImageView im_icon;
    private TextView tv_type;
    private TextView tv_title;
    private TextView tv_date;


    public Item_Record(Context context) {
        super(context);
    }


    public Item_Record(Context context,String title,String date,String type) {
        this(context);
        mContext = context;
        this.title = title;
        this.date = date;
        this.type = type;
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_record, this,true);
        im_icon = findViewById(R.id.im_icon);
        tv_type = findViewById(R.id.tv_type);
        tv_title = findViewById(R.id.tv_title);
        tv_date = findViewById(R.id.tv_date);

        if(type.equals("6")){
            im_icon.setImageDrawable(getResources().getDrawable(R.drawable.icon_xiangmu));
            tv_type.setText("项目");
        }else if(type.equals("7")){
            im_icon.setImageDrawable(getResources().getDrawable(R.drawable.icon_jiedian));
            tv_type.setText("节点");
        }else if(type.equals("8")){
            im_icon.setImageDrawable(getResources().getDrawable(R.drawable.icon_shijian));
            tv_type.setText("事件");
        }else if(type.equals("9")){
            im_icon.setImageDrawable(getResources().getDrawable(R.drawable.icon_xiangmu));
            tv_type.setText("汇总");
        }


        tv_title.setText(title);
        tv_date.setText(date);
    }
}
