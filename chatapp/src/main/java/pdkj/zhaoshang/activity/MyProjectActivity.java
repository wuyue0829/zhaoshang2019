package pdkj.zhaoshang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.zhy.autolayout.AutoLinearLayout;

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
import pdkj.zhaoshang.utils.photovideo.takevideo.utils.LogUtils;

/**
 * 创建时间： 2018/2/8
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class MyProjectActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout ly_content,ly_content1,ly_content2;
    private RelativeLayout rl_head_return;
    private AutoLinearLayout cd_one,cd_two,cd_three;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project);
        this.setSwipeBackEnable(false);
        initView();
        initData();
    }

    private void initView(){
        ly_content = (LinearLayout) findViewById(R.id.ly_content);
        ly_content1 = (LinearLayout) findViewById(R.id.ly_content1);
        ly_content2 = (LinearLayout) findViewById(R.id.ly_content2);
        cd_one = (AutoLinearLayout) findViewById(R.id.cd_one);
        cd_two = (AutoLinearLayout) findViewById(R.id.cd_two);
        cd_three = (AutoLinearLayout) findViewById(R.id.cd_three);
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        cd_one.setOnClickListener(this);
        cd_two.setOnClickListener(this);
        cd_three.setOnClickListener(this);
    }

    private void initData(){
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
                if(null != data){
                    if(!BaseUtil.isSpace(data.getHcategorys())){
                        ly_content.removeAllViews();
                        for(ResultMyProjectData.ProjectData projectData:data.getHcategorys()){
                            ly_content.addView(new ItemMyProject(mContext,projectData));
                        }
                    }

                    if(!BaseUtil.isSpace(data.getMcategorys())){
                        ly_content1.removeAllViews();
                        for(ResultMyProjectData.ProjectData projectData:data.getMcategorys()){
                            ly_content1.addView(new ItemMyProject(mContext,projectData));
                        }
                    }

                    if(!BaseUtil.isSpace(data.getScategorys())){
                        ly_content2.removeAllViews();
                        for(ResultMyProjectData.ProjectData projectData:data.getScategorys()){
                            ly_content2.addView(new ItemMyProject(mContext,projectData));
                        }
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
            case R.id.cd_one:
                Intent intent = new Intent(mContext,ProjectListActivity.class);
                intent.putExtra("type","我创建的项目");
                startActivity(intent);
                break;
            case R.id.cd_two:
                Intent intent1 = new Intent(mContext,ProjectListActivity.class);
                intent1.putExtra("type","我参与的项目");
                startActivity(intent1);
                break;
            case R.id.cd_three:
                Intent intent2 = new Intent(mContext,ProjectListActivity.class);
                intent2.putExtra("type","共享给我的项目");
                startActivity(intent2);
                break;
        }
    }
}
