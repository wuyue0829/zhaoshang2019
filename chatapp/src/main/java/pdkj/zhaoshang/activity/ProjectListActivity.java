package pdkj.zhaoshang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultMyProjectData;
import pdkj.zhaoshang.entity.ResultProjectData;
import pdkj.zhaoshang.utils.BaseUtil;
import pdkj.zhaoshang.utils.ItemMyProject;
import pdkj.zhaoshang.utils.ItemProject;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.ToastUtil;
import pdkj.zhaoshang.utils.photovideo.takevideo.utils.LogUtils;

/**
 * Created by wuyue on 2018/5/16.
 */

public class ProjectListActivity extends BaseActivity implements View.OnClickListener{


    private TextView tv_title;
    private LinearLayout ly_content;
    private RelativeLayout rl_head_return;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        this.setSwipeBackEnable(false);
        title = getIntent().getExtras().getString("type");
        initView();
        initData();
    }

    private void initView(){
        ly_content = (LinearLayout) findViewById(R.id.ly_content);
        tv_title = (TextView) findViewById(R.id.tv_title);
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
    }

    private void initData(){
        tv_title.setText(title);
        rl_head_return.setOnClickListener(this);
        RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_CATEGORY_CATEGORYLIST);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        params.addBodyParameter("oper","mine");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ResultMyProjectData.Data data;
                ResultMyProjectData resultProjectData = new Gson().fromJson(result, ResultMyProjectData.class);
                data = resultProjectData.getData();
                if(title.equals("我创建的项目")){
                    if(null != data){
                        if(!BaseUtil.isSpace(data.getHcategorys())){
                            ly_content.removeAllViews();
                            for(ResultMyProjectData.ProjectData projectData:data.getHcategorys()){
                                ly_content.addView(new ItemMyProject(mContext,projectData));
                            }
                        }else{
                            ToastUtil.shortToast(mContext,"还条件下暂时没有项目");
                        }
                    }else{
                        ToastUtil.shortToast(mContext,"还条件下暂时没有项目");
                    }
                }else if(title.equals("我参与的项目")){
                    if(null != data){
                        if(!BaseUtil.isSpace(data.getMcategorys())){
                            ly_content.removeAllViews();
                            for(ResultMyProjectData.ProjectData projectData:data.getMcategorys()){
                                ly_content.addView(new ItemMyProject(mContext,projectData));
                            }
                        }else{
                            ToastUtil.shortToast(mContext,"还条件下暂时没有项目");
                        }
                    }else{
                        ToastUtil.shortToast(mContext,"还条件下暂时没有项目");
                    }
                }else{
                    if(null != data){
                        if(!BaseUtil.isSpace(data.getScategorys())){
                            ly_content.removeAllViews();
                            for(ResultMyProjectData.ProjectData projectData:data.getScategorys()){
                                ly_content.addView(new ItemMyProject(mContext,projectData));
                            }
                        }else{
                            ToastUtil.shortToast(mContext,"还条件下暂时没有项目");
                        }
                    }else{
                        ToastUtil.shortToast(mContext,"还条件下暂时没有项目");
                    }
                }

            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtils.e("ex",ex.getMessage());
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
                LogUtils.e("ex",cex.getMessage());
            }
            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_head_return:
                finish();
                break;
        }
    }
}
