package mytapp.xmz.com.mysteriousapp.modules.tansuo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoBaseInfo;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoOneImageInfo;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoThreeImageInfo;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoZeroImageInfo;


/**
 * Created by Administrator on 2016/11/12.
 */
public class TansuoRecyclerViewAdapter extends RecyclerView.Adapter<TansuoRecyclerViewAdapter.BaseViewHold> {

    private List<TansuoBaseInfo> list;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private OnCreateFooterHolderListener listener;

    public TansuoRecyclerViewAdapter(Context context, List<TansuoBaseInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BaseViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHold viewHold = null;
        LayoutInflater from = LayoutInflater.from(context);
        switch (viewType){
            case TansuoBaseInfo.Type.ONEIMAGETYPE :
                View oneImageView = from.inflate(R.layout.tansuo_fragment_rva_one,parent,false);
                viewHold = new OneImageViewHold(oneImageView);
                break;
            case TansuoBaseInfo.Type.THREEIMAGETYPE :
                View threeImageView = from.inflate(R.layout.tansuo_fragment_rva_three,parent,false);
                viewHold = new ThreeImageViewHold(threeImageView);
                break;
            case TansuoBaseInfo.Type.ZEROIMAGETYPE :
                View zeroImageView = from.inflate(R.layout.tansuo_fragment_rva_zero,parent,false);
                viewHold = new ZeroImageViewHold(zeroImageView);
                break;
            case TansuoBaseInfo.Type.FOOTERTYPE :
                View footerView = from.inflate(R.layout.tansuo_fragment_recyclerview_footer,parent,false);
                viewHold = new FooterViewHold(footerView);
               // viewHold = new BaseViewHold(footerView);
                break;
        }
        return viewHold;
    }


    public interface OnCreateFooterHolderListener{
        void onCreateFooterHolder(View footerView);
    }
    public void setOnCreateFooterHolderListener(OnCreateFooterHolderListener listener){
        this.listener = listener;
    }
    @Override
    public void onBindViewHolder(BaseViewHold holder, int position) {
        if(position == list.size()){
            FooterViewHold footerViewHold = (FooterViewHold) holder;
            View itemView = footerViewHold.itemView;
            if (listener != null) {
                listener.onCreateFooterHolder(itemView);
            }
            return;
        }
        TansuoBaseInfo baseInfo = list.get(position);
        int item_type = baseInfo.getItem_type();
        switch(item_type){
            case TansuoBaseInfo.Type.ONEIMAGETYPE :
                TansuoOneImageInfo tansuoOneImageInfo = (TansuoOneImageInfo) baseInfo;
                OneImageViewHold oneImageViewHold = (OneImageViewHold) holder;
                oneImageViewHold.tansuo_rva_one_title.setText(tansuoOneImageInfo.getArticle_title());
              //  oneImageViewHold.tansuo_rva_one_comment.setText(tansuoOneImageInfo.getComment_sum()+"评论");
                oneImageViewHold.tansuo_rva_one_time.setText(tansuoOneImageInfo.getDatetime()+"\t\t\t"+tansuoOneImageInfo.getComment_sum()+"评论");
                //Picasso.with(context).load(tansuoOneImageInfo.getImageUrl()).into(oneImageViewHold.tansuo_rva_one_image);
                Picasso.with(context).load(tansuoOneImageInfo.getImageUrl()).placeholder(R.mipmap.small_loadpic_empty_listpage).error(R.mipmap.small_loadpic_empty_listpage).into(oneImageViewHold.tansuo_rva_one_image);
                break;
            case TansuoBaseInfo.Type.THREEIMAGETYPE :
                TansuoThreeImageInfo tansuoThreeImageInfo = (TansuoThreeImageInfo) baseInfo;
                ThreeImageViewHold threeImageViewHold = (ThreeImageViewHold) holder;
                threeImageViewHold.tansuo_rva_three_title.setText(tansuoThreeImageInfo.getArticle_title());
               // threeImageViewHold.tansuo_rva_three_comment.setText(tansuoThreeImageInfo.getComment_sum()+"评论");
                threeImageViewHold.tansuo_rva_three_time.setText(tansuoThreeImageInfo.getDatetime()+"\t\t\t"+tansuoThreeImageInfo.getComment_sum()+"评论");
                Picasso.with(context).load(tansuoThreeImageInfo.getLeftImageUrl()).placeholder(R.mipmap.small_loadpic_empty_listpage).error(R.mipmap.small_loadpic_empty_listpage).into(threeImageViewHold.tansuo_rva_three_left_image);
                Picasso.with(context).load(tansuoThreeImageInfo.getRightImageUrl()).placeholder(R.mipmap.small_loadpic_empty_listpage).error(R.mipmap.small_loadpic_empty_listpage).into(threeImageViewHold.tansuo_rva_three_right_image);
                Picasso.with(context).load(tansuoThreeImageInfo.getCenterImageUrl()).placeholder(R.mipmap.small_loadpic_empty_listpage).error(R.mipmap.small_loadpic_empty_listpage).into(threeImageViewHold.tansuo_rva_three_center_image);
                break;
            case TansuoBaseInfo.Type.ZEROIMAGETYPE :
                TansuoZeroImageInfo tansuoZeroImageInfo = (TansuoZeroImageInfo) baseInfo;
                ZeroImageViewHold zeroImageViewHold = (ZeroImageViewHold) holder;
                zeroImageViewHold.tansuo_rva_zero_title.setText(tansuoZeroImageInfo.getArticle_title());
                zeroImageViewHold.tansuo_rva_zero_time.setText(tansuoZeroImageInfo.getDatetime()+"\t\t\t"+tansuoZeroImageInfo.getComment_sum()+"评论");
                break;
        }
        //监听事件
        if(onItemClickListener!=null){
            onItemClickListener.onItemClick(holder.itemView,position);
        }

    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(list.size()==position){
            return TansuoBaseInfo.Type.FOOTERTYPE;
        }else {
            return list.get(position).getItem_type();
        }
    }


    //BaseViewHold
    class BaseViewHold extends RecyclerView.ViewHolder{
        public BaseViewHold(View itemView) {
            super(itemView);
        }
    }

   //FooterViewHold
    class FooterViewHold extends BaseViewHold{
       View itemView;
        public FooterViewHold(View itemView) {
            super(itemView);
            this.itemView =itemView;
        }
    }

    //ZeroImageViewHold
    class ZeroImageViewHold extends BaseViewHold{
        TextView tansuo_rva_zero_title,tansuo_rva_zero_time;
        //View itemVIew;

        public ZeroImageViewHold(View itemView) {
            super(itemView);
           // this.itemVIew = itemView;
            tansuo_rva_zero_time= (TextView) itemView.findViewById(R.id.tansuo_rva_zero_time);
            tansuo_rva_zero_title= (TextView) itemView.findViewById(R.id.tansuo_rva_zero_title);
        }
    }
    //OneImageViewHold
    class OneImageViewHold extends BaseViewHold{
        //View itemView;
        TextView tansuo_rva_one_title,tansuo_rva_one_time;
        //tansuo_rva_one_comment;
        ImageView tansuo_rva_one_image;

        public OneImageViewHold(View itemView) {
            super(itemView);
           // this.itemView = itemView;
            //tansuo_rva_one_comment= (TextView) itemView.findViewById(R.id.tansuo_rva_one_comment);
            tansuo_rva_one_image= (ImageView) itemView.findViewById(R.id.tansuo_rva_one_image);
            tansuo_rva_one_time= (TextView) itemView.findViewById(R.id.tansuo_rva_one_time);
            tansuo_rva_one_title= (TextView) itemView.findViewById(R.id.tansuo_rva_one_title);
        }
    }
    //ThreeImageViewHold
    class ThreeImageViewHold extends BaseViewHold{
        //View itemView;
        TextView tansuo_rva_three_title,tansuo_rva_three_time;
        //tansuo_rva_three_comment;
        ImageView tansuo_rva_three_left_image,tansuo_rva_three_center_image,tansuo_rva_three_right_image;
        public ThreeImageViewHold(View itemView) {
            super(itemView);
            //this.itemView =itemView;
            //tansuo_rva_three_comment= (TextView) itemView.findViewById(R.id.tansuo_rva_three_comment);
            tansuo_rva_three_left_image= (ImageView) itemView.findViewById(R.id.tansuo_rva_three_left_image);
            tansuo_rva_three_center_image= (ImageView) itemView.findViewById(R.id.tansuo_rva_three_center_image);
            tansuo_rva_three_right_image= (ImageView) itemView.findViewById(R.id.tansuo_rva_three_right_image);
            tansuo_rva_three_time= (TextView) itemView.findViewById(R.id.tansuo_rva_three_time);
            tansuo_rva_three_title= (TextView) itemView.findViewById(R.id.tansuo_rva_three_title);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

