package pdkj.zhaoshang.utils;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.platform.comapi.map.I;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import pdkj.zhaoshang.R;
import pdkj.zhaoshang.activity.AddEventActivity;
import pdkj.zhaoshang.activity.NodeDetailyActivity;
import pdkj.zhaoshang.activity.ProjectDetailActivity;
import pdkj.zhaoshang.database.FriendEntry;
import pdkj.zhaoshang.database.UserEntry;
import pdkj.zhaoshang.entity.NodeEntity;
import pdkj.zhaoshang.entity.ResultEventData;
import pdkj.zhaoshang.entity.ResultProjectData;

public class Item_Nodes extends LinearLayout {
    private Context mContext;
    private TextView tv_node_title;
    private ImageView im_icon;
    private RelativeLayout rl_tianjia;
    private RelativeLayout rl_contnet;
    private NodeEntity nodeEntity;
    private String projectJieDuan;
    private boolean isShowAdd;
    private String projectID;
    private ResultEventData.Event projectData;


    public Item_Nodes(Context context) {
        super(context);
    }


    public Item_Nodes(Context context, ResultEventData.Event projectData, boolean isShowAdd, NodeEntity nodeEntity,String projectJieDuan,String projectID) {
        this(context);
        mContext = context;
        this.isShowAdd = isShowAdd;
        this.projectID =projectID;
        this.projectData = projectData;
        this.nodeEntity = nodeEntity;
        this.projectJieDuan = projectJieDuan;
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_nodes, this,true);
        tv_node_title = findViewById(R.id.tv_node_title);
        im_icon = findViewById(R.id.im_icon);
        rl_tianjia = findViewById(R.id.rl_tianjia);
        rl_contnet = findViewById(R.id.rl_contnet);

        if(null != projectData){

            if(!BaseUtil.isSpace(projectData.getTitle())){
                tv_node_title.setText(projectData.getTitle());
            }

            if(isShowAdd){
                rl_tianjia.setVisibility(View.VISIBLE);
            }else{
                rl_tianjia.setVisibility(View.GONE);
            }


        }else{
            rl_contnet.setVisibility(View.GONE);
            rl_tianjia.setVisibility(View.VISIBLE);
        }

        rl_tianjia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddEventActivity.class);
                intent.putExtra("NodeID",nodeEntity.getNodeID());
                intent.putExtra("NodeName",nodeEntity.getNodeName());
                if(BaseUtil.isSpace(nodeEntity.getProjectID())){
                    intent.putExtra("ProjectID",nodeEntity.getProjectID());
                }else{
                    intent.putExtra("ProjectID",projectID);
                }

                intent.putExtra("ProjectJieDuan",nodeEntity.getProjectJieDuan());
                intent.putExtra("ProjectName",nodeEntity.getProjectName());
                intent.putExtra("projectJieDuan",projectJieDuan);
                mContext.startActivity(intent);
            }
        });

        rl_contnet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NodeDetailyActivity.class);
                intent.putExtra("id",projectData.getId());
                mContext.startActivity(intent);

            }
        });



    }
}
