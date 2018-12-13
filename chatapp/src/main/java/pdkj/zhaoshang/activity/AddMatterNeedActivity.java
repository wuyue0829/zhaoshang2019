package pdkj.zhaoshang.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.necer.ndialog.NDialog;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultNullData;
import pdkj.zhaoshang.entity.ResultProjectNameData;
import pdkj.zhaoshang.utils.DateUtil;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.ToastUtil;

import static pdkj.zhaoshang.application.JGApplication.context;

/**
 * 创建时间： 2018/2/7
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class AddMatterNeedActivity extends BaseActivity implements View.OnClickListener{


    private EditText ed_title;
    private EditText ed_content;
    private TextView tv_jiedian;
    private TextView tv_time;
    private RelativeLayout rl_time;
    private RelativeLayout rl_jiedian;
    private RelativeLayout rl_submit;
    private RelativeLayout rl_head_return;
    private RelativeLayout moren;
    private List<ResultProjectNameData.DataMessage> nameList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private List<String> idList = new ArrayList<>();
    private List<String> moRenList = new ArrayList<>();
    private String chanYeID;
    private Date selectDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wait);
        initView();
        initData();
    }

    private void initView(){
        ed_title = (EditText) findViewById(R.id.ed_title);
        ed_content = (EditText) findViewById(R.id.ed_content);
        rl_time = (RelativeLayout) findViewById(R.id.rl_time);
        rl_jiedian = (RelativeLayout) findViewById(R.id.rl_jiedian);
        rl_submit = (RelativeLayout) findViewById(R.id.rl_submit);
        moren = (RelativeLayout) findViewById(R.id.moren);
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        tv_jiedian = (TextView) findViewById(R.id.tv_jiedian);
        tv_time = (TextView) findViewById(R.id.tv_time);
        getProjectName();
        rl_time.setOnClickListener(this);
        rl_jiedian.setOnClickListener(this);
        rl_submit.setOnClickListener(this);
        rl_head_return.setOnClickListener(this);
        moren.setOnClickListener(this);
    }


    private void initData(){
        moRenList.add("电话");
        moRenList.add("邮件");
        moRenList.add("会见");
        moRenList.add("拜访");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_time:
                timeChooseDialog();
                break;
            case R.id.rl_jiedian:
                xiangMuChanYeDialog(titleList);
                break;
            case R.id.rl_submit:
                if(checkInput()){
                    submit();
                }
                break;
            case R.id.rl_head_return:
                finish();
                break;
            case R.id.moren:
                moRenDialog(moRenList);
                break;
        }
    }


    private void getProjectName(){

        RequestParams params = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_CATEGORY_LIST);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                nameList = new Gson().fromJson(result, ResultProjectNameData.class).getData();
                for(ResultProjectNameData.DataMessage nodeData:nameList){
                    titleList.add(nodeData.getTitle());
                    idList.add(nodeData.getId());
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

    /**
     * 项目名称下拉显示
     */
    public void xiangMuChanYeDialog(List<String> list){
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ed_title.getWindowToken(), 0);
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
                        tv_jiedian.setText(item);
                        chanYeID = idList.get(which);
                    }
                }).create(NDialog.CHOICE).show();
    }



    /**
     * 项目名称下拉显示
     */
    public void moRenDialog(List<String> list){
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ed_title.getWindowToken(), 0);
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
                        ed_title.setText(item);
                    }
                }).create(NDialog.CHOICE).show();
    }


    public void timeChooseDialog(){
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ed_title.getWindowToken(), 0);

        TimePickerView timePickerView = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(final Date date, View v) {
                tv_time.setText(getDataTime(date));
                selectDate = date;
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH点mm分");
        return format.format(date);
    }


    private boolean checkInput(){
        if(TextUtils.isEmpty(ed_title.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请填写待办事项名称");
            return false;
        }
        if(TextUtils.isEmpty(tv_time.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请选择待办事项时间");
            return false;
        }
        if(TextUtils.isEmpty(tv_jiedian.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请选择待办事项关联项目");
            return false;
        }
        if(TextUtils.isEmpty(ed_content.getText().toString().trim())){
            ToastUtil.shortToast(mContext,"请填写待办事项备注");
            return false;
        }
        return true;
    }

    public void submit(){
        RequestParams params = new RequestParams(NetContants.BASE_URL + NetContants.URL_BACKLOG_ADD);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        params.addBodyParameter("plannedTime",DateUtil.getFormatDate("yyyy/MM/dd HH:mm",selectDate));
        params.addBodyParameter("itemName",ed_title.getText().toString().trim());
        params.addBodyParameter("categoryName",tv_jiedian.getText().toString().trim());
        params.addBodyParameter("itemContent",ed_content.getText().toString().trim());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ResultNullData resultNullData = new Gson().fromJson(result, ResultNullData.class);
                if(resultNullData.getResult().equals("0")){
                    ToastUtil.shortToast(mContext,"待办事件添加成功！");
                    finish();
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
