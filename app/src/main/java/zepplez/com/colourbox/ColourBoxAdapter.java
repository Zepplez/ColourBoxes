package zepplez.com.colourbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AndrewZ on 7/05/15.
 */
class BoxColourAdapter extends ArrayAdapter<ColourBoxes> {
    protected Context mContext;
    protected List<ColourBoxes> mBoxes;
    protected int mLevel;
    protected int mSize;

    public BoxColourAdapter(Context context, List<ColourBoxes> boxes, int level, int size){
        super(context, R.layout.box_item, boxes);
        mContext = context;
        mBoxes = boxes;
        mLevel = level;
        mSize = size;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.box_item, null);
            holder = new ViewHolder();
            holder.boxLayout = (RelativeLayout) convertView.findViewById(R.id.boxLayout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        ColourBoxes box = mBoxes.get(position);

        holder.boxLayout.setBackgroundColor(box.mColour);
        ViewGroup.LayoutParams params = holder.boxLayout.getLayoutParams();
        params.height = mSize;
        params.width = mSize;
        return convertView;
    }

    public static class ViewHolder {
        RelativeLayout boxLayout;
    }

    public void refill(List<ColourBoxes> boxes) {
        mBoxes.clear();
        mBoxes.addAll(boxes);
    }
}
