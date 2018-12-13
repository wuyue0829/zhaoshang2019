package pdkj.zhaoshang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultLoginData;
import pdkj.zhaoshang.utils.BaseUtil;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.ToastUtil;

public class FangActivity extends BaseActivity{


    private RelativeLayout rl_head_return;
    private RelativeLayout rl_submit;
    private EditText ed_title;
    private EditText ed_renshu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fang);
        init();
        initData();
    }

    private void init(){
        rl_head_return = (RelativeLayout) findViewById(R.id.rl_head_return);
        rl_submit = (RelativeLayout) findViewById(R.id.rl_submit);
        ed_title = (EditText) findViewById(R.id.ed_title);
        ed_renshu = (EditText) findViewById(R.id.ed_renshu);
    }

    private void initData(){
        rl_head_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BaseUtil.isSpace(ed_title.getText().toString()) || BaseUtil.isSpace(ed_renshu.getText().toString()) ){
                    ToastUtil.shortToast(mContext,"请输入必填的信息");
                }else{
                    RequestParams params = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_USER_FEEDBACK);
                    params.addHeader("token", SharePreferenceManager.getCachedUserToken());
                    params.addQueryStringParameter("renshu", "");
                    params.addQueryStringParameter("name", "");
                    x.http().post(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            goToActivity(mContext, MainActivity.class);
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
        });
    }
}
