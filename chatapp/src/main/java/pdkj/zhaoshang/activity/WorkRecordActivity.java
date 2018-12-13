package pdkj.zhaoshang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultEventData;
import pdkj.zhaoshang.entity.ResultRecordData;
import pdkj.zhaoshang.utils.BaseUtil;
import pdkj.zhaoshang.utils.DateUtil;
import pdkj.zhaoshang.utils.Item_Record;
import pdkj.zhaoshang.utils.SharePreferenceManager;

/**
 * 创建时间： 2018/2/12
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class WorkRecordActivity extends BaseActivity implements View.OnClickListener{

    private RelativeLayout rl_head_return;
    private LinearLayout ly_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_record);
        setSwipeBackEnable(false);
        initView();
        initData();
    }

    private void initView(){
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        ly_content = (LinearLayout) findViewById(R.id.ly_content);
    }

    private void initData(){
        rl_head_return.setOnClickListener(this);

        //获取节点数据
        RequestParams params = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_LOG_LIST4PAGE);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ResultRecordData resultRecordData = new Gson().fromJson(result, ResultRecordData.class);
                if(!BaseUtil.isSpace(resultRecordData.getData())){
                    for(ResultRecordData.Record record:resultRecordData.getData()){
                        ly_content.addView(new Item_Record(mContext,record.getOperationContent(), DateUtil.getFormatDate("yyyy年MM月dd日",DateUtil.getDate("yyyy-MM-dd HH:mm:ss",record.getAddTime())),record.getModel()));
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_head_return:
                finish();
                break;
        }
    }

}
