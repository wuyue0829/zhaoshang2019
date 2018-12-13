package pdkj.zhaoshang.activity.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.VerticalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.google.gson.Gson;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import pdkj.zhaoshang.R;
import pdkj.zhaoshang.activity.ProjectDetailActivity;
import pdkj.zhaoshang.activity.ProjectSummarizeActivity;
import pdkj.zhaoshang.database.CategoryTpyeEntry;
import pdkj.zhaoshang.database.DataDaoConfig;
import pdkj.zhaoshang.database.FriendEntry;
import pdkj.zhaoshang.database.UserEntry;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.NodeEntity;
import pdkj.zhaoshang.entity.ResultData;
import pdkj.zhaoshang.entity.ResultDetailListData;
import pdkj.zhaoshang.entity.ResultEventData;
import pdkj.zhaoshang.entity.ResultNewProjectData;
import pdkj.zhaoshang.entity.ResultNumData;
import pdkj.zhaoshang.entity.Summarize;
import pdkj.zhaoshang.utils.BaseUtil;
import pdkj.zhaoshang.utils.DoNumberUtil;
import pdkj.zhaoshang.utils.Item_Nodes;
import pdkj.zhaoshang.utils.Item_Summarize;
import pdkj.zhaoshang.utils.Item_project_content;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.photovideo.takevideo.utils.LogUtils;

/**
 * 创建时间： 2018/2/9
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
@SuppressLint("ValidFragment")
public class ProjectAttractFragment extends BaseFragment{

    private View mView;
    private LinearLayout ly_content;
    private Context mContext;
    private TextView tv_null;
    private String projectID;
    private String projectName;
    private Button button;
    private LinearLayout ly_list;
    private List<Summarize.SummarizeData> listArr;
    private EditText ed_content;
    private RadioGroup radiogroup;
    private RadioButton radioButton;
    private RadioButton radioButton2;
    private TextView tv_jieguo;
    private boolean isLOW = false;
    private int c = 0;

    private EditText ed_title1;
    private EditText ed_leixing;
    private EditText ed_chanye;
    private EditText ed_hangye;
    private EditText ed_fuwuduixiang;
    private EditText ed_fuzeren;
    private EditText ed_beizhu;
    private EditText ed_shijian;
    private VerticalStepView setpview;
    private List<ResultDetailListData.NodeList> list = new ArrayList<>();
    private List<FriendEntry> findAll3 = new ArrayList<>();
    private String nodeId;

    public ProjectAttractFragment(Context mContext,List<ResultDetailListData.NodeList> list,String projectID,String projectName,String num){
        this.list = list;
        this.nodeId = num;
        this.projectID = projectID;
        this.mContext = mContext;
        this.projectName = projectName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.project_content,null);
        return mView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initView();
        initData();
    }


    private void initView(){
        ly_content = mView.findViewById(R.id.ly_content);
        tv_null = mView.findViewById(R.id.tv_null);
        button = mView.findViewById(R.id.button);
        button.setVisibility(View.VISIBLE);
        ly_list = mView.findViewById(R.id.ly_list);
        ed_content = mView. findViewById(R.id.ed_content);
        tv_jieguo =  mView.findViewById(R.id.tv_jieguo);
        radiogroup = mView.findViewById(R.id.radiogroup);
        radioButton =  mView.findViewById(R.id.radioButton);
        radioButton2 = mView.findViewById(R.id.radioButton2);
        ed_title1 = mView.findViewById(R.id.ed_title1);
        ed_leixing = mView.findViewById(R.id.ed_leixing);
        ed_shijian = mView.findViewById(R.id.ed_shijian);
        ed_chanye = mView.findViewById(R.id.ed_chanye);
        ed_hangye = mView.findViewById(R.id.ed_hangye);
        ed_fuwuduixiang = mView.findViewById(R.id.ed_fuwuduixiang);
        ed_fuzeren = mView.findViewById(R.id.ed_fuzeren);
        ed_beizhu = mView.findViewById(R.id.ed_beizhu);
        setpview = mView.findViewById(R.id.step_view);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,ProjectSummarizeActivity.class);
                intent.putExtra("categoryId",projectID);
                intent.putExtra("stageId",list.get(0).getStageId());
                startActivity(intent);
            }
        });
    }

    private void initData(){

        if(!BaseUtil.isSpace(list)){
            tv_null.setVisibility(View.GONE);
            ly_content.removeAllViews();
            final List<String> list0 = new ArrayList<>();

            for(int i = 0; i<list.size();i++){
                list0.add(list.get(i).getTitle());
            }

            RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_CATEGORY_NODELIST2CATEGORY);
            params.addHeader("token", SharePreferenceManager.getCachedUserToken());
            params.addBodyParameter("categoryId",projectID);
            params.addBodyParameter("stageId","0");
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    ResultNumData resultData = new Gson().fromJson(result,ResultNumData.class);

                    for(ResultDetailListData.NodeList nodeList:list){
                        if(nodeList.getId().equals(resultData.getData().getNodeId())){
                            break;
                        }
                        c++;
                    }

                    setpview.setStepsViewIndicatorComplectingPosition(c)//设置完成的步数
                            .reverseDraw(false)//default is true
                            .setStepViewTexts(list0)//总步骤
                            .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                            .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark))//设置StepsViewIndicator完成线的颜色
                            .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.blue))//设置StepsViewIndicator未完成线的颜色
                            .setStepViewComplectedTextColor(ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark))//设置StepsView text完成线的颜色
                            .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.blue))//设置StepsView text未完成线的颜色
                            .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                            .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                            .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.drawable.attention));//设置StepsViewIndicator AttentionIcon


                }
                //请求异常后的回调方法
                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    LogUtils.e("ex",ex.getMessage());
                    setpview.setStepsViewIndicatorComplectingPosition(0)//设置完成的步数
                            .reverseDraw(false)//default is true
                            .setStepViewTexts(list0)//总步骤
                            .setLinePaddingProportion(0.85f)//设置indicator线与线间距的比例系数
                            .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark))//设置StepsViewIndicator完成线的颜色
                            .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getActivity(), R.color.blue))//设置StepsViewIndicator未完成线的颜色
                            .setStepViewComplectedTextColor(ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark))//设置StepsView text完成线的颜色
                            .setStepViewUnComplectedTextColor(ContextCompat.getColor(getActivity(), R.color.blue))//设置StepsView text未完成线的颜色
                            .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getActivity(), R.drawable.complted))//设置StepsViewIndicator CompleteIcon
                            .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getActivity(), R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
                            .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getActivity(), R.drawable.attention));//设置StepsViewIndicator AttentionIcon
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
            tv_null.setVisibility(View.VISIBLE);
        }
        getCommonList();
    }

    private void getDetaily(){
        RequestParams params = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_CATEGORY_DETAIL);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        params.addBodyParameter("id",projectID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                DbManager db = x.getDb(DataDaoConfig.getDaoConfig());
                ResultNewProjectData resultNewProjectData = new Gson().fromJson(result,ResultNewProjectData.class);
                if(resultNewProjectData.getData().getType().equals("1")){
                    ed_leixing.setText("土建项目");
                }else{
                    ed_leixing.setText("非土建项目");
                }
                ed_fuwuduixiang.setText(resultNewProjectData.getData().getCustomer());
                ed_shijian.setText(resultNewProjectData.getData().getStartDate());

                ed_title1.setText(resultNewProjectData.getData().getTitle());
                try {
                    List<CategoryTpyeEntry> list = db.selector(CategoryTpyeEntry.class).where("id","==",DoNumberUtil.intNullDowith(resultNewProjectData.getData().getTrade())).findAll();
                    if(list.size()>0){
                        ed_chanye.setText(list.get(0).getName());
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                ed_leixing.setText(resultNewProjectData.getData().getTitle());
                try {
                    ed_hangye.setText(db.selector(CategoryTpyeEntry.class).expr(" parentId = " + resultNewProjectData.getData().getIndustry()).and(WhereBuilder.b("model", "=", 1)).findAll().get(0).getName());
                } catch (DbException e) {
                    e.printStackTrace();
                }
                ed_fuwuduixiang.setText(resultNewProjectData.getData().getTitle());
                if(BaseUtil.isSpace(resultNewProjectData.getData().getSummary())){
                    ed_beizhu.setText("无");
                }else{
                    ed_beizhu.setText(resultNewProjectData.getData().getSummary());
                }
                UserEntry user = UserEntry.getUser(JMessageClient.getMyInfo().getUserName(),
                        JMessageClient.getMyInfo().getAppKey());
                findAll3 = user.getFriends();
                for(FriendEntry friendEntry1:findAll3){
                    if(friendEntry1.username.equals(resultNewProjectData.getData().getHeader())){
                        ed_fuzeren.setText(friendEntry1.nickName);
                    }
                }

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

    private void getCommonList(){
        RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_STAGE_COMMENTLIST);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        params.addBodyParameter("categoryId",projectID);
        params.addBodyParameter("stageId",list.get(0).getStageId());
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
