package pdkj.zhaoshang.activity;

import android.content.Intent;
import android.content.MutableContextWrapper;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import pdkj.zhaoshang.R;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultData;
import pdkj.zhaoshang.entity.ResultEventData;
import pdkj.zhaoshang.entity.ResultNodeData;
import pdkj.zhaoshang.entity.ResultNodeFinalData;
import pdkj.zhaoshang.utils.BaseUtil;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.ToastUtil;

/**
 * 创建时间： 2018/2/10
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class AddEventActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout rl_head_return;
    ImageView im1;
    ImageView im2;
    ImageView im3;
    ImageView im4;
    ImageView im5;
    ImageView im6;

    private TextView tv_name;
    private TextView tv_leixing;
    private TextView tv_chanye;
    private TextView tv_fuzeren;
    private TextView tv_time;
    private EditText ed_title;
    private RelativeLayout rl_submit;

    private String NodeID;
    private String NodeName;
    private String ProjectID;
    private String ProjectJieDuan;
    private String ProjectName;
    private EditText ed_content;
    private String files = "";

    private RelativeLayout rl_time;

    private AddEventActivity addEventActivity;

    private List<File> listFiles;
    private List<LocalMedia> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        addEventActivity = this;
        initView();
        initData();
    }

    private void initView(){
        im1 = (ImageView) findViewById(R.id.im1);
        im2 = (ImageView) findViewById(R.id.im2);
        im3 = (ImageView) findViewById(R.id.im3);
        im4 = (ImageView) findViewById(R.id.im4);
        im5 = (ImageView) findViewById(R.id.im5);
        im6 = (ImageView) findViewById(R.id.im6);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_leixing = (TextView) findViewById(R.id.tv_leixing);
        tv_chanye = (TextView) findViewById(R.id.tv_chanye);
        tv_fuzeren = (TextView) findViewById(R.id.tv_fuzeren);
        tv_time = (TextView) findViewById(R.id.tv_time);
        rl_time = (RelativeLayout) findViewById(R.id.rl_time);
        rl_submit = (RelativeLayout) findViewById(R.id.rl_submit);
        ed_content = (EditText) findViewById(R.id.ed_content);
        ed_title = (EditText) findViewById(R.id.ed_title);

        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        listFiles = new ArrayList<>();
    }

    private void initData(){

        NodeID = getIntent().getExtras().getString("NodeID");
        NodeName = getIntent().getExtras().getString("NodeName");
        ProjectID = getIntent().getExtras().getString("ProjectID");
        ProjectJieDuan = getIntent().getExtras().getString("ProjectJieDuan");
        ProjectName = getIntent().getExtras().getString("ProjectName");

        tv_name.setText(ProjectName);
        tv_chanye.setText(NodeName);
        if(ProjectJieDuan.equals("0")){
            tv_leixing.setText("招商阶段");
        }else if(ProjectJieDuan.equals("1")){
            tv_leixing.setText("建设阶段");
        }else if(ProjectJieDuan.equals("2")){
            tv_leixing.setText("生产阶段");
        }
        tv_fuzeren.setText(JMessageClient.getMyInfo().getNickname());
        tv_time = (TextView) findViewById(R.id.tv_time);

        im1.setOnClickListener(this);
        im2.setOnClickListener(this);
        im3.setOnClickListener(this);
        im4.setOnClickListener(this);
        im5.setOnClickListener(this);
        im6.setOnClickListener(this);
        rl_time.setOnClickListener(this);
        rl_submit.setOnClickListener(this);
        rl_head_return.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im1:
                choosePic();
                break;
            case R.id.im2:
                choosePic();
                break;
            case R.id.im3:
                choosePic();
                break;
            case R.id.im4:
                choosePic();
                break;
            case R.id.im5:
                choosePic();
                break;
            case R.id.im6:
                choosePic();
                break;
            case R.id.rl_head_return:
                finish();
                break;
            case R.id.rl_time:
                timeChooseDialog();
                break;
            case R.id.rl_submit:
                if(inputCheck()){
                    submit();
                }
                break;
        }

    }


    public void timeChooseDialog(){
        TimePickerView timePickerView = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(final Date date, View v) {

                tv_time.setText(getDataTime(date));
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setCancelText("取消")
                .setSubmitText("确定")
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setOutSideCancelable(true)
                .isCyclic(true)
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setSubmitColor(Color.GRAY)//确定按钮文字颜色
                .setCancelColor(Color.GRAY)//取消按钮文字颜色
                .isCenterLabel(false)
                .build();
        timePickerView.show();
    }


    public String getDataTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void choosePic(){
        PictureSelector.create(addEventActivity)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(6)// 最大图片选择数量 int
                .minSelectNum(0)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
                .openClickSound(false)// 是否开启点击声音 true or false
                .selectionMedia(list)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    list = selectList;
                    for(int i = 0 ;i< selectList.size();i++){
                        if(selectList.get(i).isCompressed()){
                            listFiles.add(new File(selectList.get(i).getCompressPath()));
                        }
                    }
                    if(selectList.size() == 0){
                        im1.setVisibility(View.VISIBLE);
                        im2.setVisibility(View.GONE);
                        im3.setVisibility(View.GONE);
                        im4.setVisibility(View.GONE);
                        im5.setVisibility(View.GONE);
                        im6.setVisibility(View.GONE);

                    }else if(selectList.size() == 1){
                        Glide.with(mContext).load(selectList.get(0).getCompressPath()).into(im1);
                        im1.setVisibility(View.VISIBLE);
                        im2.setVisibility(View.VISIBLE);
                        im3.setVisibility(View.GONE);
                        im4.setVisibility(View.GONE);
                        im5.setVisibility(View.GONE);
                        im6.setVisibility(View.GONE);
                    }else if(selectList.size() == 2){
                        Glide.with(mContext).load(selectList.get(0).getCompressPath()).into(im1);
                        Glide.with(mContext).load(selectList.get(1).getCompressPath()).into(im2);
                        im1.setVisibility(View.VISIBLE);
                        im2.setVisibility(View.VISIBLE);
                        im3.setVisibility(View.VISIBLE);
                        im4.setVisibility(View.GONE);
                        im5.setVisibility(View.GONE);
                        im6.setVisibility(View.GONE);
                    }else if(selectList.size() == 3){
                        Glide.with(mContext).load(selectList.get(0).getCompressPath()).into(im1);
                        Glide.with(mContext).load(selectList.get(1).getCompressPath()).into(im2);
                        Glide.with(mContext).load(selectList.get(2).getCompressPath()).into(im3);
                        im1.setVisibility(View.VISIBLE);
                        im2.setVisibility(View.VISIBLE);
                        im3.setVisibility(View.VISIBLE);
                        im4.setVisibility(View.VISIBLE);
                        im5.setVisibility(View.GONE);
                        im6.setVisibility(View.GONE);
                    }else if(selectList.size() == 4){
                        Glide.with(mContext).load(selectList.get(0).getCompressPath()).into(im1);
                        Glide.with(mContext).load(selectList.get(1).getCompressPath()).into(im2);
                        Glide.with(mContext).load(selectList.get(2).getCompressPath()).into(im3);
                        Glide.with(mContext).load(selectList.get(3).getCompressPath()).into(im4);
                        im1.setVisibility(View.VISIBLE);
                        im2.setVisibility(View.VISIBLE);
                        im3.setVisibility(View.VISIBLE);
                        im4.setVisibility(View.VISIBLE);
                        im5.setVisibility(View.VISIBLE);
                        im6.setVisibility(View.GONE);
                    }else if(selectList.size() == 5){
                        Glide.with(mContext).load(selectList.get(0).getCompressPath()).into(im1);
                        Glide.with(mContext).load(selectList.get(1).getCompressPath()).into(im2);
                        Glide.with(mContext).load(selectList.get(2).getCompressPath()).into(im3);
                        Glide.with(mContext).load(selectList.get(3).getCompressPath()).into(im4);
                        Glide.with(mContext).load(selectList.get(4).getCompressPath()).into(im5);
                        im1.setVisibility(View.VISIBLE);
                        im2.setVisibility(View.VISIBLE);
                        im3.setVisibility(View.VISIBLE);
                        im4.setVisibility(View.VISIBLE);
                        im5.setVisibility(View.VISIBLE);
                        im6.setVisibility(View.VISIBLE);
                    }else if(selectList.size() == 6){
                        Glide.with(mContext).load(selectList.get(0).getCompressPath()).into(im1);
                        Glide.with(mContext).load(selectList.get(1).getCompressPath()).into(im2);
                        Glide.with(mContext).load(selectList.get(2).getCompressPath()).into(im3);
                        Glide.with(mContext).load(selectList.get(3).getCompressPath()).into(im4);
                        Glide.with(mContext).load(selectList.get(4).getCompressPath()).into(im5);
                        Glide.with(mContext).load(selectList.get(5).getCompressPath()).into(im6);
                        im1.setVisibility(View.VISIBLE);
                        im2.setVisibility(View.VISIBLE);
                        im3.setVisibility(View.VISIBLE);
                        im4.setVisibility(View.VISIBLE);
                        im5.setVisibility(View.VISIBLE);
                        im6.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    }


    private void submit(){


        final ResultEventData.Event event = new ResultEventData.Event();
        RequestParams params = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_COMMON_UPLOAD);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        for(File file:listFiles){
            params.addBodyParameter("files", file,"multipart/form-data;charset=utf-8");
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                files = new Gson().fromJson(result, ResultData.class).getData();
                event.setImages(files);
                event.setNodeId(NodeID);
                event.setContent(ed_content.getText().toString().trim());
                event.setCategoryId(ProjectID);
                event.setStageId(ProjectJieDuan);
                event.setTitle(ed_title.getText().toString().trim());
                event.setHappenDate(tv_time.getText().toString().trim());
                RequestParams params = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_EVENT_ADD);
                params.addHeader("token", SharePreferenceManager.getCachedUserToken());
                params.addBodyParameter("jsonObject",new Gson().toJson(event),"application/json");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        ToastUtil.shortToast(mContext,"事件添加成功！");
                        Intent intent = new Intent(mContext, NodeDetailyActivity.class);
                        intent.putExtra("id", new Gson().fromJson(result,ResultNodeFinalData.class).getData().getId());
                        mContext.startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFinished() {

                    }
                });

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }


    private boolean inputCheck(){
        if(TextUtils.isEmpty( tv_name.getText().toString().trim())){
            return false;
        }
        if(TextUtils.isEmpty( tv_leixing.getText().toString().trim())){
            return false;
        }
        if(TextUtils.isEmpty( tv_chanye.getText().toString().trim())){
            return false;
        }
        if(TextUtils.isEmpty( tv_fuzeren.getText().toString().trim())){
            return false;
        }
        if(TextUtils.isEmpty( tv_time.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请选择时间");
            return false;
        }
        if(TextUtils.isEmpty( ed_content.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请填写事件详情");
            return false;
        }
        if(TextUtils.isEmpty( ed_title.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请填写事件标题");
            return false;
        }
        if(listFiles.size()<2){
            ToastUtil.shortToast(mContext,"至少上传两张图片");
            return false;
        }

        return true;
    }
}
