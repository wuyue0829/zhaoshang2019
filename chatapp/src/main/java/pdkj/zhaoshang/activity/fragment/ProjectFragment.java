package pdkj.zhaoshang.activity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.activity.MatterNeedActivity;
import pdkj.zhaoshang.activity.MyProjectActivity;
import pdkj.zhaoshang.activity.ProjectAllActivity;
import pdkj.zhaoshang.activity.ProjectEvaluateActivity;
import pdkj.zhaoshang.activity.SearchProjectActivity;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultBannerData;
import pdkj.zhaoshang.entity.ResultProjectData;
import pdkj.zhaoshang.utils.GlideImageLoaders;
import pdkj.zhaoshang.utils.MyViewPage;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.photovideo.takevideo.utils.LogUtils;

/**
 * Created by ${chenyn} on 2017/2/20.
 */

public class ProjectFragment extends BaseFragment implements View.OnClickListener{

    private Activity mContext;
    private View mRootView;
    private RelativeLayout rl_matter_need;
    private ScrollView sl_main;
    private RelativeLayout rl_toolbar;
    private ImageView im_top;
    private Banner banner_home;
    private TextView tv_title;
    private MyViewPage container;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<>();
    private TextView tv_zhaoshang;
    private TextView tv_jianshe;
    private TextView tv_shenchan;
    private View view_zhaoshang;
    private View view_jianshe;
    private View view_shenchan;
    private TextView tv_zhaoshang_top;
    private TextView tv_jianshe_top;
    private TextView tv_shenchan_top;
    private TextView tv_shownot;
    private View view_zhaoshang_top;
    private View view_jianshe_top;
    private View view_shenchan_top;
    private RelativeLayout rl_zhaoshang;
    private RelativeLayout rl_jianshe;
    private RelativeLayout rl_shenchan;

    private RelativeLayout rl_zhaoshang_top1;
    private RelativeLayout rl_jianshe_top;
    private RelativeLayout rl_huizong;
    private RelativeLayout rl_shenchan_top;
    private RelativeLayout rl_myproject;
    private RelativeLayout rl_project_all;
    private TextView tv_search;
    private LinearLayout rl_zhaoshang_top;
    private LinearLayout ly_menu;
    private FloatingActionButton fab01Add;

    private int height1 = 0;
    private int height2 = 0;
    private int height3 = 0;
    private List<String> bannerList;
    List<ResultProjectData.ProjectData> list = new ArrayList<>();
    List<ResultProjectData.ProjectData> list1 = new ArrayList<>();
    List<ResultProjectData.ProjectData> list2 = new ArrayList<>();

    HomeAttractFragment tab001;
    HomeBuildFragment tab002;
    HomeProductionFragmnet tab003;

    private int isSelect = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        mRootView = layoutInflater.inflate(R.layout.fragment_project,
                (ViewGroup) getActivity().findViewById(R.id.main_view), false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup p = (ViewGroup) mRootView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    @SuppressLint("NewApi")
    private void initView(){
        rl_matter_need = (RelativeLayout) mContext.findViewById(R.id.rl_matter_need);
        sl_main = (ScrollView) mContext.findViewById(R.id.sl_main);
        banner_home = (Banner) mContext.findViewById(R.id.banner_home);
        rl_toolbar = (RelativeLayout) mContext.findViewById(R.id.rl_toolbar);
        rl_project_all = mContext.findViewById(R.id.rl_project_all);
        im_top = (ImageView) mContext.findViewById(R.id.im_top);
        tv_title = (TextView) mContext.findViewById(R.id.tv_title);
        tv_search = (TextView) mContext.findViewById(R.id.tv_search);
        container = (MyViewPage) mContext.findViewById(R.id.container);
        fab01Add = mContext.findViewById(R.id.fab01Add);

        tv_zhaoshang = (TextView) mContext.findViewById(R.id.tv_zhaoshang);
        tv_jianshe = (TextView) mContext.findViewById(R.id.tv_jianshe);
        tv_shenchan = (TextView) mContext.findViewById(R.id.tv_shenchan);

        view_zhaoshang = mContext.findViewById(R.id.view_zhaoshang);
        view_jianshe = mContext.findViewById(R.id.view_jianshe);
        view_shenchan = mContext.findViewById(R.id.view_shenchuan);


        tv_zhaoshang_top = (TextView) mContext.findViewById(R.id.tv_zhaoshang_top);
        tv_jianshe_top = (TextView) mContext.findViewById(R.id.tv_jianshe_top);
        tv_shenchan_top = (TextView) mContext.findViewById(R.id.tv_shenchan_top);


        view_zhaoshang_top = mContext.findViewById(R.id.view_zhaoshang_top);
        view_jianshe_top = mContext.findViewById(R.id.view_jianshe_top);
        view_shenchan_top = mContext.findViewById(R.id.view_shenchuan_top);

        rl_zhaoshang = (RelativeLayout) mContext.findViewById(R.id.rl_zhaoshang);
        rl_jianshe = (RelativeLayout) mContext.findViewById(R.id.rl_jianshe);
        rl_shenchan = (RelativeLayout) mContext.findViewById(R.id.rl_shenchan);
        ly_menu = (LinearLayout) mContext.findViewById(R.id.ly_menu);
        rl_myproject = (RelativeLayout) mContext.findViewById(R.id.rl_myproject);
        rl_huizong = (RelativeLayout) mContext.findViewById(R.id.rl_huizong);

        rl_zhaoshang_top1 = (RelativeLayout) mContext.findViewById(R.id.rl_zhaoshang_top1);
        rl_jianshe_top = (RelativeLayout) mContext.findViewById(R.id.rl_jianshe_top);
        rl_shenchan_top = (RelativeLayout) mContext.findViewById(R.id.rl_shenchan_top);

        rl_zhaoshang_top = (LinearLayout) mContext.findViewById(R.id.rl_zhaoshang_top);



        rl_toolbar.post(new Runnable() {
            @Override
            public void run() {
                height1 = rl_toolbar.getHeight();
                im_top.post(new Runnable() {
                    @Override
                    public void run() {
                        height2 = im_top.getHeight();
                        ly_menu.post(new Runnable() {
                            @Override
                            public void run() {
                                height3 = ly_menu.getHeight();
                                mHandler.sendEmptyMessage(1000);
                            }
                        });
                    }
                });
            }
        });


        rl_zhaoshang.setOnClickListener(this);
        rl_jianshe.setOnClickListener(this);
        rl_shenchan.setOnClickListener(this);

        rl_zhaoshang_top1.setOnClickListener(this);
        rl_jianshe_top.setOnClickListener(this);
        rl_shenchan_top.setOnClickListener(this);
        rl_myproject.setOnClickListener(this);
        rl_huizong.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        rl_project_all.setOnClickListener(this);
        fab01Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectEvaluateActivity.class);
                intent.putExtra("type","0");
                startActivity(intent);
            }
        });


        rl_matter_need.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,MatterNeedActivity.class));
            }
        });

        setToolbarStatusBarAlpha(0);
        setSelect(0);

        /*sl_main.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onScrollChange(View v, int scrollX, final int scrollY, int oldScrollX, final int oldScrollY) {
                int headerHeight = banner_home.getHeight()-120;
                int scrollDistance = Math.min(scrollY, headerHeight);
                int alpha = (int) ((float) scrollDistance / (float) headerHeight * 255F);
                setToolbarStatusBarAlpha(alpha);
                int[] location = new int[2];
                ly_menu.getLocationOnScreen(location);
                int yPosition = location[1];

                if (yPosition - rl_toolbar.getHeight() <= 0) {
                    rl_zhaoshang_top.setVisibility(View.GONE);

                } else {
                    rl_zhaoshang_top.setVisibility(View.GONE);
                }

            }
        });*/
        setBannerShow();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner_home.startAutoPlay();
    }

    /**
     * 页面后台的时候停止轮播
     */
    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        banner_home.stopAutoPlay();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_zhaoshang:
                setSelect(0);
                break;
            case R.id.rl_jianshe:
                setSelect(1);
                break;
            case R.id.rl_shenchan:
                setSelect(2);
                break;
            case R.id.rl_zhaoshang_top1:
                setSelect(0);
                break;
            case R.id.rl_jianshe_top:
                setSelect(1);
                break;
            case R.id.rl_shenchan_top:
                setSelect(2);
                break;
            case R.id.rl_myproject:
                startActivity(new Intent(getActivity(), MyProjectActivity.class));
                break;
            case R.id.rl_huizong:
                Intent intent = new Intent(getActivity(), ProjectEvaluateActivity.class);
                intent.putExtra("type","1");
                startActivity(intent);
                break;
            case R.id.tv_search:
                startActivity(new Intent(mContext, SearchProjectActivity.class));
                break;
            case R.id.rl_project_all:
                startActivity(new Intent(mContext, ProjectAllActivity.class));
                break;
        }
    }


    @SuppressLint("HandlerLeak")
    private android.os.Handler mHandler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SharePreferenceManager.setHomeTopSize(height1+height2+height3);
        }
    };

    private void setToolbarStatusBarAlpha(int alpha) {
        if(alpha>0){
            rl_toolbar.getBackground().mutate().setAlpha(alpha);
            if(null != im_top.getBackground()){
                im_top.getBackground().mutate().setAlpha(alpha);
            }

            if(alpha > 155){
                tv_title.setVisibility(View.VISIBLE);
            }else{
                tv_title.setVisibility(View.GONE);
            }
        }else{
            rl_toolbar.getBackground().mutate().setAlpha(0);
            if(null!= im_top.getBackground()){
                im_top.getBackground().mutate().setAlpha(0);
            }
            tv_title.setVisibility(View.GONE);
        }
    }

    private void setSelect(int i){
        //改变内容区域，把图片设置为亮的
        switch (i) {
            case 0:
                tv_zhaoshang.setTextColor(getResources().getColor(R.color.menu_item_click_color));
                tv_jianshe.setTextColor(getResources().getColor(R.color.black1));
                tv_shenchan.setTextColor(getResources().getColor(R.color.black1));
                view_zhaoshang.setVisibility(View.VISIBLE);
                view_jianshe.setVisibility(View.GONE);
                view_shenchan.setVisibility(View.GONE);

                tv_zhaoshang_top.setTextColor(getResources().getColor(R.color.menu_item_click_color));
                tv_jianshe_top.setTextColor(getResources().getColor(R.color.black1));
                tv_shenchan_top.setTextColor(getResources().getColor(R.color.black1));
                view_zhaoshang_top.setVisibility(View.VISIBLE);
                view_jianshe_top.setVisibility(View.GONE);
                view_shenchan_top.setVisibility(View.GONE);
                isSelect = 0;
                break;
            case 1:
                tv_zhaoshang.setTextColor(getResources().getColor(R.color.black1));
                tv_jianshe.setTextColor(getResources().getColor(R.color.menu_item_click_color));
                tv_shenchan.setTextColor(getResources().getColor(R.color.black1));
                view_jianshe.setVisibility(View.VISIBLE);
                view_zhaoshang.setVisibility(View.GONE);
                view_shenchan.setVisibility(View.GONE);

                tv_zhaoshang_top.setTextColor(getResources().getColor(R.color.black1));
                tv_jianshe_top.setTextColor(getResources().getColor(R.color.menu_item_click_color));
                tv_shenchan_top.setTextColor(getResources().getColor(R.color.black1));
                view_jianshe_top.setVisibility(View.VISIBLE);
                view_zhaoshang_top.setVisibility(View.GONE);
                view_shenchan_top.setVisibility(View.GONE);
                isSelect = 1;
                break;
            case 2:
                tv_zhaoshang.setTextColor(getResources().getColor(R.color.black1));
                tv_jianshe.setTextColor(getResources().getColor(R.color.black1));
                tv_shenchan.setTextColor(getResources().getColor(R.color.menu_item_click_color));
                view_zhaoshang.setVisibility(View.GONE);
                view_jianshe.setVisibility(View.GONE);
                view_shenchan.setVisibility(View.VISIBLE);

                tv_zhaoshang_top.setTextColor(getResources().getColor(R.color.black1));
                tv_jianshe_top.setTextColor(getResources().getColor(R.color.black1));
                tv_shenchan_top.setTextColor(getResources().getColor(R.color.menu_item_click_color));
                view_zhaoshang_top.setVisibility(View.GONE);
                view_jianshe_top.setVisibility(View.GONE);
                view_shenchan_top.setVisibility(View.VISIBLE);
                isSelect = 2;
                break;
            default:
                break;
        }
        //切换Fragment
        container.setCurrentItem(i);
    }


    /**
     * 设置首页顶端Banner
     */
    private void setBannerShow(){
        RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_BANNER_LIST);
        params.addHeader("token",SharePreferenceManager.getCachedUserToken());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ResultBannerData userBannerModel = new Gson().fromJson(result, ResultBannerData.class);
                List<String> arrayList = new ArrayList<>();
                List<String> arrayTitles = new ArrayList<>();
                bannerList = new ArrayList<>();

                if(null != userBannerModel.getData()){
                    for(ResultBannerData.BannerList userBanner:userBannerModel.getData()){
                        arrayList.add(NetContants.BASE_URL_RESOURCE+ userBanner.getIcon());
                    }
                    for(ResultBannerData.BannerList userBanner:userBannerModel.getData()){
                        arrayTitles.add(userBanner.getTitle());
                    }
                    for(ResultBannerData.BannerList userBanner:userBannerModel.getData()){
                        bannerList.add(userBanner.getLink());
                    }
                }else{
                    arrayList.add("res/drawable/banner.png");
                }
                //设置banner样式
                banner_home.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                //设置图片加载器
                banner_home.setImageLoader(new GlideImageLoaders());
                //设置图片集合
                banner_home.setImages(arrayList);
                //设置banner动画效果
                banner_home.setBannerAnimation(Transformer.Default);
                //设置标题集合（当banner样式有显示title时）
                banner_home.setBannerTitles(arrayTitles);
                //设置自动轮播，默认为true
                banner_home.isAutoPlay(true);
                //设置轮播时间
                banner_home.setDelayTime(3000);
                //设置指示器位置（当banner模式中有指示器时）
                banner_home.setIndicatorGravity(BannerConfig.RIGHT);
                //设置点击事件
                banner_home.setOnBannerListener(listener);
                //banner设置方法全部调用完毕时最后调用
                banner_home.start();
                banner_home.startAutoPlay();
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

    private OnBannerListener listener=new OnBannerListener() {
        @Override
        public void OnBannerClick(int position) {
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    private void initList(){
        RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_CATEGORY_CATEGORYLIST);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                list.clear();
                list1.clear();
                list2.clear();
                ResultProjectData resultProjectData = new Gson().fromJson(result, ResultProjectData.class);
                if(resultProjectData.getResult().equals("0")){
                    int i =0;
                    for(ResultProjectData.ProjectData projectData:resultProjectData.getData()){
                        /*if(projectData.getStageId().equals("0")){
                            list.add(projectData);
                        }*/
                        if(i<=4){
                            list.add(projectData);
                            i++;
                        }

                    }

                    tab001 = new HomeAttractFragment(container,0,list);
                    for(ResultProjectData.ProjectData projectData:resultProjectData.getData()){
                        if(projectData.getStageId().equals("1")){
                            list1.add(projectData);
                        }
                    }
                    tab002 = new HomeBuildFragment(container,1,list1);
                    for(ResultProjectData.ProjectData projectData:resultProjectData.getData()){
                        if(projectData.getStageId().equals("2")){
                            list2.add(projectData);
                        }
                    }
                    tab003 = new HomeProductionFragmnet(container,2,list2);
                    mFragments.clear();
                    mFragments.add(tab001);
                    container.setOffscreenPageLimit(1);

                    mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {

                        @Override
                        public int getCount() {
                            return mFragments.size();
                        }

                        @Override
                        public Fragment getItem(int arg0) {
                            return mFragments.get(arg0);
                        }


                        @Override
                        public int getItemPosition(Object object) {
                            return -2;
                        }
                    };

                    container.setAdapter(mAdapter);
                    container.setScrollble(true);

                    setSelect(isSelect);
                    container.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                        @Override
                        public void onPageSelected(int arg0) {
                            //当前选中的Fragment 下标
                            int currentItem = container.getCurrentItem();
                            container.resetHeight(arg0);
                            //把图片全设置为暗的
                            switch (currentItem) {
                                case 0:
                                    tv_zhaoshang.setTextColor(getResources().getColor(R.color.menu_item_click_color));
                                    tv_jianshe.setTextColor(getResources().getColor(R.color.black1));
                                    tv_shenchan.setTextColor(getResources().getColor(R.color.black1));
                                    view_zhaoshang.setVisibility(View.VISIBLE);
                                    view_jianshe.setVisibility(View.GONE);
                                    view_shenchan.setVisibility(View.GONE);

                                    tv_zhaoshang_top.setTextColor(getResources().getColor(R.color.menu_item_click_color));
                                    tv_jianshe_top.setTextColor(getResources().getColor(R.color.black1));
                                    tv_shenchan_top.setTextColor(getResources().getColor(R.color.black1));
                                    view_zhaoshang_top.setVisibility(View.VISIBLE);
                                    view_jianshe_top.setVisibility(View.GONE);
                                    view_shenchan_top.setVisibility(View.GONE);
                                    break;
                                case 1:
                                    tv_zhaoshang.setTextColor(getResources().getColor(R.color.black1));
                                    tv_jianshe.setTextColor(getResources().getColor(R.color.menu_item_click_color));
                                    tv_shenchan.setTextColor(getResources().getColor(R.color.black1));
                                    view_jianshe.setVisibility(View.VISIBLE);
                                    view_zhaoshang.setVisibility(View.GONE);
                                    view_shenchan.setVisibility(View.GONE);

                                    tv_zhaoshang_top.setTextColor(getResources().getColor(R.color.black1));
                                    tv_jianshe_top.setTextColor(getResources().getColor(R.color.menu_item_click_color));
                                    tv_shenchan_top.setTextColor(getResources().getColor(R.color.black1));
                                    view_jianshe_top.setVisibility(View.VISIBLE);
                                    view_zhaoshang_top.setVisibility(View.GONE);
                                    view_shenchan_top.setVisibility(View.GONE);
                                    break;
                                case 2:
                                    tv_zhaoshang.setTextColor(getResources().getColor(R.color.black1));
                                    tv_jianshe.setTextColor(getResources().getColor(R.color.black1));
                                    tv_shenchan.setTextColor(getResources().getColor(R.color.menu_item_click_color));
                                    view_zhaoshang.setVisibility(View.GONE);
                                    view_jianshe.setVisibility(View.GONE);
                                    view_shenchan.setVisibility(View.VISIBLE);

                                    tv_zhaoshang_top.setTextColor(getResources().getColor(R.color.black1));
                                    tv_jianshe_top.setTextColor(getResources().getColor(R.color.black1));
                                    tv_shenchan_top.setTextColor(getResources().getColor(R.color.menu_item_click_color));
                                    view_zhaoshang_top.setVisibility(View.GONE);
                                    view_jianshe_top.setVisibility(View.GONE);
                                    view_shenchan_top.setVisibility(View.VISIBLE);
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
                    container.resetHeight(0);
                    mAdapter.notifyDataSetChanged();

                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
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
