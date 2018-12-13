package pdkj.zhaoshang.activity.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyachi.stepview.VerticalStepView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultDetailListData;
import pdkj.zhaoshang.entity.ResultNumData;
import pdkj.zhaoshang.utils.BaseUtil;
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
public class ProjectProductionFragment extends BaseFragment{

    private View mView;
    private LinearLayout ly_content;
    private Context mContext;
    private TextView tv_null;
    private String projectID;
    private String projectName;
    private VerticalStepView setpview2;
    private String nodeId;
    private int c;
    private List<ResultDetailListData.NodeList> list2 = new ArrayList<>();


    public ProjectProductionFragment(Context mContext,List<ResultDetailListData.NodeList> list,String projectID,String projectName,String num){
        this.list2 = list;
        this.projectID = projectID;
        this.mContext = mContext;
        this.projectName =projectName;
        this.nodeId = num;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.project_content2,null);
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
        setpview2 = mView.findViewById(R.id.step_view2);
    }

    private void initData(){

        if(!BaseUtil.isSpace(list2)){
            tv_null.setVisibility(View.GONE);
            ly_content.removeAllViews();

            final List<String> list0 = new ArrayList<>();

            for(int i = 0; i<list2.size();i++){
                list0.add(list2.get(i).getTitle());
            }
            RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_CATEGORY_NODELIST2CATEGORY);
            params.addHeader("token", SharePreferenceManager.getCachedUserToken());
            params.addBodyParameter("categoryId",projectID);
            params.addBodyParameter("stageId","2");
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    ResultNumData resultData = new Gson().fromJson(result,ResultNumData.class);

                    for(ResultDetailListData.NodeList nodeList:list2){
                        if(nodeList.getId().equals(resultData.getData().getNodeId())){
                            break;
                        }
                        c++;
                    }

                    setpview2.setStepsViewIndicatorComplectingPosition(c)//设置完成的步数
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
                    setpview2.setStepsViewIndicatorComplectingPosition(0)//设置完成的步数
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

    }
}
