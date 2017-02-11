package mytapp.xmz.com.mysteriousapp.modules.tuji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.bmobbean.TalkBean;

/**
 * Created by Administrator on 2001/1/2 0002.
 */
public class MyCommentAdapter extends BaseAdapter {
    private Context context;
    private List<TalkBean> list;

    public MyCommentAdapter(List<TalkBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.tuji_comment_item_layout,parent,false);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.tuji_comment_item_text);
            holder.username = (TextView) convertView.findViewById(R.id.tuji_comment_item_username);
            holder.time = (TextView) convertView.findViewById(R.id.tuji_comment_item_time);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(list.get(position).getTalk());
        holder.time.setText(list.get(position).getTime());
        holder.username.setText(list.get(position).getUsername());
        return convertView;
    }
    class ViewHolder{
        TextView text,username,time;
    }
}
