package pdkj.zhaoshang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

import pdkj.zhaoshang.R;
import pdkj.zhaoshang.entity.ResultMatterListData;
import pdkj.zhaoshang.utils.BaseUtil;

/**
 * 创建时间： 2018/1/31
 * <p>
 * 编写人：ASMory
 * <p>
 * 功能简述：
 */
public class MatterNeedAdapter extends RecyclerView.Adapter<MatterNeedAdapter.MyViewHolder> {

    private Context context;
    private List<ResultMatterListData.Matter.MatterEntye> list;

    public MatterNeedAdapter(Context context) {
        this.context = context;
    }

    public MatterNeedAdapter(Context context, List<ResultMatterListData.Matter.MatterEntye> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(null != list&&list.size()>0){
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_matter_nedd, parent,false));
        }else{
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_null, parent,false));
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if(!BaseUtil.isSpace(list)){
            holder.tv_title.setText(list.get(position).getItemName());
            holder.tv_matter_time.setText(list.get(position).getPlannedTime());
            holder.tv_content.setText(list.get(position).getItemContent());
            holder.tv_project.setText(list.get(position).getCategoryName());
        }
    }

    @Override
    public int getItemCount() {
        if(null != list){
            return list.size();
        }else{
            return 1;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_project;
        TextView tv_content;
        TextView tv_matter_time;


        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_project = (TextView) itemView.findViewById(R.id.tv_project);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_matter_time = (TextView) itemView.findViewById(R.id.tv_matter_time);
        }
    }

}
