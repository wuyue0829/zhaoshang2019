package pdkj.zhaoshang.activity.fragment;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;
import de.hdodenhof.circleimageview.CircleImageView;
import me.drakeet.materialdialog.MaterialDialog;
import pdkj.zhaoshang.R;
import pdkj.zhaoshang.activity.FeedbackActivity;
import pdkj.zhaoshang.activity.LoginPdActivity;
import pdkj.zhaoshang.activity.MatterNeedActivity;
import pdkj.zhaoshang.activity.PersonalActivity;
import pdkj.zhaoshang.activity.WorkRecordActivity;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.ResultLibAppData;
import pdkj.zhaoshang.service.DownloadService;
import pdkj.zhaoshang.utils.AppInfoUtil;
import pdkj.zhaoshang.utils.SharePreferenceManager;
import pdkj.zhaoshang.utils.ToastUtil;
import pdkj.zhaoshang.utils.photovideo.takevideo.utils.LogUtils;

/**
 * Created by ${chenyn} on 2017/2/20.
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener,PermissionListener {
    private View mRootView;
    private Context mContext;
    private CircleImageView im_photo;
    private TextView tv_name;
    private RelativeLayout rl_xiugai;
    private RelativeLayout rl_feedback;
    private RelativeLayout rl_updata;
    private RelativeLayout rl_need;
    private RelativeLayout rl_work_record;
    private Bitmap mBitmap;
    private String urlStr;
    private static final int PERMISSION_WRITE = 2000;
    public static final String PERSONAL_PHOTO = "personal_photo";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        mRootView = layoutInflater.inflate(R.layout.fragment_setting,
                (ViewGroup) getActivity().findViewById(R.id.main_view), false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup p = (ViewGroup) mRootView.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        im_photo = (CircleImageView) mRootView.findViewById(R.id.im_photo);
        tv_name = (TextView) mRootView.findViewById(R.id.tv_name);
        rl_xiugai = (RelativeLayout) mRootView.findViewById(R.id.rl_xiugai);
        rl_updata = (RelativeLayout) mRootView.findViewById(R.id.rl_updata);
        rl_feedback = (RelativeLayout) mRootView.findViewById(R.id.rl_feedback);
        rl_work_record = (RelativeLayout) mRootView.findViewById(R.id.rl_work_record);
        rl_need = mRootView.findViewById(R.id.rl_need);
        rl_xiugai.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);
        rl_updata.setOnClickListener(this);
        rl_need.setOnClickListener(this);
        rl_work_record.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        UserInfo myInfo = JMessageClient.getMyInfo();
        myInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
            @Override
            public void gotResult(int i, String s, Bitmap bitmap) {
                if (i == 0) {
                    mBitmap = bitmap;
                    im_photo.setImageBitmap(bitmap);
                }else {
                    mBitmap = null;
                    im_photo.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rc_default_portrait));
                }
            }
        });
        if(!TextUtils.isEmpty(myInfo.getNickname())){
            tv_name.setText(myInfo.getNickname());
        }else{
            tv_name.setText(myInfo.getUserName());
        }

        super.onResume();
    }

    public void cancelNotification() {
        NotificationManager manager = (NotificationManager) this.getActivity().getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    //退出登录
    public void Logout() {
        final Intent intent = new Intent();
        UserInfo info = JMessageClient.getMyInfo();
        if (null != info) {
            SharePreferenceManager.setCachedUsername(info.getUserName());
            if (info.getAvatarFile() != null) {
                SharePreferenceManager.setCachedAvatarPath(info.getAvatarFile().getAbsolutePath());
            }
            JMessageClient.logout();
            intent.setClass(mContext, LoginPdActivity.class);
            startActivity(intent);
        } else {
            ToastUtil.shortToast(mContext, "退出失败");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_xiugai:
                Intent intent = new Intent(mContext, PersonalActivity.class);
                intent.putExtra(PERSONAL_PHOTO, mBitmap);
                mContext.startActivity(intent);
                break;
            case R.id.rl_feedback:
                Intent intent1 = new Intent(mContext, FeedbackActivity.class);
                mContext.startActivity(intent1);
                break;
            case R.id.rl_updata:
                RequestParams params1 = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_APPVERSION_CHECK+"1"+"/"+ (AppInfoUtil.getAppVersionCode(mContext)+""));
                params1.addHeader("token",SharePreferenceManager.getCachedUserToken());
                x.http().get(params1, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        final ResultLibAppData libAppUpdata = new Gson().fromJson(result,ResultLibAppData.class);
                        if(libAppUpdata.getResult().equals("0")){
                            final MaterialDialog mMaterialDialog1 = new MaterialDialog(mContext);
                            mMaterialDialog1.setTitle("提示！");
                            mMaterialDialog1.setMessage("有新的版本！");
                            mMaterialDialog1.setPositiveButton("更新", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    urlStr = libAppUpdata.getData().getUploadUri();
                                    AndPermission.with(getActivity())
                                            .requestCode(PERMISSION_WRITE)
                                            .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                            // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
                                            .rationale(new RationaleListener() {
                                                @Override
                                                public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                                    rationale.resume();
                                                }
                                            }).send();

                                    Intent intent = new Intent(getActivity(),  DownloadService.class);
                                    intent.putExtra("downloadurl",NetContants.BASE_URL_RESOURCE+libAppUpdata.getData().getUploadUri());
                                    getActivity().startService(intent);
                                    mMaterialDialog1.dismiss();
                                    ToastUtil.shortToast(mContext,"已开始后台下载");
                                }
                            });
                            mMaterialDialog1.setNegativeButton("取消", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mMaterialDialog1.dismiss();
                                }
                            });
                            mMaterialDialog1.show();
                        }else{
                            ToastUtil.shortToast(mContext,"您当前是最新版本！");
                        }
                    }
                    //请求异常后的回调方法
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        LogUtils.e("ex",ex.getMessage());
                    }
                    //主动调用取消请求的回调方法
                    @Override
                    public void onCancelled(CancelledException cex) {
                        LogUtils.e("ex",cex.getMessage());
                    }
                    @Override
                    public void onFinished() {
                    }
                });
                break;
            case  R.id.rl_need:
                startActivity(new Intent(mContext,MatterNeedActivity.class));
                break;
            case R.id.rl_work_record:
                startActivity(new Intent(mContext,WorkRecordActivity.class));
                break;
        }
    }


    @Override
    public void onSucceed(int requestCode, List<String> grantPermissions) {
        Intent intent = new Intent(mContext,  DownloadService.class);
        intent.putExtra("downloadurl",urlStr);
        mContext.startService(intent);
    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {
        Toast.makeText(mContext, "我们需要您的权限来保存新版本apk", Toast.LENGTH_SHORT).show();
    }
}
