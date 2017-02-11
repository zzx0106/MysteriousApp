package mytapp.xmz.com.mysteriousapp.modules.tuji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.modules.tuji.bean.TujiInfo;

/**
 * Created by Administrator on 2016/11/11 0011.
 */
public class MyAdapter extends BaseAdapter{
    private List<TujiInfo> list;
    private Context context;

    public MyAdapter(Context context, List<TujiInfo> tujiInfos) {
        this.context = context;
        this.list = tujiInfos;
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
        if(convertView == null){
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.tuji_item_layout,parent,false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.tuji_item_text1);
            holder.comment = (TextView) convertView.findViewById(R.id.tuji_item_text2);
            holder.picNum = (TextView) convertView.findViewById(R.id.tuji_picNum);
            holder.image = (ImageView) convertView.findViewById(R.id.tuji_item_img);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置数据
        Picasso.with(context).load(list.get(position).getArticle_thumb()).tag("listview").into(holder.image);
        holder.picNum.setText(list.get(position).getArticle_thumb_sum()+"图片");
        holder.title.setText(list.get(position).getArticle_title());
        holder.comment.setText(list.get(position).getComment_sum()+"评价");
        return convertView;
    }

    class ViewHolder{
        TextView picNum;
        TextView title;
        TextView comment;
        ImageView image;
    }
}
