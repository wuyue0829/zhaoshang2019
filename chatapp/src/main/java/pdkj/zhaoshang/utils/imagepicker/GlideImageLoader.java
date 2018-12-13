package pdkj.zhaoshang.utils.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.youth.banner.loader.ImageLoaderInterface;

import java.io.File;

import pdkj.zhaoshang.R;

public class GlideImageLoader implements ImageLoader, ImageLoaderInterface {

    @Override
    public void displayImages(Activity activity, String path, ImageView imageView, int width, int height) {

        Glide.with(activity)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .into(imageView);
    }



    @Override
    public void clearMemoryCache() {
    }

    @Override
    public void displayImage(Context context, Object path, View imageView) {

    }

    @Override
    public View createImageView(Context context) {
        return null;
    }
}
