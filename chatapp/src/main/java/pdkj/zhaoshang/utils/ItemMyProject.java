package pdkj.zhaoshang.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import pdkj.zhaoshang.R;
import pdkj.zhaoshang.activity.ProjectDetailActivity;
import pdkj.zhaoshang.database.CategoryTpyeEntry;
import pdkj.zhaoshang.database.DataDaoConfig;
import pdkj.zhaoshang.database.FriendEntry;
import pdkj.zhaoshang.database.UserEntry;
import pdkj.zhaoshang.entity.ResultMyProjectData;
import pdkj.zhaoshang.entity.ResultProjectData;

public class ItemMyProject extends LinearLayout {
    private Context mContext;
    private TextView tv_project_name;
    private TextView tv_gsname;
    private TextView tv_fuzeren;
    private TextView tv_date;
    private TextView tv_content;
    private TextView tv_jieguo;
    private RelativeLayout rl_back;
    private ResultMyProjectData.ProjectData projectData;
    private List<FriendEntry> findAll3 = new ArrayList<>();


    public ItemMyProject(Context context) {
        super(context);
    }


    public ItemMyProject(Context context, ResultMyProjectData.ProjectData projectData) {
        this(context);
        mContext = context;
        this.projectData = projectData;
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_project, this,true);
        tv_project_name = (TextView) findViewById(R.id.tv_project_name);
        tv_gsname = (TextView) findViewById(R.id.tv_gsname);
        tv_fuzeren = (TextView) findViewById(R.id.tv_fuzeren);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_jieguo = (TextView) findViewById(R.id.tv_jieguo);
        rl_back = (RelativeLayout) findViewById(R.id.rl_back);

        tv_project_name.setText(projectData.getCategoryTitle());
        tv_gsname.setText(projectData.getCustomer());
        UserEntry user = UserEntry.getUser(JMessageClient.getMyInfo().getUserName(),
                JMessageClient.getMyInfo().getAppKey());
        findAll3 = user.getFriends();
        FriendEntry friendEntry = new FriendEntry();
        friendEntry.username = JMessageClient.getMyInfo().getUserName();
        friendEntry.nickName = JMessageClient.getMyInfo().getNickname();
        findAll3.add(friendEntry);
        for(FriendEntry friendEntry1:findAll3){
            if(friendEntry1.username.equals(projectData.getHeader())){
                tv_fuzeren.setText(friendEntry1.nickName);
            }
        }

        tv_date.setText(DateUtil.stampToDate(projectData.getStartDate()));
        tv_content.setText(projectData.getTrade());
        DbManager db = x.getDb(DataDaoConfig.getDaoConfig());
        try {
            List<CategoryTpyeEntry> list = db.selector(CategoryTpyeEntry.class).where("id","==",DoNumberUtil.intNullDowith(projectData.getTrade())).findAll();
            if(list.size()>0){
                tv_content.setText(list.get(0).getName());
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        if(!BaseUtil.isSpace(projectData.getEventTitle())){
            rl_back.setBackground(getResources().getDrawable(R.drawable.icon_homelist));
            tv_jieguo.setText(projectData.getEventTitle());
        }else{
            rl_back.setBackground(getResources().getDrawable(R.drawable.icon_homeunlist));
            tv_jieguo.setText("该项目处于未开始阶段");
        }

        rl_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProjectDetailActivity.class);
                if(projectData.getType().equals("1")){
                    intent.putExtra("type","1");
                }else{
                    intent.putExtra("type","2");
                }
                intent.putExtra("title",projectData.getCategoryTitle());
                intent.putExtra("categoryId",projectData.getId());
                intent.putExtra("stageId",projectData.getStageId());
                mContext.startActivity(intent);
            }
        });
    }
}
