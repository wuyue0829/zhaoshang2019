package pdkj.zhaoshang.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import pdkj.zhaoshang.R;
import pdkj.zhaoshang.activity.ProjectDetailActivity;
import pdkj.zhaoshang.entity.NetContants;
import pdkj.zhaoshang.entity.NodeEntity;
import pdkj.zhaoshang.entity.ResultCategoryData;
import pdkj.zhaoshang.entity.ResultDetailListData;
import pdkj.zhaoshang.entity.ResultEventData;

/**
 * 创建时间： 2018/2/9
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class Item_project_content extends LinearLayout{
    private Context mContext;
    private TextView tv_first;
    private TextView tv_last;
    private TextView tv_title;
    private ImageView im_state;
    private boolean isLast;
    private LinearLayout ly_contnet;
    private RelativeLayout rl_title;
    private String projectID;
    private String projectName;
    private String projectJieDuan;
    private ResultDetailListData.NodeList nodeList;

    public Item_project_content(Context context) {
        super(context);
    }


    public Item_project_content(Context context,ResultDetailListData.NodeList projectData,boolean isFirst,boolean isLast,String projectID,
                                String projectName,String projectJieDuan) {
        this(context);
        this.isLast = isLast;
        this.nodeList = projectData;
        this.projectName = projectName;
        this.projectJieDuan = projectJieDuan;
        mContext = context;
        this.projectID = projectID;
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_node, this,true);
        tv_last = findViewById(R.id.tv_last);
        ly_contnet = findViewById(R.id.ly_contnet);
        rl_title = findViewById(R.id.rl_title);
        tv_title = findViewById(R.id.tv_title);
        im_state = findViewById(R.id.im_state);

        if(isLast){
            tv_last.setVisibility(INVISIBLE);
        }else{
            tv_last.setVisibility(INVISIBLE);
        }

        if(!BaseUtil.isSpace(nodeList.getTitle())){
            tv_title.setText(nodeList.getTitle());
        }
        ly_contnet.setVisibility(View.GONE);
        //获取节点数据
        RequestParams params = new RequestParams(NetContants.BASE_URL + NetContants.URL_GET_EVENT_LIST);
        params.addHeader("token", SharePreferenceManager.getCachedUserToken());
        params.addQueryStringParameter("categoryId",projectID);
        params.addQueryStringParameter("nodeId",nodeList.getId());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ResultEventData resultEventData = new Gson().fromJson(result, ResultEventData.class);
                NodeEntity nodeEntity = new NodeEntity();
                nodeEntity.setProjectID(projectID);
                nodeEntity.setProjectName(projectName);
                nodeEntity.setProjectJieDuan(projectJieDuan);
                nodeEntity.setNodeID(nodeList.getId());
                nodeEntity.setNodeName(nodeList.getTitle());
                if(!BaseUtil.isSpace(resultEventData.getData())){
                    im_state.setImageDrawable(getResources().getDrawable(R.drawable.icon_jdjh));
                    for(int i = 0;i<resultEventData.getData().size()-1;i++){

                        ly_contnet.addView(new Item_Nodes(mContext,resultEventData.getData().get(i),false,nodeEntity,projectJieDuan,projectID));
                    }
                    ly_contnet.addView(new Item_Nodes(mContext,resultEventData.getData().get(resultEventData.getData().size()-1),true,nodeEntity,projectJieDuan,projectID));
                }else{
                    ly_contnet.addView(new Item_Nodes(mContext,null,false,nodeEntity,projectJieDuan,projectID));
                    im_state.setImageDrawable(getResources().getDrawable(R.drawable.icon_jdwjh));
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

        rl_title.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ly_contnet.getVisibility() == View.VISIBLE){
                    ly_contnet.setVisibility(View.GONE);
                    tv_title.setTextColor(getResources().getColor(R.color.blue));
                }else{
                    ly_contnet.setVisibility(View.VISIBLE);
                    tv_title.setTextColor(getResources().getColor(R.color.orange));
                }
            }
        });

    }

}
