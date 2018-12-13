package pdkj.zhaoshang.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import pdkj.zhaoshang.R;
import pdkj.zhaoshang.adapter.StickyListAdapter;
import pdkj.zhaoshang.application.JGApplication;
import pdkj.zhaoshang.controller.LoginController;
import pdkj.zhaoshang.database.FriendEntry;
import pdkj.zhaoshang.database.UserEntry;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultLoginData;
import pdkj.zhaoshang.utils.DialogCreator;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.ToastUtil;
import pdkj.zhaoshang.utils.pinyin.HanziToPinyin;
import pdkj.zhaoshang.utils.pinyin.PinyinComparator;

/**
 * 创建时间： 2018/1/30
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class LoginPdActivity extends BaseActivity implements View.OnClickListener{

    private Button bt_login;
    private EditText ed_name;
    private EditText ed_password;
    private Button bt_login_fangke;
    private boolean isFangke = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpd);
        setSwipeBackEnable(false);
        initView();
        initData();
    }


    private void initView(){
        bt_login = (Button) findViewById(R.id.bt_login);
        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_password = (EditText) findViewById(R.id.ed_password);
        bt_login_fangke = (Button) findViewById(R.id.bt_login_fangke);
        bt_login.setOnClickListener(this);
        bt_login_fangke.setOnClickListener(this);

    }

    private void initData(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                if(checkInput()){
                    login(ed_name.getText().toString().trim(),ed_password.getText().toString().trim());
                }
                break;
            case R.id.bt_login_fangke:
                isFangke = true;
                login("13333332222","111111");
                break;
        }
    }

    private boolean checkInput(){
        if (TextUtils.isEmpty(ed_name.getText().toString().trim())) {
            ToastUtil.shortToast(mContext, "用户名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(ed_password.getText().toString().trim())) {
            ToastUtil.shortToast(mContext, "密码不能为空");
            return false;
        }
        return true;
    }

    private void login(String userPhone,String password){
        final Dialog dialog = DialogCreator.createLoadingDialog(mContext,
                mContext.getString(R.string.login_hint));
        dialog.show();
        RequestParams params = new RequestParams(NetContants.BASE_URL+NetContants.URL_GET_USER_LOGIN);
        params.addHeader("token",SharePreferenceManager.getCachedUserToken());
        params.addQueryStringParameter("loginName", userPhone);
        params.addQueryStringParameter("password", password);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ResultLoginData resultLoginData = new Gson().fromJson(result,ResultLoginData.class);
                if(resultLoginData.getResult().equals("0")){
                    loginJiGuangIM(resultLoginData.getData().getLoginName(),
                            resultLoginData.getData().getPassword(),dialog);
                }else{
                    ToastUtil.shortToast(mContext,resultLoginData.getMessage());
                    dialog.dismiss();
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

    private void loginJiGuangIM(String userId, final String password ,final Dialog dialog){
        JMessageClient.login(userId, password, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                dialog.dismiss();
                if (responseCode == 0) {
                    SharePreferenceManager.setCachedPsw(password);
                    UserInfo myInfo = JMessageClient.getMyInfo();
                    File avatarFile = myInfo.getAvatarFile();
                    //登陆成功,如果用户有头像就把头像存起来,没有就设置null
                    if (avatarFile != null) {
                        SharePreferenceManager.setCachedAvatarPath(avatarFile.getAbsolutePath());
                    } else {
                        SharePreferenceManager.setCachedAvatarPath(null);
                    }
                    String username = myInfo.getUserName();
                    String appKey = myInfo.getAppKey();
                    UserEntry user = UserEntry.getUser(username, appKey);
                    if (null == user) {
                        user = new UserEntry(username, appKey);
                        user.save();
                    }

                    final UserEntry user1 = UserEntry.getUser(JMessageClient.getMyInfo().getUserName(),
                            JMessageClient.getMyInfo().getAppKey());
                    ContactManager.getFriendList(new GetUserInfoListCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage, List<UserInfo> userInfoList) {
                            if (responseCode == 0) {
                                if (userInfoList.size() != 0) {
                                    ActiveAndroid.beginTransaction();
                                    try {
                                        for (UserInfo userInfo : userInfoList) {
                                            String displayName = userInfo.getDisplayName();
                                            String letter;
                                            if (!TextUtils.isEmpty(displayName.trim())) {
                                                ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance()
                                                        .get(displayName);
                                                StringBuilder sb = new StringBuilder();
                                                if (tokens != null && tokens.size() > 0) {
                                                    for (HanziToPinyin.Token token : tokens) {
                                                        if (token.type == HanziToPinyin.Token.PINYIN) {
                                                            sb.append(token.target);
                                                        } else {
                                                            sb.append(token.source);
                                                        }
                                                    }
                                                }
                                                String sortString = sb.toString().substring(0, 1).toUpperCase();
                                                if (sortString.matches("[A-Z]")) {
                                                    letter = sortString.toUpperCase();
                                                } else {
                                                    letter = "#";
                                                }
                                            } else {
                                                letter = "#";
                                            }
                                            //避免重复请求时导致数据重复A
                                            FriendEntry friend = FriendEntry.getFriend(user1,
                                                    userInfo.getUserName(), userInfo.getAppKey());
                                            if (null == friend) {
                                                if (TextUtils.isEmpty(userInfo.getAvatar())) {
                                                    friend = new FriendEntry(userInfo.getUserID(), userInfo.getUserName(), userInfo.getNotename(), userInfo.getNickname(), userInfo.getAppKey(),
                                                            null, displayName, letter, user1);
                                                } else {
                                                    friend = new FriendEntry(userInfo.getUserID(), userInfo.getUserName(), userInfo.getNotename(), userInfo.getNickname(), userInfo.getAppKey(),
                                                            userInfo.getAvatarFile().getAbsolutePath(), displayName, letter, user1);
                                                }
                                                friend.save();
                                            }
                                        }
                                        ActiveAndroid.setTransactionSuccessful();
                                    } finally {
                                        ActiveAndroid.endTransaction();
                                    }
                                } else {
                                }

                                if(isFangke){
                                    goToActivity(mContext, MainActivity.class);
                                    ToastUtil.shortToast(mContext, "登陆成功");
                                }else{
                                    goToActivity(mContext, MainActivity.class);
                                    ToastUtil.shortToast(mContext, "登陆成功");
                                }

                            }
                        }
                    });

                } else {
                    ToastUtil.shortToast(mContext, "登陆失败" + responseMessage);
                }
            }
        });
    }


}
