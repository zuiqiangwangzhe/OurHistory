package ourhistory.hj.com.ourhistory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ourhistory.hj.com.ourhistory.R;
import ourhistory.hj.com.ourhistory.java.EventInfo;

/**
 * Created by Administrator on 2016/8/6.
 */
public class EventAdapter extends BaseAdapter{
    Context context;
    List<EventInfo> list;
    LayoutInflater layoutInflater;

    public EventAdapter(Context context,List<EventInfo> list){
        this.context=context;
        this.list=list;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.item_event,null);
            viewHolder.number= (TextView) convertView.findViewById(R.id.item_even_number);
            viewHolder.time= (TextView) convertView.findViewById(R.id.item_even_time);
            viewHolder.content= (TextView) convertView.findViewById(R.id.item_even_content);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        EventInfo eventInfo=list.get(position);
        viewHolder.number.setText(eventInfo.getNumber()+"");
        viewHolder.time.setText(eventInfo.getTime());
        viewHolder.content.setText(eventInfo.getContent());
        return convertView;
    }

    class ViewHolder{
        TextView number;
        TextView time;
        TextView content;
    }

    public void refresh(List<EventInfo> list){
        this.list=list;
        notifyDataSetChanged();
    }
}
