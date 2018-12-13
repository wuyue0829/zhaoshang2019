package pdkj.zhaoshang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
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
import pdkj.zhaoshang.activity.fragment.ProjectAttractFragment;
import pdkj.zhaoshang.activity.fragment.ProjectBuildFragment;
import pdkj.zhaoshang.activity.fragment.ProjectProductionFragment;
import pdkj.zhaoshang.database.CategoryTpyeEntry;
import pdkj.zhaoshang.database.DataDaoConfig;
import pdkj.zhaoshang.database.FriendEntry;
import pdkj.zhaoshang.database.UserEntry;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultDetailListData;
import pdkj.zhaoshang.entity.ResultNewProjectData;
import pdkj.zhaoshang.entity.ResultProjectData;
import pdkj.zhaoshang.utils.BaseUtil;
import pdkj.zhaoshang.utils.DoNumberUtil;
import pdkj.zhaoshang.utils.MyViewPage;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.photovideo.takevideo.utils.LogUtils;

/**
 * 创建时间： 2018/2/9
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class ProjectDetailActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout rl_head_return;
    private RelativeLayout rl_zhaoshang;
    private RelativeLayout rl_jianshe;
    private RelativeLayout rl_shenchan;
    private RelativeLayout rl_jianshe_main;
    private String title;
    private TextView tv_title;
    private TextView tv_zhaoshang;
    private TextView tv_jianshe;
    private TextView tv_shenchan;
    private String categoryId;
    private String stageId;
    private String type;
    private EditText ed_title1;
    private EditText ed_leixing;
    private EditText ed_chanye;
    private EditText ed_hangye;
    private EditText ed_fuwuduixiang;
    private EditText ed_fuzeren;
    private EditText ed_beizhu;
    private EditText ed_shijian;
    private ViewPager container;
    private MyViewPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    List<ResultDetailListData.NodeList> list = new ArrayList<>();
    List<ResultDetailListData.NodeList> list1 = new ArrayList<>();
    List<ResultDetailListData.NodeList> list2 = new ArrayList<>();
    ProjectAttractFragment tab001;
    ProjectBuildFragment tab002;
    ProjectProductionFragment tab003;
    private List<FriendEntry> findAll3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        title = getIntent().getExtras().getString("title");
        categoryId = getIntent().getExtras().getString("categoryId");
        type = getIntent().getExtras().getString("type");
        if(!BaseUtil.isSpace(getIntent().getExtras().getString("stageId"))){
            stageId = getIntent().getExtras().getString("stageId");
        }
        setSwipeBackEnable(false);
        initView();
        initData();
        getDetaily();
    }


    private void initView(){
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        rl_zhaoshang = (RelativeLayout) findViewById(R.id.rl_zhaoshang);
        rl_jianshe = (RelativeLayout) findViewById(R.id.rl_jianshe);
        rl_shenchan = (RelativeLayout) findViewById(R.id.rl_shenchan);
        rl_jianshe_main = (RelativeLayout) findViewById(R.id.rl_jianshe_main);

        tv_zhaoshang = (TextView) findViewById(R.id.tv_zhaoshang);
        tv_jianshe = (TextView) findViewById(R.id.tv_jianshe);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_shenchan = (TextView) findViewById(R.id.tv_shenchan);
        container = (ViewPager) findViewById(R.id.container);

        ed_title1 = (EditText) findViewById(R.id.ed_title1);
        ed_leixing = (EditText) findViewById(R.id.ed_leixing);
        ed_shijian = (EditText) findViewById(R.id.ed_shijian);
        ed_chanye = (EditText) findViewById(R.id.ed_chanye);
        ed_hangye = (EditText) findViewById(R.id.ed_hangye);
        ed_fuwuduixiang = (EditText) findViewById(R.id.ed_fuwuduixiang);
        ed_fuzeren = (EditText) findViewById(R.id.ed_fuzeren);
        ed_beizhu = (EditText) findViewById(R.id.ed_beizhu);
    }

    private void initData(){
        rl_head_return.setOnClickListener(this);
        rl_zhaoshang.setOnClickListener(this);
        rl_jianshe.setOnClickListener(this);
        rl_shenchan.setOnClickListener(this);
        tv_title.setText(title);
        if(type.equals("1")){
            rl_jianshe_main.setVisibility(View.VISIBLE);
        }else{
            rl_jianshe_main.setVisibility(View.GONE);
        }
        initList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_head_return:
                finish();
                break;
            case R.id.rl_zhaoshang:
                Intent intent = new Intent(mContext,JieDuanDetailActivity.class);
                intent.putExtra("type","0");
                intent.putExtra("categoryId",categoryId);
                intent.putExtra("stageId",stageId);
                startActivity(intent);
                break;
            case R.id.rl_jianshe:
                Intent intent1 = new Intent(mContext,JieDuanDetailActivity.class);
                intent1.putExtra("type","1");
                intent1.putExtra("categoryId",categoryId);
                intent1.putExtra("stageId",stageId);
                startActivity(intent1);
                break;
            case R.id.rl_shenchan:
                Intent intent2 = new Intent(mContext,JieDuanDetailActivity.class);
                intent2.putExtra("type","2");
                intent2.putExtra("categoryId",categoryId);
                intent2.putExtra("stageId",stageId);
                startActivity(intent2);
                break;
        }
    }


    private void initList(){
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
                tab001 = new ProjectAttractFragment(mContext,list,categoryId,title,null);

                for( ResultDetailListData.NodeList projectData:resultProjectData.getData()){
                    if(projectData.getStageId().equals("1")){
                        list1.add(projectData);
                    }
                }

                if(type.equals("1")){
                    tab002 = new ProjectBuildFragment(mContext,list1,categoryId,title,null);
                }
                for( ResultDetailListData.NodeList projectData:resultProjectData.getData()){
                    if(projectData.getStageId().equals("2")){
                        list2.add(projectData);
                    }
                }
                tab003 = new ProjectProductionFragment(mContext,list2,categoryId,title,null);
                mFragments.clear();
                mFragments.add(tab001);
                if(type.equals("1")){
                    mFragments.add(tab002);
                }
                mFragments.add(tab003);
                if(type.equals("1")){
                    container.setOffscreenPageLimit(3);
                }else{
                    container.setOffscreenPageLimit(2);
                }
                mAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),mFragments);
                container.setAdapter(mAdapter);


                if(type.equals("1")){
                    container.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                        @Override
                        public void onPageSelected(int arg0) {
                            //当前选中的Fragment 下标
                            int currentItem = container.getCurrentItem();
                            //把图片全设置为暗的
                            switch (currentItem) {
                                case 0:
                                    tv_zhaoshang.setTextColor(getResources().getColor(R.color.yellow));
                                    tv_jianshe.setTextColor(getResources().getColor(R.color.white));
                                    tv_shenchan.setTextColor(getResources().getColor(R.color.white));
                                    break;
                                case 1:
                                    tv_zhaoshang.setTextColor(getResources().getColor(R.color.white));
                                    tv_jianshe.setTextColor(getResources().getColor(R.color.yellow));
                                    tv_shenchan.setTextColor(getResources().getColor(R.color.white));
                                    break;
                                case 2:
                                    tv_zhaoshang.setTextColor(getResources().getColor(R.color.white));
                                    tv_jianshe.setTextColor(getResources().getColor(R.color.white));
                                    tv_shenchan.setTextColor(getResources().getColor(R.color.yellow));
                                    break;
                                default:
                                    break;
                            }
                        }

                        @Override
                        public void onPageScrolled(int arg0, float arg1, int arg2) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int arg0) {

                        }
                    });
                }else{
                    container.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                        @Override
                        public void onPageSelected(int arg0) {
                            //当前选中的Fragment 下标
                            int currentItem = container.getCurrentItem();
                            //把图片全设置为暗的
                            switch (currentItem) {
                                case 0:
                                    tv_zhaoshang.setTextColor(getResources().getColor(R.color.yellow));
                                    tv_jianshe.setTextColor(getResources().getColor(R.color.white));
                                    tv_shenchan.setTextColor(getResources().getColor(R.color.white));
                                    break;
                                case 1:
                                    tv_zhaoshang.setTextColor(getResources().getColor(R.color.white));
                                    tv_jianshe.setTextColor(getResources().getColor(R.color.white));
                                    tv_shenchan.setTextColor(getResources().getColor(R.color.yellow));
                                    break;
                                default:
                                    break;
                            }
                        }

                        @Override
                        public void onPageScrolled(int arg0, float arg1, int arg2) {

                        }

                        @Override
                        public void onPageScrollStateChanged(int arg0) {

                        }
                    });
                }

                setSelect(0);
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


    private void setSelect(int i){
        //改变内容区域，把图片设置为亮的
        switch (i) {
            case 0:
                tv_zhaoshang.setTextColor(getResources().getColor(R.color.yellow));
                tv_jianshe.setTextColor(getResources().getColor(R.color.white));
                tv_shenchan.setTextColor(getResources().getColor(R.color.white));
                break;
            case 1:
                if(type.equals("1")){
                    tv_zhaoshang.setTextColor(getResources().getColor(R.color.white));
                    tv_jianshe.setTextColor(getResources().getColor(R.color.yellow));
                    tv_shenchan.setTextColor(getResources().getColor(R.color.white));
                }else{
                    tv_zhaoshang.setTextColor(getResources().getColor(R.color.white));
                    tv_jianshe.setTextColor(getResources().getColor(R.color.white));
                    tv_shenchan.setTextColor(getResources().getColor(R.color.yellow));
                }

                break;
            case 2:
                tv_zhaoshang.setTextColor(getResources().getColor(R.color.white));
                tv_jianshe.setTextColor(getResources().getColor(R.color.white));
                tv_shenchan.setTextColor(getResources().getColor(R.color.yellow));
                break;
            default:
                break;
        }
        //切换Fragment
        container.setCurrentItem(i);
    }


    public class MyViewPagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> list;

        public MyViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((Fragment) obj).getView();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Fragment fragment = ((Fragment) object);
        }

    }


    private void getDetaily(){
        RequestParams params = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_CATEGORY_DETAIL);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        params.addBodyParameter("id",categoryId);
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
                try {
                    ed_hangye.setText(db.selector(CategoryTpyeEntry.class).expr(" parentId = " + resultNewProjectData.getData().getIndustry()).and(WhereBuilder.b("model", "=", 1)).findAll().get(0).getName());
                } catch (DbException e) {
                    e.printStackTrace();
                }
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
}
