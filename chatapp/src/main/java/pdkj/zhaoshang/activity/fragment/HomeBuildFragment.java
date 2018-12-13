package pdkj.zhaoshang.activity.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultProjectData;
import pdkj.zhaoshang.utils.BaseUtil;
import pdkj.zhaoshang.utils.ItemProject;
import pdkj.zhaoshang.utils.MyViewPage;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.photovideo.takevideo.utils.LogUtils;

/**
 * 创建时间： 2018/2/1
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
@SuppressLint("ValidFragment")
public class HomeBuildFragment extends Fragment {
    private View mRootView;

    private int fragmentID = 0;
    private MyViewPage vp;
    private LinearLayout ly_content;
    List<ResultProjectData.ProjectData> list = new ArrayList<>();

    public HomeBuildFragment(MyViewPage vp, int fragmentID,List<ResultProjectData.ProjectData> list){
        this.vp = vp;
        this.fragmentID =fragmentID;
        this.list = list;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        mRootView = layoutInflater.inflate(R.layout.test_main1,
                (ViewGroup) getActivity().findViewById(R.id.main_view), false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup p = (ViewGroup) mRootView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        vp.setObjectForPosition(mRootView,fragmentID);
        return mRootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        ly_content = mRootView.findViewById(R.id.ly_content);
        if(!BaseUtil.isSpace(list)){
            ly_content.removeAllViews();
            for(ResultProjectData.ProjectData projectData:list){
                ly_content.addView(new ItemProject(getActivity(),projectData));
            }
        }
    }
}
