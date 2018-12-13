package pdkj.zhaoshang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.Summarize;
import pdkj.zhaoshang.utils.BaseUtil;
import pdkj.zhaoshang.utils.Item_Summarize;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.ToastUtil;
import pdkj.zhaoshang.utils.photovideo.takevideo.utils.LogUtils;

public class ProjectSummarizeActivity extends BaseActivity implements View.OnClickListener{


    private RelativeLayout rl_head_return;
    private RelativeLayout rl_submit;
    private String categoryId;
    private String stageId;
    private LinearLayout ly_list;
    private List<Summarize.SummarizeData> listArr;
    private EditText ed_content;
    private RadioGroup radiogroup;
    private RadioButton radioButton;
    private RadioButton radioButton2;
    private TextView tv_jieguo;
    private boolean isLOW = false;

    private String state1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_summarize);
        initView();
        initData();
    }


    private void initView(){
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        ly_list = (LinearLayout) findViewById(R.id.ly_list);
        ed_content = (EditText) findViewById(R.id.ed_content);
        tv_jieguo = (TextView) findViewById(R.id.tv_jieguo);
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        rl_submit = (RelativeLayout) findViewById(R.id.rl_submit);
        rl_head_return.setOnClickListener(this);

        rl_submit.setOnClickListener(this);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioButton:
                        state1 = "1";
                        break;
                    case R.id.radioButton2:
                        state1 = "2";
                        break;
                }
            }
        });
    }


    private void initData(){
        categoryId = getIntent().getExtras().getString("categoryId");
        stageId = getIntent().getExtras().getString("stageId");
        getCommonList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_head_return:
                finish();
                break;
            case R.id.rl_submit:
                addcommon();
                break;

        }
    }


    private void addcommon(){
        if(!isLOW){
            if(BaseUtil.isSpace(state1)){
                Toast.makeText(mContext,"请选择项目结果",Toast.LENGTH_LONG).show();
                return;
            }
        }

        if(BaseUtil.isSpace(ed_content.getText().toString().trim())){
            Toast.makeText(mContext,"请填写项目总结",Toast.LENGTH_LONG).show();
            return;
        }
        RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_STAGE_ADDCOMMENT);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        params.addBodyParameter("categoryId",categoryId);
        params.addBodyParameter("stageId",stageId);
        params.addBodyParameter("state1",state1);
        params.addBodyParameter("content1",ed_content.getText().toString().trim());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ToastUtil.shortToast(mContext,"添加成功");
                ed_content.setText("");
                hintKbOne();
                getCommonList();
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


    private void getCommonList(){
        RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_STAGE_COMMENTLIST);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        params.addBodyParameter("categoryId",categoryId);
        params.addBodyParameter("stageId",stageId);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                listArr = new Gson().fromJson(result, Summarize.class).getData();
                if(new Gson().fromJson(result, Summarize.class).getMessage().equals("1")){
                    isLOW = true;
                }else{
                    isLOW = false;
                }
                if(isLOW){
                    tv_jieguo.setVisibility(View.VISIBLE);
                    radiogroup.setVisibility(View.GONE);
                }else{
                    tv_jieguo.setVisibility(View.GONE);
                    radiogroup.setVisibility(View.VISIBLE);
                }
                if(!BaseUtil.isSpace(listArr)){
                    ly_list.removeAllViews();
                    for(Summarize.SummarizeData data:listArr){
                        ly_list.addView(new Item_Summarize(mContext,data));
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
}
