package pdkj.zhaoshang.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

import pdkj.zhaoshang.utils.photovideo.takevideo.utils.LogUtils;

/**
 * 创建时间： 2018/2/7
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class GlideImageLoaders extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        LogUtils.e("图片地址",path+"");
        Glide.with(context).load(path).into(imageView);
    }
}