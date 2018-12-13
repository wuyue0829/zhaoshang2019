package pdkj.zhaoshang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import pdkj.zhaoshang.R;
import pdkj.zhaoshang.database.CategoryTpyeEntry;
import pdkj.zhaoshang.database.DataDaoConfig;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultCategoryTpyeData;
import pdkj.zhaoshang.entity.ResultData;
import pdkj.zhaoshang.utils.AppInfoUtil;
import pdkj.zhaoshang.utils.SharePreferenceManager;

public class WelcomeActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mContext = this;
        initData();
    }

    private void initData() {
        //检测账号是否登陆
        UserInfo myInfo = JMessageClient.getMyInfo();
        if (myInfo == null) {
            RequestParams params = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_USER_TOKEN);
            params.addQueryStringParameter("uniqueId", AppInfoUtil.getMac());
            params.addQueryStringParameter("osType", "1");
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    ResultData resultData = new Gson().fromJson(result, ResultData.class);
                    SharePreferenceManager.setCachedUserToken(resultData.getData());
                    getLibProject();
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
        }else {
            goToMainActivity();
        }
    }



    private void getLibProject(){
        RequestParams params = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_LIB_PROJECT);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                DbManager db = x.getDb(DataDaoConfig.getDaoConfig());
                List<CategoryTpyeEntry> list = new Gson().fromJson(result, ResultCategoryTpyeData.class).getData();
                for(CategoryTpyeEntry categoryTpyeEntry:list){
                    try {
                        db.saveOrUpdate(categoryTpyeEntry);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
                goToRegisterAndLoginActivity();
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

    private void goToMainActivity() {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    private void goToRegisterAndLoginActivity() {
        startActivity(new Intent(mContext, LoginPdActivity.class));
        finish();
    }
}
