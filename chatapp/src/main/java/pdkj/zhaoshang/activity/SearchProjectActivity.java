package pdkj.zhaoshang.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.photovideo.takevideo.utils.LogUtils;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;

/**
 * 创建时间： 2018/2/11
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class SearchProjectActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout rl_head_return;
    private LinearLayout ly_content;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setSwipeBackEnable(false);
        initView();
        initData();
    }

    private void initView(){
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        ly_content = (LinearLayout) findViewById(R.id.ly_content);
        searchView = (SearchView) findViewById(R.id.search_view);
    }

    private void initData(){
        rl_head_return.setOnClickListener(this);
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                hintKbOne();
                RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_CATEGORY_CATEGORYLIST);
                params.addHeader("token", SharePreferenceManager.getCachedUserToken());
                params.addBodyParameter("title",string);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        List<ResultProjectData.ProjectData> list = new ArrayList<>();
                        ResultProjectData resultProjectData = new Gson().fromJson(result, ResultProjectData.class);
                        list = resultProjectData.getData();
                        if(!BaseUtil.isSpace(list)){
                            ly_content.removeAllViews();
                            for(ResultProjectData.ProjectData projectData:list){
                                ly_content.addView(new ItemProject(mContext,projectData));
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
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_head_return:
                hintKbOne();
                finish();
                break;
        }
    }
}
