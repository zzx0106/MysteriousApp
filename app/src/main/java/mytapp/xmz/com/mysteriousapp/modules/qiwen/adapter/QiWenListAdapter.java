package mytapp.xmz.com.mysteriousapp.modules.qiwen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.bean.QWItemInfo;

/**
 * Created by Administrator on 2016/11/11.
 */
public class QiWenListAdapter extends BaseAdapter {

    private final Context context;
    private final List<QWItemInfo> list;

    public QiWenListAdapter(Context context, List<QWItemInfo> list) {
        this.context = context;
        this.list = list;
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
        BaseViewHolder viewHolder = null;
        int type = list.get(position).getType();
        if (convertView == null) {
            switch (type) {
                case QWItemInfo.Type.TYPE_ONE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.qiwen_item_layout, parent, false);
                    viewHolder = new OneViewHolder(convertView);
                    convertView.setTag(viewHolder);
                    viewHolder = new OneViewHolder(convertView);
                    break;
                case QWItemInfo.Type.TYPE_THREE:
                    convertView = LayoutInflater.from(context).inflate(R.layout.qiwen_item_three_layout, parent, false);
                    viewHolder = new ThreeViewHolder(convertView);
                    convertView.setTag(viewHolder);
                    viewHolder = new ThreeViewHolder(convertView);
                    break;
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (BaseViewHolder) convertView.getTag();
        }
        QWItemInfo qwItemInfo = list.get(position);
        switch (type) {
            case QWItemInfo.Type.TYPE_ONE:
                makeDateTypeOne(viewHolder, qwItemInfo);
                break;
            case QWItemInfo.Type.TYPE_THREE:
                makeDateTypeThree(viewHolder, qwItemInfo);
                break;
        }
        return convertView;
    }

    private void makeDateTypeOne(BaseViewHolder viewHolder, QWItemInfo qwItemInfo) {
        OneViewHolder oneViewHolder = (OneViewHolder) viewHolder;
        oneViewHolder.title.setText(qwItemInfo.getArticle_title());
        oneViewHolder.content.setText("发表于" + qwItemInfo.getDatetime() + "    " + qwItemInfo.getComment_sum() + "条评论");
        JSONArray article_thumb = qwItemInfo.getArticle_thumb();
        if (article_thumb.length() > 0) {
            try {
                Picasso.with(context).load(article_thumb.getString(0)).into(oneViewHolder.pic);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void makeDateTypeThree(BaseViewHolder viewHolder, QWItemInfo qwItemInfo) {
        ThreeViewHolder threeViewHolder = (ThreeViewHolder) viewHolder;
        threeViewHolder.title.setText(qwItemInfo.getArticle_title());
        threeViewHolder.content.setText("发表于" + qwItemInfo.getDatetime() + "    " + qwItemInfo.getComment_sum() + "条评论");
        JSONArray article_thumb = qwItemInfo.getArticle_thumb();
        try {
            Picasso.with(context).load(article_thumb.getString(0)).into(threeViewHolder.left);
            Picasso.with(context).load(article_thumb.getString(1)).into(threeViewHolder.mid);
            Picasso.with(context).load(article_thumb.getString(2)).into(threeViewHolder.right);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class BaseViewHolder {
    }

    class OneViewHolder extends BaseViewHolder {
        private final TextView title;
        private final TextView content;
        private final ImageView pic;

        public OneViewHolder(View viewOne) {
            title = (TextView) viewOne.findViewById(R.id.qiwen_item_one_title);
            content = (TextView) viewOne.findViewById(R.id.qiwen_item_one_content);
            pic = (ImageView) viewOne.findViewById(R.id.qiwen_item_one_iv);

        }
    }

    class ThreeViewHolder extends BaseViewHolder {

        private final TextView title;
        private final TextView content;
        private final ImageView left;
        private final ImageView mid;
        private final ImageView right;

        public ThreeViewHolder(View viewThree) {
            title = (TextView) viewThree.findViewById(R.id.qiwen_item_three_title);
            content = (TextView) viewThree.findViewById(R.id.qiwen_item_three_content);
            left = (ImageView) viewThree.findViewById(R.id.qiwen_item_three_left_iv);
            mid = (ImageView) viewThree.findViewById(R.id.qiwen_item_three_mid_iv);
            right = (ImageView) viewThree.findViewById(R.id.qiwen_item_three_right_iv);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
