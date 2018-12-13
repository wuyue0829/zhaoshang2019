package pdkj.zhaoshang.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.necer.ndialog.NDialog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.entity.Evaluate;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultEvaluateData;
import pdkj.zhaoshang.entity.ResultProjectData;
import pdkj.zhaoshang.utils.BaseUtil;
import pdkj.zhaoshang.utils.DoNumberUtil;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.ToastUtil;
import pdkj.zhaoshang.utils.photovideo.takevideo.utils.LogUtils;

/**
 * 创建时间： 2018/2/11
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ProjectEvaluateActivity extends BaseActivity implements View.OnClickListener{

    private EditText et_project_title;
    private TextView tv_nianfen;
    private EditText ed_zhandi;
    private EditText ed_touru;
    private EditText ed_gudingtouru;
    private EditText ed_chanchushouru;
    private EditText et_chanchuqz;
    private EditText ed_shuijin;
    private EditText et_shuijinqz;
    private EditText ed_lirun;
    private EditText et_lirunqz;
    private EditText ed_renshu;
    private EditText et_renshuqz;
    private EditText ed_nenghao;
    private EditText et_nenghaoqz;
    private EditText et_zhejiuzhi;
    private EditText et_gongzi;
    private TextView tv_title;
    private TextView tv_zenjiazhi;
    private TextView tv_top_title;
    private TextView tv_one;
    private CardView cardview1;
    private Button bt_submit;
    private RelativeLayout rl_project;
    private RelativeLayout rl_leixing;
    private RelativeLayout rl_head_return;
    private int xiangmuID;

    private float chancinthuqz;
    private float shuijinqz;
    private float lirunqz;
    private float renshuqz;
    private float nenghaoqz;
    private Evaluate evaluate;
    private String type;
    private List<String> listData = new ArrayList<>();
    List<ResultProjectData.ProjectData> list = new ArrayList<>();
    List<String> listAll = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_evaluate);
        setSwipeBackEnable(false);
        initView();
        initData();
    }


    private void initView(){
        type = getIntent().getExtras().getString("type");
        rl_project = (RelativeLayout) findViewById(R.id.rl_project);
        rl_leixing = (RelativeLayout) findViewById(R.id.rl_leixing);
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        et_project_title = (EditText) findViewById(R.id.et_project_title);
        tv_nianfen = (TextView) findViewById(R.id.tv_nianfen);
        cardview1 = (CardView) findViewById(R.id.cardview1);
        tv_title =  (TextView) findViewById(R.id.tv_title);
        tv_one = (TextView) findViewById(R.id.tv_one);
        ed_zhandi = (EditText) findViewById(R.id.ed_zhandi);
        ed_touru = (EditText) findViewById(R.id.ed_touru);
        ed_gudingtouru = (EditText) findViewById(R.id.ed_gudingtouru);
        ed_chanchushouru = (EditText) findViewById(R.id.ed_chanchushouru);
        et_chanchuqz = (EditText) findViewById(R.id.et_chanchuqz);
        ed_shuijin = (EditText) findViewById(R.id.ed_shuijin);
        et_shuijinqz = (EditText) findViewById(R.id.et_shuijinqz);
        ed_lirun = (EditText) findViewById(R.id.ed_lirun);
        et_lirunqz = (EditText) findViewById(R.id.et_lirunqz);
        ed_renshu = (EditText) findViewById(R.id.ed_renshu);
        et_renshuqz = (EditText) findViewById(R.id.et_renshuqz);
        ed_nenghao = (EditText) findViewById(R.id.ed_nenghao);
        et_nenghaoqz = (EditText) findViewById(R.id.et_nenghaoqz);
        tv_zenjiazhi = (TextView) findViewById(R.id.tv_zenjiazhi);
        tv_top_title = (TextView) findViewById(R.id.tv_top_title);
        et_zhejiuzhi = (EditText) findViewById(R.id.et_zhejiuzhi);
        et_gongzi = (EditText) findViewById(R.id.et_gongzi);
        if(type.equals("0")){
            tv_top_title.setText("招商项目辅助决策模型");
            tv_one.setVisibility(View.GONE);
            cardview1.setVisibility(View.GONE);
        }else{
            tv_top_title.setText("招商项目辅助决策模型");
        }

        bt_submit = (Button) findViewById(R.id.bt_submit);
        for(int i = 18; i >= 0;i--){
            listData.add(2000+i+"");
        }
        for(int i = 79; i >= 0;i--){
            listData.add(1920+i+"");
        }

        RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_CATEGORY_CATEGORYLIST);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ResultProjectData resultProjectData = new Gson().fromJson(result, ResultProjectData.class);
                for(ResultProjectData.ProjectData projectData:resultProjectData.getData()){
                    list.add(projectData);
                    listAll.add(projectData.getCategoryTitle());
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

    private void initData(){
        bt_submit.setOnClickListener(this);
        rl_project.setOnClickListener(this);
        rl_leixing.setOnClickListener(this);
        rl_head_return.setOnClickListener(this);

        ed_shuijin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                tv_zenjiazhi.setText(DoNumberUtil.floatNullDowith(ed_shuijin.getText().toString().trim())+
                        DoNumberUtil.floatNullDowith(ed_lirun.getText().toString().trim())+
                        DoNumberUtil.floatNullDowith(et_zhejiuzhi.getText().toString().trim())+
                        DoNumberUtil.floatNullDowith(et_gongzi.getText().toString().trim())+"");
            }
        });


        ed_lirun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                tv_zenjiazhi.setText(DoNumberUtil.floatNullDowith(ed_shuijin.getText().toString().trim())+
                        DoNumberUtil.floatNullDowith(ed_lirun.getText().toString().trim())+
                        DoNumberUtil.floatNullDowith(et_zhejiuzhi.getText().toString().trim())+
                        DoNumberUtil.floatNullDowith(et_gongzi.getText().toString().trim())+"");
            }
        });

        et_zhejiuzhi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                tv_zenjiazhi.setText(DoNumberUtil.floatNullDowith(ed_shuijin.getText().toString().trim())+
                        DoNumberUtil.floatNullDowith(ed_lirun.getText().toString().trim())+
                        DoNumberUtil.floatNullDowith(et_zhejiuzhi.getText().toString().trim())+
                        DoNumberUtil.floatNullDowith(et_gongzi.getText().toString().trim())+"");
            }
        });

        et_gongzi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                tv_zenjiazhi.setText(DoNumberUtil.floatNullDowith(ed_shuijin.getText().toString().trim())+
                        DoNumberUtil.floatNullDowith(ed_lirun.getText().toString().trim())+
                        DoNumberUtil.floatNullDowith(et_zhejiuzhi.getText().toString().trim())+
                        DoNumberUtil.floatNullDowith(et_gongzi.getText().toString().trim())+"");
            }
        });


    }


    private boolean setQuanZhongInput(){
        if(BaseUtil.isSpace(et_chanchuqz.getText().toString().trim())){
            chancinthuqz = 0;
        }else{
            chancinthuqz = DoNumberUtil.floatNullDowith(et_chanchuqz.getText().toString().trim());
        }

        if(BaseUtil.isSpace(et_shuijinqz.getText().toString().trim())){
            shuijinqz = 0;
        }else{
            shuijinqz = DoNumberUtil.floatNullDowith(et_shuijinqz.getText().toString().trim());
        }

        if(BaseUtil.isSpace(et_lirunqz.getText().toString().trim())){
            lirunqz = 0;
        }else{
            lirunqz = DoNumberUtil.floatNullDowith(et_lirunqz.getText().toString().trim());
        }

        if(BaseUtil.isSpace(et_renshuqz.getText().toString().trim())){
            renshuqz = 0;
        }else{
            renshuqz = DoNumberUtil.floatNullDowith(et_renshuqz.getText().toString().trim());
        }

        if(BaseUtil.isSpace(et_nenghaoqz.getText().toString().trim())){
            nenghaoqz = 0;
        }else{
            nenghaoqz = DoNumberUtil.floatNullDowith(et_nenghaoqz.getText().toString().trim());
        }

        if((chancinthuqz+shuijinqz+lirunqz+renshuqz+nenghaoqz) != 100){
            ToastUtil.shortToast(mContext,"权重总和必须等于100，请检查权重是否填写合适");
            return false;
        }else{
            return true;
        }
    }


    private boolean checkInput(){

        if(type.equals("1")){
            if(BaseUtil.isSpace(tv_title.getText().toString().trim())){
                ToastUtil.shortToast(mContext,"请选择项目");
                return false;
            }

            if(BaseUtil.isSpace(tv_nianfen.getText().toString().trim())){
                ToastUtil.shortToast(mContext,"请选择年份");
                return false;
            }
        }

        if(BaseUtil.isSpace(ed_zhandi.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请输入占地面积");
            return false;
        }

        if(BaseUtil.isSpace(ed_touru.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请输入总资产投入");
            return false;
        }

        if(BaseUtil.isSpace(ed_gudingtouru.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请输入固定资产投入");
            return false;
        }

        if(BaseUtil.isSpace(ed_chanchushouru.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请输入产出收入");
            return false;
        }

        if(BaseUtil.isSpace(ed_shuijin.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请输入实缴税金");
            return false;
        }

        if(BaseUtil.isSpace(ed_lirun.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请输入营业利润");
            return false;
        }

        if(BaseUtil.isSpace(ed_renshu.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请输入从业人数");
            return false;
        }

        if(BaseUtil.isSpace(ed_nenghao.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请输入能源消耗");
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_submit:
                if(checkInput()){
                    if(setQuanZhongInput()){
                        if(type.equals("0")){
                            //评估
                            evaluate = new Evaluate();
                            evaluate.setDepreciation(et_zhejiuzhi.getText().toString().trim());
                            evaluate.setEnergyUse(ed_nenghao.getText().toString().trim());
                            evaluate.setEnergyWeight(et_nenghaoqz.getText().toString().trim());
                            evaluate.setIncrease(tv_zenjiazhi.getText().toString().trim());
                            evaluate.setInvestment(ed_gudingtouru.getText().toString().trim());
                            evaluate.setJobNum(ed_renshu.getText().toString().trim());
                            evaluate.setJobWeight(et_renshuqz.getText().toString().trim());
                            evaluate.setLandArea(ed_zhandi.getText().toString().trim());
                            evaluate.setOutPut(ed_chanchushouru.getText().toString().trim());
                            evaluate.setOutPutWeight(et_chanchuqz.getText().toString().trim());
                            evaluate.setProfit(ed_lirun.getText().toString().trim());
                            evaluate.setProfitWeight(et_lirunqz.getText().toString().trim());
                            evaluate.setTaxes(ed_shuijin.getText().toString().trim());
                            evaluate.setTaxesWeight(et_shuijinqz.getText().toString().trim());
                            evaluate.setTotalAmount(ed_touru.getText().toString().trim());
                            evaluate.setWages(et_gongzi.getText().toString().trim());
                            evaluate.setYear(tv_nianfen.getText().toString().trim());
                            RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_CONCLUSION_EVALUATE);
                            params.addHeader("token", SharePreferenceManager.getCachedUserToken());
                            params.addBodyParameter("jsonObject",new Gson().toJson(evaluate),"application/json");
                            x.http().post(params, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    if(new Gson().fromJson(result, ResultEvaluateData.class).getResult().equals("0")){
                                        Intent intent = new Intent(mContext,EvaluteReportActivity.class);
                                        intent.putExtra("result",result);
                                        mContext.startActivity(intent);
                                        finish();
                                    }else if(new Gson().fromJson(result, ResultEvaluateData.class).getResult().equals("700")){
                                        ToastUtil.shortToast(mContext,new Gson().fromJson(result, ResultEvaluateData.class).getMessage()+",请选择其他年份");
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
                        }else{
                            //汇总
                            evaluate = new Evaluate();
                            evaluate.setCategoryId(list.get(xiangmuID-1).getId()+"");
                            evaluate.setDepreciation(et_zhejiuzhi.getText().toString().trim());
                            evaluate.setEnergyUse(ed_nenghao.getText().toString().trim());
                            evaluate.setEnergyWeight(et_nenghaoqz.getText().toString().trim());
                            evaluate.setIncrease(tv_zenjiazhi.getText().toString().trim());
                            evaluate.setInvestment(ed_gudingtouru.getText().toString().trim());
                            evaluate.setJobNum(ed_renshu.getText().toString().trim());
                            evaluate.setJobWeight(et_renshuqz.getText().toString().trim());
                            evaluate.setLandArea(ed_zhandi.getText().toString().trim());
                            evaluate.setOutPut(ed_chanchushouru.getText().toString().trim());
                            evaluate.setOutPutWeight(et_chanchuqz.getText().toString().trim());
                            evaluate.setProfit(ed_lirun.getText().toString().trim());
                            evaluate.setProfitWeight(et_lirunqz.getText().toString().trim());
                            evaluate.setTaxes(ed_shuijin.getText().toString().trim());
                            evaluate.setTaxesWeight(et_shuijinqz.getText().toString().trim());
                            evaluate.setTotalAmount(ed_touru.getText().toString().trim());
                            evaluate.setWages(et_gongzi.getText().toString().trim());
                            evaluate.setYear(tv_nianfen.getText().toString().trim());
                            RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_CONCLUSION_ADD);
                            params.addHeader("token", SharePreferenceManager.getCachedUserToken());
                            params.addBodyParameter("jsonObject",new Gson().toJson(evaluate),"application/json");
                            x.http().post(params, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    if(new Gson().fromJson(result, ResultEvaluateData.class).getResult().equals("0")){
                                        Intent intent = new Intent(mContext,EvaluteReportActivity.class);
                                        intent.putExtra("result",result);
                                        mContext.startActivity(intent);
                                        finish();
                                    }else if(new Gson().fromJson(result, ResultEvaluateData.class).getResult().equals("700")){
                                        ToastUtil.shortToast(mContext,new Gson().fromJson(result, ResultEvaluateData.class).getMessage()+",请选择其他年份");
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
                }
                break;

            case R.id.rl_project:
                xiangMuIDDialog(listAll);
                break;
            case R.id.rl_leixing:
                xiangMuNianfengDialog(listData);
                break;
            case R.id.rl_head_return:
                finish();
                break;


        }
    }



    /**
     * 类型下拉显示
     */
    public void xiangMuIDDialog(List<String> list){
        new NDialog(mContext)
                .setItems(list.toArray(new String[list.size()]))
                .setItemGravity(Gravity.LEFT)
                .setItemColor(Color.parseColor("#000000"))
                .setItemHeigh(50)
                .setItemSize(14)
                .setDividerHeigh(1)
                .setAdapter(null)
                .setDividerColor(Color.parseColor("#c1c1c1"))
                .setHasDivider(true)
                .setOnChoiceListener(new NDialog.OnChoiceListener() {
                    @Override
                    public void onClick(String item, int which) {
                        tv_title.setText(item);
                        xiangmuID = (which+1);
                    }
                }).create(NDialog.CHOICE).show();
    }


    /**
     * 类型下拉显示
     */
    public void xiangMuNianfengDialog(List<String> list){
        new NDialog(mContext)
                .setItems(list.toArray(new String[list.size()]))
                .setItemGravity(Gravity.LEFT)
                .setItemColor(Color.parseColor("#000000"))
                .setItemHeigh(50)
                .setItemSize(14)
                .setDividerHeigh(1)
                .setAdapter(null)
                .setDividerColor(Color.parseColor("#c1c1c1"))
                .setHasDivider(true)
                .setOnChoiceListener(new NDialog.OnChoiceListener() {
                    @Override
                    public void onClick(String item, int which) {
                        tv_nianfen.setText(item);
                    }
                }).create(NDialog.CHOICE).show();
    }
}
