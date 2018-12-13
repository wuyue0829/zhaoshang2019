package pdkj.zhaoshang.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xrichtext.XRichText;
import pdkj.zhaoshang.R;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultNodeFinalData;
import pdkj.zhaoshang.utils.BaseUtil;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.view.NineGridTestLayout;


/**
 * 创建时间： 2018/2/10
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class NodeDetailyActivity extends BaseActivity{

    private RelativeLayout rl_head_return;
    private TextView tv_title;
    private TextView tv_time;
    NineGridTestLayout layout;
    private String id = "";
    private XRichText richText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_detail);
        initView();
        initData();
    }

    private void initView(){
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_time = (TextView) findViewById(R.id.tv_time);
        layout = (NineGridTestLayout) findViewById(R.id.layout_nine_grid);
        richText = (XRichText) findViewById(R.id.richText);
        id = getIntent().getExtras().getString("id");
    }

    private void initData(){
        rl_head_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_EVENT_FINDBYID);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        params.addQueryStringParameter("eventId", id);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ResultNodeFinalData resultNodeFinalData = new Gson().fromJson(result, ResultNodeFinalData.class);
                tv_time.setText(resultNodeFinalData.getData().getHappenDate());

                tv_title.setText(resultNodeFinalData.getData().getTitle());
                layout.setIsShowAll(true);
                resultNodeFinalData.getData().getImages();
                List<String> listStr = new ArrayList<>();
                CharSequence charSequence= Html.fromHtml(resultNodeFinalData.getData().getContent());
                richText.setText(charSequence);
                String[] sourceStrArray = resultNodeFinalData.getData().getImages().split(",");
                if(!BaseUtil.isSpace(resultNodeFinalData.getData().getImages())){
                    for(String str:getArrList(sourceStrArray)){
                        listStr.add(NetContants.BASE_URL_RESOURCE+str);
                    }
                    if(!BaseUtil.isSpace(listStr)){
                        layout.setUrlList(listStr);
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

    private List<String> getArrList(String[] arr) {
        List<String> list = new ArrayList<String>();
        for(String col : arr) {
            list.add(col);
        }
        return list;
    }
}
