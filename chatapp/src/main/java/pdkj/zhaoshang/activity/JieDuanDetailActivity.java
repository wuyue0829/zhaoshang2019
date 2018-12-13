package pdkj.zhaoshang.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import pdkj.zhaoshang.activity.fragment.ProjectAttractFragment;
import pdkj.zhaoshang.activity.fragment.ProjectBuildFragment;
import pdkj.zhaoshang.activity.fragment.ProjectProductionFragment;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultDetailListData;
import pdkj.zhaoshang.utils.BaseUtil;
import pdkj.zhaoshang.utils.DoNumberUtil;
import pdkj.zhaoshang.utils.Item_project_content;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.photovideo.takevideo.utils.LogUtils;

/**
 * Created by wuyue on 2018/5/16.
 */

public class JieDuanDetailActivity  extends BaseActivity implements View.OnClickListener{


    private LinearLayout ly_content;
    private RelativeLayout rl_head_return;
    private String type;
    private String categoryId;
    private String stageId;
    private TextView tv_title;
    private TextView tv_null;
    private List<ResultDetailListData.NodeList> list = new ArrayList<>();
    private List<ResultDetailListData.NodeList> list1 = new ArrayList<>();
    private List<ResultDetailListData.NodeList> list2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jieduan_detaily);
        initView();
        initData();
    }


    private void initView(){
        type = getIntent().getExtras().getString("type");
        categoryId = getIntent().getExtras().getString("categoryId");
        stageId = getIntent().getExtras().getString("stageId");
        ly_content = (LinearLayout) findViewById(R.id.ly_content);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_null = (TextView) findViewById(R.id.tv_null);
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        if(type.equals("0")){
            tv_title.setText("招商阶段");
        }else if(type.equals("1")){
            tv_title.setText("建设阶段");
        }else if(type.equals("2")){
            tv_title.setText("运营阶段");
        }
    }

    private void initData(){
        rl_head_return.setOnClickListener(this);
        RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_LIB_NODE);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        params.addBodyParameter("categoryId",categoryId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                ResultDetailListData resultProjectData = new Gson().fromJson(result, ResultDetailListData.class);
                for( ResultDetailListData.NodeList projectData:resultProjectData.getData()){
                    if(projectData.getStageId().equals("0")){
                        list.add(projectData);
                    }
                }

                for( ResultDetailListData.NodeList projectData:resultProjectData.getData()){
                    if(projectData.getStageId().equals("1")){
                        list1.add(projectData);
                    }
                }

                for( ResultDetailListData.NodeList projectData:resultProjectData.getData()){
                    if(projectData.getStageId().equals("2")){
                        list2.add(projectData);
                    }
                }

                if(type.equals("0")){
                    if(!BaseUtil.isSpace(list)){
                        tv_null.setVisibility(View.GONE);
                        ly_content.removeAllViews();

                        for(int i = 0; i<list.size();i++){
                            ly_content.addView(new Item_project_content(mContext,list.get(i),false,false,categoryId,stageId,"0"));
                        }

                    }else{
                        tv_null.setVisibility(View.VISIBLE);
                    }

                }else if(type.equals("1")){
                    if(!BaseUtil.isSpace(list1)){
                        tv_null.setVisibility(View.GONE);
                        ly_content.removeAllViews();

                        for(int i = 0; i<list1.size();i++){
                            ly_content.addView(new Item_project_content(mContext,list1.get(i),false,false,categoryId,stageId,"0"));
                        }

                    }else{
                        tv_null.setVisibility(View.VISIBLE);
                    }
                }else if(type.equals("2")){
                    if(!BaseUtil.isSpace(list2)){
                        tv_null.setVisibility(View.GONE);
                        ly_content.removeAllViews();

                        for(int i = 0; i<list2.size();i++){
                            ly_content.addView(new Item_project_content(mContext,list2.get(i),false,false,categoryId,stageId,"0"));
                        }

                    }else{
                        tv_null.setVisibility(View.VISIBLE);
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


    private class DataList{
        public List<ResultDetailListData.NodeList> data;

        public List<ResultDetailListData.NodeList> getData() {
            return data;
        }

        public void setData(List<ResultDetailListData.NodeList> data) {
            this.data = data;
        }
    }


    public class Data{
        public List<ResultDetailListData.NodeList> list;

        public List<ResultDetailListData.NodeList> getList() {
            return list;
        }

        public void setList(List<ResultDetailListData.NodeList> list) {
            this.list = list;
        }
    }
}
