package pdkj.zhaoshang.activity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yyydjk.library.DropDownMenu;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.adapter.GirdDropDownAdapter;
import pdkj.zhaoshang.database.CategoryTpyeEntry;
import pdkj.zhaoshang.database.DataDaoConfig;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultProjectData;
import pdkj.zhaoshang.utils.BaseUtil;
import pdkj.zhaoshang.utils.ItemProject;
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
public class ProjectAllActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout ly_content;
    private RelativeLayout rl_head_return;
    private DropDownMenu mDropDownMenu;
    private DropDownMenu mDropDownMenu1;
    private String chanYeID;
    private List<CategoryTpyeEntry> findAll = new ArrayList<>();
    private List<CategoryTpyeEntry> findAll1 = new ArrayList<>();

    private String headers[] = {"项目阶段", "项目类型","启动时间"};
    private String headers1[] = {"产业类别","行业类别"};
    private String citys[] = {"所有阶段", "招商阶段", "建设阶段", "运营阶段"};
    private String cityStr[] = {"0", "1","2"};
    private String ages[] = {"所有类型","土建项目", "非土建项目"};
    private String agesStr[] = {"1","2"};
    private String sexs[] = {"所有年份","2018~2019","2017~2018", "2016~2017", "2015~2016", "2014~2015", "2013~2014", "2012~2013", "2011~2012", "2010~2011"
            , "2009~2010", "2008~2009", "2007~2008", "2006~2007", "2005~2006", "2004~2005", "2003~2004"};
    private List<String> listChanye = new ArrayList<>();
    private List<String> listHangye  = new ArrayList<>();

    private String type;
    private String startDate;
    private String endDate;
    private String stageId;
    private String chanye;
    private String hangye;
    private GirdDropDownAdapter cityAdapter;
    private GirdDropDownAdapter cityAdapter1;
    private GirdDropDownAdapter cityAdapter2;
    private GirdDropDownAdapter cityAdapter3;
    private GirdDropDownAdapter cityAdapter4;
    private List<View> popupViews = new ArrayList<>();
    private List<View> popupViews1 = new ArrayList<>();
    private DbManager db = x.getDb(DataDaoConfig.getDaoConfig());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_all);
        initView();
        initData();

    }

    private void initView(){
        ly_content = (LinearLayout) findViewById(R.id.ly_content);
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        mDropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);
        mDropDownMenu1 = (DropDownMenu) findViewById(R.id.dropDownMenu1);
    }

    private void initData(){
        rl_head_return.setOnClickListener(this);
        //init city menu
        final ListView cityView = new ListView(this);
        cityAdapter = new GirdDropDownAdapter(this, Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        final ListView cityView1 = new ListView(this);
        cityAdapter1 = new GirdDropDownAdapter(this, Arrays.asList(ages));
        cityView1.setDividerHeight(0);
        cityView1.setAdapter(cityAdapter1);

        final ListView cityView2 = new ListView(this);
        cityAdapter2 = new GirdDropDownAdapter(this, Arrays.asList(sexs));
        cityView2.setDividerHeight(0);
        cityView2.setAdapter(cityAdapter2);

        final ListView chanyeView = new ListView(this);
        try {
            findAll = db.selector(CategoryTpyeEntry.class).expr("model = 0").findAll();
            for(CategoryTpyeEntry categoryTpyeEntry:findAll){
                listChanye.add(categoryTpyeEntry.getName());
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        cityAdapter3 = new GirdDropDownAdapter(this, listChanye);
        chanyeView.setDividerHeight(0);
        chanyeView.setAdapter(cityAdapter3);

        chanYeID = findAll.get(0).getId()+"";

        final ListView hangyeView = new ListView(this);
        try {
            findAll1 = db.selector(CategoryTpyeEntry.class).expr(" parentId = " + chanYeID).and(WhereBuilder.b("model", "=", 1)).findAll();
            listHangye.clear();
            for(CategoryTpyeEntry categoryTpyeEntry:findAll1){
                listHangye.add(categoryTpyeEntry.getName());
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        cityAdapter4 = new GirdDropDownAdapter(this, listHangye);
        hangyeView.setDividerHeight(0);
        hangyeView.setAdapter(cityAdapter4);

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(cityView1);
        popupViews.add(cityView2);
        popupViews1.add(chanyeView);
        popupViews1.add(hangyeView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
                if(position == 0){
                    stageId = "";
                }else{
                    stageId = cityStr[position-1];
                }
                setData();
            }
        });

        //add item click event
        cityView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter1.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                mDropDownMenu.closeMenu();
                if(position == 0){
                    type = "";
                }else{
                    type = agesStr[position - 1];
                }
                setData();
            }
        });

        //add item click event
        cityView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter2.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
                mDropDownMenu.closeMenu();
                if(position == 0){
                    startDate = "";
                    endDate = "";
                }else{
                    String[] sourceStrArray = sexs[position].split("~");
                    startDate = sourceStrArray[0];
                    endDate = sourceStrArray[1];
                }
                setData();
            }
        });

        //add item click event
        chanyeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter2.setCheckItem(position);
                mDropDownMenu1.setTabText(position == 0 ? headers[3] : listChanye.get(position-1));
                mDropDownMenu1.closeMenu();
                chanYeID = findAll.get(position).getId()+"";

                try {
                    findAll1 = db.selector(CategoryTpyeEntry.class).expr(" parentId = " + chanYeID).and(WhereBuilder.b("model", "=", 1)).findAll();
                    listHangye.clear();
                    for(CategoryTpyeEntry categoryTpyeEntry:findAll1){
                        listHangye.add(categoryTpyeEntry.getName());
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                setData();
            }
        });

        //add item click event
        hangyeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter2.setCheckItem(position);
                mDropDownMenu1.setTabText(position == 0 ? headers[4] : listHangye.get(position));
                mDropDownMenu1.closeMenu();
                hangye = findAll1.get(position).getId()+"";
                setData();
            }
        });

        //init context view
        LinearLayout contentView = new LinearLayout(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //init context view
        LinearLayout contentView1 = new LinearLayout(this);
        contentView1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
        mDropDownMenu1.setDropDownMenu(Arrays.asList(headers1), popupViews1, contentView1);
        setData();
    }


    private void setData(){
        RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_CATEGORY_CATEGORYLIST);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        params.addBodyParameter("type",type);
        params.addBodyParameter("startDate",startDate);
        params.addBodyParameter("endDate",endDate);
        params.addBodyParameter("stageId",stageId);
        params.addBodyParameter("trade",hangye);
        params.addBodyParameter("industry",chanYeID);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                List<ResultProjectData.ProjectData> list = new ArrayList<>();
                ResultProjectData resultProjectData = new Gson().fromJson(result, ResultProjectData.class);
                list = resultProjectData.getData();
                if(!BaseUtil.isSpace(list)){
                    ly_content.removeAllViews();
                    for(ResultProjectData.ProjectData projectData:list){
                        if(BaseUtil.isSpace(stageId)){
                            ly_content.addView(new ItemProject(mContext,projectData));
                        }else{
                            if(projectData.getStageId().equals(stageId)){
                                ly_content.addView(new ItemProject(mContext,projectData));
                            }
                        }
                    }
                }else{
                    ToastUtil.shortToast(mContext,"该条件下没有查询到结果");
                    ly_content.removeAllViews();
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
}
