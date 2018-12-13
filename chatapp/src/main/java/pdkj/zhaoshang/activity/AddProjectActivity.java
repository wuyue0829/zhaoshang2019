package pdkj.zhaoshang.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.jaygoo.selector.MultiSelectPopWindow;
import com.necer.ndialog.NDialog;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import pdkj.zhaoshang.R;
import pdkj.zhaoshang.database.CategoryTpyeEntry;
import pdkj.zhaoshang.database.DataDaoConfig;
import pdkj.zhaoshang.database.FriendEntry;
import pdkj.zhaoshang.database.UserEntry;
import pdkj.zhaoshang.entity.Category;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultCategoryData;
import pdkj.zhaoshang.entity.ResultNodeData;
import pdkj.zhaoshang.utils.BaseUtil;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.StatusBarUtil;
import pdkj.zhaoshang.utils.ToastUtil;

/**
 * 创建时间： 2018/1/31
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class AddProjectActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout rl_head_return;
    private RelativeLayout rl_leixing;
    private RelativeLayout rl_chanye;
    private RelativeLayout rl_hangye;
    private RelativeLayout rl_fuzeren;
    private RelativeLayout rl_time;
    private RelativeLayout rl_chenyuan;
    private RelativeLayout rl_jiedian;
    private RelativeLayout rl_submit;
    private RelativeLayout rl_quanxian;
    private RelativeLayout rl_touzileixing;


    private TextView tv_leixing;
    private TextView tv_touzileixing;
    private TextView tv_chanye;
    private TextView tv_hangye;
    private TextView tv_fuzeren;
    private TextView tv_time;
    private TextView tv_jiedian;
    private TextView tv_chenyuan;
    private TextView tv_quanxian;
    private EditText ed_title;
    private EditText ed_fuwuduixiang;
    private EditText ed_mianji;
    private EditText ed_touzie;
    private List<String> xinghaoList = new ArrayList<>();
    private List<String> jieduanList = new ArrayList<>();
    private List<String> touziList = new ArrayList<>();
    private List<String> chanyeList = new ArrayList<>();
    private List<String> hangyeList = new ArrayList<>();
    private List<CategoryTpyeEntry> findAll = new ArrayList<>();
    private List<CategoryTpyeEntry> findAll2 = new ArrayList<>();
    private List<FriendEntry> findAll3 = new ArrayList<>();
    private ArrayList<String> findAll4 = new ArrayList<>();
    private ArrayList<String> findAll5 = new ArrayList<>();
    private ArrayList<String> findAll6 = new ArrayList<>();

    private String chenyuan = "";
    private String chenyuanquanxian = "";
    private String jiedian = "";
    private EditText ed_beizhu;


    private List<String> chenyuanUnchoose = new ArrayList<>();
    private List<String> jiedianUnchoose  = new ArrayList<>();
    private List<String> quanxianUnchoose  = new ArrayList<>();

    private List<ResultNodeData.NodeData> nodeList = new ArrayList<>();
    private int chanYeID = 0;
    private int hangyeID = 0;
    private String fuzerenID = "";
    private String stageId = "";
    private String touzileixing = "";
    private DbManager db = x.getDb(DataDaoConfig.getDaoConfig());

    private String xiangmuleixing;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        this.setSwipeBackEnable(false);

        //设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            StatusBarUtil.setStatusBarTranslucent(this,true);
        }
        initView();
    }

    private void initView(){
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        rl_leixing = (RelativeLayout) findViewById(R.id.rl_leixing);
        rl_chanye = (RelativeLayout) findViewById(R.id.rl_chanye);
        rl_hangye = (RelativeLayout) findViewById(R.id.rl_hangye);
        rl_fuzeren = (RelativeLayout) findViewById(R.id.rl_fuzeren);
        rl_time = (RelativeLayout) findViewById(R.id.rl_time);
        rl_jiedian = (RelativeLayout) findViewById(R.id.rl_jiedian);
        rl_chenyuan = (RelativeLayout) findViewById(R.id.rl_chenyuan);
        rl_submit = (RelativeLayout) findViewById(R.id.rl_submit);
        tv_quanxian = (TextView) findViewById(R.id.tv_quanxian);
        rl_quanxian = (RelativeLayout) findViewById(R.id.rl_quanxian);
        rl_touzileixing = (RelativeLayout) findViewById(R.id.rl_touzileixing);
        tv_leixing = (TextView) findViewById(R.id.tv_leixing);
        tv_chanye = (TextView) findViewById(R.id.tv_chanye);
        tv_touzileixing = (TextView) findViewById(R.id.tv_touzileixing);
        tv_jiedian = (TextView) findViewById(R.id.tv_jiedian);
        tv_hangye = (TextView) findViewById(R.id.tv_hangye);
        tv_fuzeren = (TextView) findViewById(R.id.tv_fuzeren);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_chenyuan = (TextView) findViewById(R.id.tv_chenyuan);
        ed_title = (EditText) findViewById(R.id.ed_title);
        ed_beizhu = (EditText) findViewById(R.id.ed_beizhu);
        ed_fuwuduixiang = (EditText) findViewById(R.id.ed_fuwuduixiang);
        ed_mianji = (EditText) findViewById(R.id.ed_mianji);
        ed_touzie = (EditText) findViewById(R.id.ed_touzie);

        xinghaoList.add("土地建设项目");
        xinghaoList.add("非土地建设项目");


        try {
            findAll = db.selector(CategoryTpyeEntry.class).expr("model = 0").findAll();
            for(CategoryTpyeEntry categoryTpyeEntry:findAll){
                chanyeList.add(categoryTpyeEntry.getName());
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        UserEntry user = UserEntry.getUser(JMessageClient.getMyInfo().getUserName(),
                JMessageClient.getMyInfo().getAppKey());
        findAll3 = user.getFriends();
        FriendEntry friendEntry = new FriendEntry();
        friendEntry.username = JMessageClient.getMyInfo().getUserName();
        friendEntry.nickName = JMessageClient.getMyInfo().getNickname();
        findAll3.add(friendEntry);
        for(int i = 0;i< findAll3.size();i++){
            findAll4.add(findAll3.get(i).nickName);
            chenyuanUnchoose.add(findAll3.get(i).username);
        }

        rl_head_return.setOnClickListener(this);
        rl_leixing.setOnClickListener(this);
        rl_chanye.setOnClickListener(this);
        rl_hangye.setOnClickListener(this);
        rl_fuzeren.setOnClickListener(this);
        rl_time.setOnClickListener(this);
        rl_chenyuan.setOnClickListener(this);
        rl_jiedian.setOnClickListener(this);
        rl_submit.setOnClickListener(this);
        rl_quanxian.setOnClickListener(this);
        rl_touzileixing.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_head_return:
                if(!isWriteContent()){
                    finish();
                    hintKbOne();
                }
                break;
            case R.id.rl_leixing:
                xiangMuLeiXingDialog(xinghaoList);
                break;
            case R.id.rl_chanye:
                xiangMuChanYeDialog(chanyeList);
                break;
            case R.id.rl_hangye:
                if(chanYeID == 0){
                    ToastUtil.shortToast(mContext,"请先选择项目产业");
                }else{
                    try {
                        findAll2 = db.selector(CategoryTpyeEntry.class).expr(" parentId = " + chanYeID).and(WhereBuilder.b("model", "=", 1)).findAll();
                        hangyeList.clear();
                        for(CategoryTpyeEntry categoryTpyeEntry:findAll2){
                            hangyeList.add(categoryTpyeEntry.getName());
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    xiangMuHangYeDialog(hangyeList);
                }
                break;

            case R.id.rl_fuzeren:
                xiangMuFuZeRenDialog(findAll4);
                break;

            case R.id.rl_time:
                timeChooseDialog();
                break;
            case R.id.rl_chenyuan:
                dialogMoreChoice();
                break;
            case R.id.rl_jiedian:
                if(BaseUtil.isSpace(tv_leixing.getText().toString().trim())){
                    ToastUtil.shortToast(mContext,"请先选择项目类型！");
                }else{
                    getNode();
                }
                break;

            case R.id.rl_submit:
                submit();
                break;
            case R.id.rl_quanxian:
                if(BaseUtil.isSpace(tv_chenyuan.getText().toString().trim())){
                    ToastUtil.shortToast(mContext,"请先选择成员！");
                }else{
                    dialogQuanxianChoice();
                }
                break;
            case R.id.rl_touzileixing:
                touzileixingDialog();
                break;

        }
    }


    private boolean isWriteContent(){
        return false;
    }


    /**
     * 类型下拉显示
     */
    public void xiangMuLeiXingDialog(List<String> list){
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
                        tv_leixing.setText(item);
                        xiangmuleixing = (which+1)+"";
                    }
                }).create(NDialog.CHOICE).show();
    }


    /**
     * 行业下拉显示
     */
    public void xiangMuChanYeDialog(List<String> list){
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
                        tv_chanye.setText(item);
                        tv_hangye.setText("");
                        chanYeID = findAll.get(which).getId();
                    }
                }).create(NDialog.CHOICE).show();
    }

    /**
     * 产业下拉显示
     */
    public void xiangMuHangYeDialog(List<String> list){
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
                        tv_hangye.setText(item);
                        hangyeID = findAll2.get(which).getId();
                    }
                }).create(NDialog.CHOICE).show();
    }


    /**
     * 负责人下拉显示
     */
    public void xiangMuFuZeRenDialog(List<String> list){
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
                        tv_fuzeren.setText(item);
                        fuzerenID = findAll3.get(which).username;
                    }
                }).create(NDialog.CHOICE).show();
    }


    public void timeChooseDialog(){
        TimePickerView timePickerView = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(final Date date, View v) {

                tv_time.setText(getDataTime(date));
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setCancelText("取消")
                .setSubmitText("确定")
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setOutSideCancelable(true)
                .isCyclic(true)
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setSubmitColor(Color.GRAY)//确定按钮文字颜色
                .setCancelColor(Color.GRAY)//取消按钮文字颜色
                .isCenterLabel(false)
                .build();
        timePickerView.show();
    }


    public String getDataTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    /**
     * 多选
     */
    private void dialogMoreChoice() {

        new MultiSelectPopWindow.Builder(this)
                .setNameArray(findAll4)
                .setConfirmListener(new MultiSelectPopWindow.OnConfirmClickListener() {
                    @Override
                    public void onClick(ArrayList<Integer> indexList, ArrayList<String> selectedList) {
                        String content = "";
                        for(String str:selectedList){
                            content = content + str + ",";
                        }
                        findAll6 = selectedList;
                        tv_chenyuan.setText(content.substring(0,content.length()-1));

                        for(Integer integer:indexList){
                            chenyuan = chenyuan + (chenyuanUnchoose.get(integer)+",");
                            quanxianUnchoose.add(chenyuanUnchoose.get(integer));
                        }
                        chenyuan = chenyuan.substring(0,chenyuan.length()-1);

                    }
                })
                .setCancel("取消")
                .setConfirm("完成")
                .setTitle("选择成员")
                .build()
                .show(findViewById(R.id.mBottom));
    }


    /**
     * 多选
     */
    private void dialogQuanxianChoice() {

        new MultiSelectPopWindow.Builder(this)
                .setNameArray(findAll6)
                .setConfirmListener(new MultiSelectPopWindow.OnConfirmClickListener() {
                    @Override
                    public void onClick(ArrayList<Integer> indexList, ArrayList<String> selectedList) {
                        String content = "";
                        for(String str:selectedList){
                            content = content + str + ",";
                        }
                        tv_quanxian.setText(content.substring(0,content.length()-1));

                        for(Integer integer:indexList){
                            chenyuanquanxian = chenyuanquanxian + (quanxianUnchoose.get(integer)+",");
                        }
                        chenyuanquanxian = chenyuanquanxian.substring(0,chenyuanquanxian.length()-1);

                    }
                })
                .setCancel("取消")
                .setConfirm("完成")
                .setTitle("权限分配")
                .build()
                .show(findViewById(R.id.mBottom));
    }


    /**
     * 负责人下拉显示
     */
    public void jieduanDialog(List<String> list){
        touziList = new ArrayList<>();
        touziList.add("招商阶段");
        touziList.add("建设阶段");
        touziList.add("运营阶段");

        new NDialog(mContext)
                .setItems(touziList.toArray(new String[touziList.size()]))
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
                        tv_jiedian.setText(item);
                        stageId = which+"";
                    }
                }).create(NDialog.CHOICE).show();
    }



    /**
     * 负责人下拉显示
     */
    public void touzileixingDialog(){
        jieduanList = new ArrayList<>();
        jieduanList.add("内资");
        jieduanList.add("外资");

        if(tv_leixing.getText().toString().trim().equals("非土地建设项目")){
            jieduanList.remove(1);
        }

        new NDialog(mContext)
                .setItems(jieduanList.toArray(new String[jieduanList.size()]))
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
                        tv_touzileixing.setText(item);
                        touzileixing = which+ "";
                    }
                }).create(NDialog.CHOICE).show();
    }

    /**
     * 多选
     */
    private void dialogMoreJieDian() {
        new MultiSelectPopWindow.Builder(this)
                .setNameArray(findAll5)
                .setConfirmListener(new MultiSelectPopWindow.OnConfirmClickListener() {
                    @Override
                    public void onClick(ArrayList<Integer> indexList, ArrayList<String> selectedList) {
                        //do something
                        String content = "";
                        for(String str:selectedList){
                            content = content + str + "-";
                        }
                        tv_jiedian.setText("已选择");
                        for(Integer integer:indexList){
                            jiedian = jiedian + (jiedianUnchoose.get(integer)+",");
                        }
                        jiedian = jiedian.substring(0,jiedian.length()-1);
                    }
                })
                .setCancel("取消")
                .setConfirm("完成")
                .setTitle("选择节点")
                .build()
                .show(findViewById(R.id.mBottom));
    }


    private void getNode(){
        /*RequestParams params;
        if(tv_leixing.getText().toString().trim().equals("非土地建设项目")){
            params = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_STAGE_LIST2SELECT + "1");
        }else{
            params = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_STAGE_LIST2SELECT + "1");
        }

        params.addHeader("token", SharePreferenceManager.getCachedUserToken());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                nodeList = new Gson().fromJson(result, ResultNodeData.class).getData();
                for(ResultNodeData.NodeData nodeData:nodeList){
                    findAll5.add(nodeData.getTitle());
                    jiedianUnchoose.add(nodeData.getId());
                }
                dialogMoreJieDian();
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
        });*/
        jieduanDialog(jieduanList);
    }


    private void submit(){
        if(checkInput()){
            Category category = new Category();
            category.setTitle(ed_title.getText().toString().trim());
            category.setType(xiangmuleixing);
            category.setIndustry(chanYeID+"");
            category.setTrade(hangyeID+"");
            category.setCustomer(ed_fuwuduixiang.getText().toString().trim());
            category.setHeader(fuzerenID);
            category.setMembers(chenyuan);
            category.setAuthUsers(chenyuanquanxian);
            category.setCurrentStage(stageId);
            category.setStartDate(tv_time.getText().toString().trim());
            category.setSummary(ed_beizhu.getText().toString().trim());
            category.setArea(ed_mianji.getText().toString().trim());
            category.setAmount(ed_touzie.getText().toString().trim());
            category.setInvestType(touzileixing);
            RequestParams params = new RequestParams(NetContants.BASE_URL + NetContants.URL_CATEGORY_ADD);
            params.setMultipart(false);
            params.setAsJsonContent(true);
            params.addHeader("token", SharePreferenceManager.getCachedUserToken());
            params.addBodyParameter("category",new Gson().toJson(category),"application/json");
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    ToastUtil.shortToast(mContext,"项目添加成功！");
                    //跳转到项目详情列表
                    Intent intent = new Intent(mContext, ProjectDetailActivity.class);
                    if(new Gson().fromJson(result, ResultCategoryData.class).getData().getType().equals("1")){
                        intent.putExtra("type","1");
                    }else{
                        intent.putExtra("type","2");
                    }
                    intent.putExtra("title",new Gson().fromJson(result, ResultCategoryData.class).getData().getTitle());
                    intent.putExtra("categoryId",new Gson().fromJson(result, ResultCategoryData.class).getData().getId()+"");
                    mContext.startActivity(intent);
                    finish();
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

    private boolean checkInput(){
        if(TextUtils.isEmpty(ed_title.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请填写项目名称");
            return false;
        }

        if(TextUtils.isEmpty(tv_leixing.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请选择项目类型");
            return false;
        }

        if(TextUtils.isEmpty(tv_chanye.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请选择项目产业");
            return false;
        }

        if(TextUtils.isEmpty(tv_hangye.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请选择项目行业");
            return false;
        }

        if(TextUtils.isEmpty(ed_fuwuduixiang.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请填写服务对象");
            return false;
        }

        if(TextUtils.isEmpty(tv_fuzeren.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请选择项目负责人");
            return false;
        }

        if(TextUtils.isEmpty(tv_chenyuan.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请选择项目成员");
            return false;
        }

        if(TextUtils.isEmpty(tv_jiedian.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请勾选项目所处阶段");
            return false;
        }

        if(TextUtils.isEmpty(tv_time.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请选择项目启动时间");
            return false;
        }
        if(TextUtils.isEmpty(ed_mianji.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请填写占地面积");
            return false;
        }
        if(TextUtils.isEmpty(ed_touzie.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请填写拟投资额");
            return false;
        }

        if(TextUtils.isEmpty(tv_touzileixing.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请选择项目投资类型");
            return false;
        }
     return true;
    }
}
