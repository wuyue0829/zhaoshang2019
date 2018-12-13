package pdkj.zhaoshang.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MyListView extends ListView {

    private int height;


    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        View item = adapter.getView(0, null, null);
        item.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        int itemHeight = item.getMeasuredHeight();
        int count = adapter.getCount();
        height = itemHeight * count;
        measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), height);
    }

    public int getRealHeight() {
        return height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }
}