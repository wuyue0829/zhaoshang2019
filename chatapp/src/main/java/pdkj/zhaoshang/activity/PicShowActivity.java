package pdkj.zhaoshang.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.utils.ImageLoadUtils;
import pdkj.zhaoshang.view.ZoomImageView;

/**
 * 创建时间： 2018/2/10
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class PicShowActivity extends BaseActivity{
    private ImageView im_show;
    private String usrStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);
        initView();
        initData();
    }

    private void initView(){
        im_show = (ImageView) findViewById(R.id.im_show);
        usrStr = getIntent().getExtras().getString("urlStr");
    }

    private void initData(){
        Glide.with(mContext).load(usrStr).into(im_show);
    }
}
