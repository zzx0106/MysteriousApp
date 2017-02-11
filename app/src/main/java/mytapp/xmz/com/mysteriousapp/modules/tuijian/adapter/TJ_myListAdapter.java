package mytapp.xmz.com.mysteriousapp.modules.tuijian.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.modules.tuijian.bean.Tuijian;

/**
 * Created by WXW20147854 on 2016/11/12.
 */
public class TJ_myListAdapter extends BaseAdapter {
    private List<Tuijian> lists;
    private Context context;

    public TJ_myListAdapter(Context context, List<Tuijian> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if(convertView == null){
            view = View.inflate(context, R.layout.tuijian_listview_item, null);
            holder = new ViewHolder();

            holder.tj_iv = (ImageView) view.findViewById(R.id.tj_iv);
            holder.tj_tv_title = (TextView) view.findViewById(R.id.tj_tv_title);
            holder.tj_tv_datetime = (TextView) view.findViewById(R.id.tj_tv_datetime);
            holder.tj_tv_comment = (TextView) view.findViewById(R.id.tj_tv_comment);
            view.setTag(holder);
        }
        else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        // 讲文本数据设置到ListView上
        holder.tj_tv_title.setText(lists.get(position).getArticle_title());
        holder.tj_tv_datetime.setText(lists.get(position).getDatetime());
        holder.tj_tv_comment.setText(lists.get(position).getComment_sum() + "评论");

        // 图片设置
        Picasso.with(context).load(lists.get(position).getArticle_thumb()).into(holder.tj_iv);

        return view;
    }

    class ViewHolder{
        ImageView tj_iv;
        TextView tj_tv_title;
        TextView tj_tv_datetime;
        TextView tj_tv_comment;
    }
}
