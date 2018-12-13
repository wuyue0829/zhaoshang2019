package pdkj.zhaoshang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.necer.ncalendar.calendar.NCalendar;
import com.necer.ncalendar.listener.OnCalendarChangedListener;
import com.necer.ncalendar.utils.MyLog;

import org.joda.time.DateTime;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.adapter.MatterNeedAdapter;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultListStrData;
import pdkj.zhaoshang.entity.ResultMatterListData;
import pdkj.zhaoshang.utils.DateUtil;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.photovideo.takevideo.utils.LogUtils;

/**
 * 创建时间： 2018/1/31
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class MatterNeedActivity extends BaseActivity implements OnCalendarChangedListener {

    private NCalendar ncalendar;
    private RecyclerView recyclerView;
    private TextView tv_month;
    private TextView tv_date;
    private RelativeLayout rl_head_return;
    private RelativeLayout rl_submit;
    private List<ResultMatterListData.Matter.MatterEntye> list = new ArrayList<>();
    private MatterNeedAdapter aaAdapter = null;
    private MatterNeedAdapter aaAdapter1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matter_need);
        this.setSwipeBackEnable(false);
        initView();

    }


    private void initView(){
        ncalendar = (NCalendar) findViewById(R.id.ncalendarrrr);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        rl_submit = (RelativeLayout) findViewById(R.id.rl_submit);
        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_date = (TextView) findViewById(R.id.tv_date);
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        rl_head_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,AddMatterNeedActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //查询今天的List

        ncalendar.setOnCalendarChangedListener(this);

        final List<String> list = new ArrayList<>();
        RequestParams params1 = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_BACKLOG_LIST4DATE);
        params1.addHeader("token", SharePreferenceManager.getCachedUserToken());
        x.http().get(params1, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ResultListStrData resultListStrData = new Gson().fromJson(result, ResultListStrData.class);
                for(String str:resultListStrData.getData()){
                    list.add(DateUtil.getFormatDate("yyyy-MM-dd",DateUtil.getDate("yyyy/MM/dd HH:mm",str)));
                }
                ncalendar.post(new Runnable() {
                    @Override
                    public void run() {
                        ncalendar.setPoint(list);
                    }
                });
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
    public void onCalendarChanged(DateTime dateTime) {
        tv_month.setText(dateTime.getMonthOfYear() + "月");
        tv_date.setText(dateTime.getYear() + "年" + dateTime.getMonthOfYear() + "月" + dateTime.getDayOfMonth() + "日");

        RequestParams params = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_BACKLOG_LIST4PAGE);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        params.addBodyParameter("pageNum","1");
        params.addBodyParameter("pageSize","50");
        params.addBodyParameter("time",DateUtil.getFormatDate("yyyy/MM/dd",DateUtil.getDate("yyyy/MM/dd",dateTime.getYear() + "/" + dateTime.getMonthOfYear() + "/" + dateTime.getDayOfMonth())));
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                list = new Gson().fromJson(result,ResultMatterListData.class).getData().getPageData();
                if(null != list&&list.size()>0){
                    LogUtils.e("内容是"+list.size());
                    recyclerView.removeAllViews();
                    aaAdapter = new MatterNeedAdapter(mContext,list);
                    recyclerView.setAdapter(aaAdapter);
                    aaAdapter.notifyDataSetChanged();
                }else{
                    aaAdapter1 = new MatterNeedAdapter(mContext);
                    recyclerView.setAdapter(aaAdapter1);
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

        MyLog.d("dateTime::" + dateTime);
    }

    public void setDate(View view) {
        ncalendar.setDate("2018-1-31");
    }

    public void toMonth(View view) {
        ncalendar.toMonth();
    }

    public void toWeek(View view) {
        ncalendar.toWeek();
    }

    public void toToday(View view) {
        ncalendar.toToday();
    }

    public void toNextPager(View view) {
        ncalendar.toNextPager();
    }

    public void toLastPager(View view) {
        ncalendar.toLastPager();
    }


}
