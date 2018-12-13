package pdkj.zhaoshang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rmondjone.locktableview.LockTableView;
import com.rmondjone.xrecyclerview.ProgressStyle;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultConlusionSrandard;
import pdkj.zhaoshang.entity.ResultEvaluateData;
import pdkj.zhaoshang.utils.DoNumberUtil;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.view.RadarData;
import pdkj.zhaoshang.view.RadarView;

/**
 * 创建时间： 2018/2/11
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class EvaluteReportActivity extends BaseActivity{

    private String result;
    private ResultEvaluateData.EvaluateEnty evaluateEnty;
    private TextView tv_title;
    private RadarView radarView1;
    private RadarView radarView;
    private TextView tv_score;
    private LinearLayout ly_biao1;
    private LinearLayout ly_biao2;
    private RelativeLayout rl_head_return;
    private ResultConlusionSrandard.ConlusionSrandard conlusionSrandard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evalute_report);
        setSwipeBackEnable(false);
        initView();
        initData();
    }


    private void initView(){
        tv_title = (TextView) findViewById(R.id.tv_title);
        radarView1 = (RadarView) findViewById(R.id.radarView1);
        radarView = (RadarView) findViewById(R.id.radarView);
        tv_score = (TextView) findViewById(R.id.tv_score);
        ly_biao1 = (LinearLayout) findViewById(R.id.ly_biao1);
        ly_biao2 = (LinearLayout) findViewById(R.id.ly_biao2);
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        rl_head_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initData(){

        result = getIntent().getExtras().getString("result");
        evaluateEnty = new Gson().fromJson(result,ResultEvaluateData.class).getData();
        if(evaluateEnty.getRank().equals("1")){
            tv_title.setText("此项目为全力争取项目");
        }else if(evaluateEnty.getRank().equals("2")){
            tv_title.setText("此项目为重点项目");
        }else if(evaluateEnty.getRank().equals("3")){
            tv_title.setText("此项目为基本符合项目");
        }else if(evaluateEnty.getRank().equals("4")){
            tv_title.setText("此项目为需要改进项目");
        }else if(evaluateEnty.getRank().equals("5")){
            tv_title.setText("此项目为绝对禁止项目");
        }

        BigDecimal bg = new BigDecimal(DoNumberUtil.dblNullDowith(evaluateEnty.getScore()));
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        tv_score.setText(f1+"");

        List<RadarData> dataList1 = new ArrayList<>();
        RadarData data = new RadarData("单位面积能耗", DoNumberUtil.dblNullDowith(evaluateEnty.getEnergyLand()));
        RadarData data1 = new RadarData("单位面积就业", DoNumberUtil.dblNullDowith(evaluateEnty.getJobLand()));
        RadarData data2 = new RadarData("单位面积利润", DoNumberUtil.dblNullDowith(evaluateEnty.getProfitLand()));
        RadarData data3 = new RadarData("单位面积产值", DoNumberUtil.dblNullDowith(evaluateEnty.getOutPutLand()));
        RadarData data4 = new RadarData("单位面积税收", DoNumberUtil.dblNullDowith(evaluateEnty.getTaxesLand()));

        dataList1.add(data);
        dataList1.add(data1);
        dataList1.add(data2);
        dataList1.add(data3);
        dataList1.add(data4);
        radarView1.setDataList(dataList1);


        List<RadarData> dataList = new ArrayList<>();
        RadarData data5 = new RadarData("单位投资能耗", DoNumberUtil.dblNullDowith(evaluateEnty.getEnergyTotal()));
        RadarData data6 = new RadarData("单位投资就业", DoNumberUtil.dblNullDowith(evaluateEnty.getJobTotal()));
        RadarData data7 = new RadarData("单位投资利润", DoNumberUtil.dblNullDowith(evaluateEnty.getProfitTotal()));
        RadarData data8 = new RadarData("单位投资产值", DoNumberUtil.dblNullDowith(evaluateEnty.getOutPutTotal()));
        RadarData data9 = new RadarData("单位投资税收", DoNumberUtil.dblNullDowith(evaluateEnty.getTaxesTotal()));

        dataList.add(data5);
        dataList.add(data6);
        dataList.add(data7);
        dataList.add(data8);
        dataList.add(data9);
        radarView.setDataList(dataList);


        RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_CONCLUSION_STANDARD);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                conlusionSrandard = new Gson().fromJson(result, ResultConlusionSrandard.class).getData();
                if(null != conlusionSrandard){
                    ArrayList<ArrayList<String>> arrayLists = new ArrayList<>();

                    ArrayList<String> arrayList1 = new ArrayList<>();
                    arrayList1.add("评分标准");
                    arrayList1.add("蓝灯（100）");
                    arrayList1.add("绿灯（80）");
                    arrayList1.add("黄灯（60）");
                    arrayList1.add("红灯（40）");
                    arrayList1.add("双红灯（20）");
                    arrayList1.add("本项目得分");
                    arrayLists.add(arrayList1);

                    ArrayList<String> arrayList2 = new ArrayList<>();
                    arrayList2.add("单位面积能耗");
                    arrayList2.add(conlusionSrandard.getEnergyLand5());
                    arrayList2.add(conlusionSrandard.getEnergyLand4());
                    arrayList2.add(conlusionSrandard.getEnergyLand3());
                    arrayList2.add(conlusionSrandard.getEnergyLand2());
                    arrayList2.add(conlusionSrandard.getEnergyLand1());
                    arrayList2.add(evaluateEnty.getEnergyLand());
                    arrayLists.add(arrayList2);


                    ArrayList<String> arrayList3 = new ArrayList<>();
                    arrayList3.add("单位面积就业");
                    arrayList3.add(conlusionSrandard.getJobLand5());
                    arrayList3.add(conlusionSrandard.getJobLand4());
                    arrayList3.add(conlusionSrandard.getJobLand3());
                    arrayList3.add(conlusionSrandard.getJobLand2());
                    arrayList3.add(conlusionSrandard.getJobLand1());
                    arrayList3.add(evaluateEnty.getJobLand());
                    arrayLists.add(arrayList3);


                    ArrayList<String> arrayList4 = new ArrayList<>();
                    arrayList4.add("单位面积税收");
                    arrayList4.add(conlusionSrandard.getTaxesLand5());
                    arrayList4.add(conlusionSrandard.getTaxesLand4());
                    arrayList4.add(conlusionSrandard.getTaxesLand3());
                    arrayList4.add(conlusionSrandard.getTaxesLand2());
                    arrayList4.add(conlusionSrandard.getTaxesLand1());
                    arrayList4.add(evaluateEnty.getTaxesLand());
                    arrayLists.add(arrayList4);

                    ArrayList<String> arrayList5 = new ArrayList<>();
                    arrayList5.add("单位面积利润");
                    arrayList5.add(conlusionSrandard.getProfitLand5());
                    arrayList5.add(conlusionSrandard.getProfitLand4());
                    arrayList5.add(conlusionSrandard.getProfitLand3());
                    arrayList5.add(conlusionSrandard.getProfitLand2());
                    arrayList5.add(conlusionSrandard.getProfitLand1());
                    arrayList5.add(evaluateEnty.getProfitLand());
                    arrayLists.add(arrayList5);

                    ArrayList<String> arrayList6 = new ArrayList<>();
                    arrayList6.add("单位面积产值");
                    arrayList6.add(conlusionSrandard.getOutPutLand5());
                    arrayList6.add(conlusionSrandard.getOutPutLand4());
                    arrayList6.add(conlusionSrandard.getOutPutLand3());
                    arrayList6.add(conlusionSrandard.getOutPutLand2());
                    arrayList6.add(conlusionSrandard.getOutPutLand1());
                    arrayList6.add(evaluateEnty.getOutPutLand());
                    arrayLists.add(arrayList6);
                    setBiao(ly_biao1,arrayLists);





                    ArrayList<ArrayList<String>> arrayLists1 = new ArrayList<>();

                    ArrayList<String> arrayList7 = new ArrayList<>();
                    arrayList7.add("评分标准");
                    arrayList7.add("蓝灯（100）");
                    arrayList7.add("绿灯（80）");
                    arrayList7.add("黄灯（60）");
                    arrayList7.add("红灯（40）");
                    arrayList7.add("双红灯（20）");
                    arrayList7.add("本项目得分");
                    arrayLists1.add(arrayList7);

                    ArrayList<String> arrayList8 = new ArrayList<>();
                    arrayList8.add("单位投资能耗");
                    arrayList8.add(conlusionSrandard.getEnergyTotal5());
                    arrayList8.add(conlusionSrandard.getEnergyTotal4());
                    arrayList8.add(conlusionSrandard.getEnergyTotal3());
                    arrayList8.add(conlusionSrandard.getEnergyTotal2());
                    arrayList8.add(conlusionSrandard.getEnergyTotal1());
                    arrayList8.add(evaluateEnty.getEnergyTotal());
                    arrayLists1.add(arrayList8);


                    ArrayList<String> arrayList9 = new ArrayList<>();
                    arrayList9.add("单位投资就业");
                    arrayList9.add(conlusionSrandard.getJobTotal5());
                    arrayList9.add(conlusionSrandard.getJobTotal4());
                    arrayList9.add(conlusionSrandard.getJobTotal3());
                    arrayList9.add(conlusionSrandard.getJobTotal2());
                    arrayList9.add(conlusionSrandard.getJobTotal1());
                    arrayList9.add(evaluateEnty.getJobTotal());
                    arrayLists1.add(arrayList9);


                    ArrayList<String> arrayList10 = new ArrayList<>();
                    arrayList10.add("单位投资税收");
                    arrayList10.add(conlusionSrandard.getTaxesTotal5());
                    arrayList10.add(conlusionSrandard.getTaxesTotal4());
                    arrayList10.add(conlusionSrandard.getTaxesTotal3());
                    arrayList10.add(conlusionSrandard.getTaxesTotal2());
                    arrayList10.add(conlusionSrandard.getTaxesTotal1());
                    arrayList10.add(evaluateEnty.getTaxesTotal());
                    arrayLists1.add(arrayList10);

                    ArrayList<String> arrayList11 = new ArrayList<>();
                    arrayList11.add("单位投资利润");
                    arrayList11.add(conlusionSrandard.getProfitTotal5());
                    arrayList11.add(conlusionSrandard.getProfitTotal4());
                    arrayList11.add(conlusionSrandard.getProfitTotal3());
                    arrayList11.add(conlusionSrandard.getProfitTotal2());
                    arrayList11.add(conlusionSrandard.getProfitTotal1());
                    arrayList11.add(evaluateEnty.getProfitTotal());
                    arrayLists1.add(arrayList11);

                    ArrayList<String> arrayList12 = new ArrayList<>();
                    arrayList12.add("单位投资产值");
                    arrayList12.add(conlusionSrandard.getOutPutTotal5());
                    arrayList12.add(conlusionSrandard.getOutPutTotal4());
                    arrayList12.add(conlusionSrandard.getOutPutTotal3());
                    arrayList12.add(conlusionSrandard.getOutPutTotal2());
                    arrayList12.add(conlusionSrandard.getOutPutTotal1());
                    arrayList12.add(evaluateEnty.getOutPutTotal());
                    arrayLists1.add(arrayList12);
                    setBiao(ly_biao2,arrayLists1);

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


    private void setBiao(ViewGroup mContentView,ArrayList<ArrayList<String>> mTableDatas){
        final LockTableView mLockTableView = new LockTableView(this, mContentView, mTableDatas);
        mLockTableView.setLockFristColumn(true) //是否锁定第一列
                .setLockFristRow(true) //是否锁定第一行
                .setMaxColumnWidth(100) //列最大宽度
                .setMinColumnWidth(40) //列最小宽度
                .setMinRowHeight(20)//行最小高度
                .setMaxRowHeight(60)//行最大高度
                .setTextViewSize(16) //单元格字体大小
                .setFristRowBackGroudColor(R.color.table_head)//表头背景色
                .setTableHeadTextColor(R.color.beijin)//表头字体颜色
                .setTableContentTextColor(R.color.border_color)//单元格字体颜色
                .setNullableString("N/A") //空值替换值
                .setTableViewListener(new LockTableView.OnTableViewListener() {
                    //设置横向滚动监听
                    @Override
                    public void onTableViewScrollChange(int x, int y) {
                    }
                })
                .setTableViewRangeListener(new LockTableView.OnTableViewRangeListener() {
                    //设置横向滚动边界监听
                    @Override
                    public void onLeft(HorizontalScrollView view) {
                    }

                    @Override
                    public void onRight(HorizontalScrollView view) {
                    }
                })
                .setOnItemClickListenter(new LockTableView.OnItemClickListenter() {
                    @Override
                    public void onItemClick(View item, int position) {
                    }
                })
                .setOnItemLongClickListenter(new LockTableView.OnItemLongClickListenter() {
                    @Override
                    public void onItemLongClick(View item, int position) {
                    }
                })
                .setOnItemSeletor(R.color.dashline_color)//设置Item被选中颜色
                .show(); //显示表格,此方法必须调用
        mLockTableView.getTableScrollView().setPullRefreshEnabled(false);
        mLockTableView.getTableScrollView().setLoadingMoreEnabled(false);
        mLockTableView.getTableScrollView().setRefreshProgressStyle(ProgressStyle.SquareSpin);
    }
}
