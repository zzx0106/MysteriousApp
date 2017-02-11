package mytapp.xmz.com.mysteriousapp.modules.qiwen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.bmobbean.TalkBean;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.widget.XCRoundImageView;

/**
 * Created by Administrator on 2016/11/16.
 */
public class CommentAdapter extends BaseAdapter {

    private final Context context;
    private final List<TalkBean> list;

    public CommentAdapter(Context context, List<TalkBean> list) {
        this.context =context;
        this.list =list;
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
        viewHolder holder =null;
        if(convertView==null){
            holder = new viewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.qiwen_item_comment_layout,parent,false);
            holder.icon = (XCRoundImageView) convertView.findViewById(R.id.qiwen_comment_icon);
            holder.talkview = (TextView) convertView.findViewById(R.id.qiwen_comment_talk);
            holder.usernameview = (TextView) convertView.findViewById(R.id.qiwen_comment_username);
            holder.timeview = (TextView) convertView.findViewById(R.id.qiwen_comment_time);
            convertView.setTag(holder);
        }else {
            holder = (viewHolder) convertView.getTag();
        }

        TalkBean talkBean = list.get(position);
        holder.usernameview.setText(talkBean.getUsername());
        holder.talkview.setText(talkBean.getTalk());
        holder.timeview.setText(talkBean.getTime());
        //holder.icon.setImageBitmap();
        return convertView;
    }

    class viewHolder{
        XCRoundImageView icon ;
        TextView usernameview,talkview,timeview;
    }
}
