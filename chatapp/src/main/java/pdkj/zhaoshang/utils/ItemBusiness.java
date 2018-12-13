package pdkj.zhaoshang.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.entity.NetContants;

public class ItemBusiness extends LinearLayout {


    public ItemBusiness(Context context) {
        super(context);
        init();
    }


    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_null, this,true);
    }
}
