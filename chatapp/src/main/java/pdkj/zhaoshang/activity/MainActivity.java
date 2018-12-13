package pdkj.zhaoshang.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import cn.jiguang.api.JCoreInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import pdkj.zhaoshang.R;
import pdkj.zhaoshang.application.JGApplication;
import pdkj.zhaoshang.controller.MainController;
import pdkj.zhaoshang.utils.StatusBarUtil;
import pdkj.zhaoshang.utils.ThreadUtil;
import pdkj.zhaoshang.view.MainView;

public class MainActivity extends FragmentActivity {
    private MainController mMainController;
    private MainView mMainView;
    private Button actionbar_null_btn;
    private ImageView im_middle;
    private long firstTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            StatusBarUtil.setStatusBarTranslucent(this,true);
        }

        mMainView = (MainView) findViewById(R.id.main_view);
        mMainView.initModule();
        mMainController = new MainController(mMainView, this);
        im_middle = (ImageView) findViewById(R.id.im_middle);
        actionbar_null_btn = (Button) findViewById(R.id.actionbar_null_btn);

        im_middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddProjectActivity.class));
            }
        });

        actionbar_null_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddProjectActivity.class));
            }
        });

        mMainView.setOnClickListener(mMainController);
        mMainView.setOnPageChangeListener(mMainController);
    }

    public FragmentManager getSupportFragmentManger() {
        return getSupportFragmentManager();
    }

    @Override
    protected void onPause() {
        JCoreInterface.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        JCoreInterface.onResume(this);
        mMainController.sortConvList();
        JGApplication.mGroupInfoList.clear();
        ThreadUtil.runInThread(new Runnable() {
            @Override
            public void run() {
                JMessageClient.getGroupIDList(new GetGroupIDListCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage, List<Long> groupIDList) {
                        if (responseCode == 0) {
                            for (final Long groupID : groupIDList) {
                                JMessageClient.getGroupInfo(groupID, new GetGroupInfoCallback() {
                                    @Override
                                    public void gotResult(int responseCode, String responseMessage, GroupInfo groupInfo) {
                                        if (responseCode == 0) {
                                            JGApplication.mGroupInfoList.add(groupInfo);
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {
                    this.finish();
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
