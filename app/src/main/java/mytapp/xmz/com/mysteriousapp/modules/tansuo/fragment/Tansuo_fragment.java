package mytapp.xmz.com.mysteriousapp.modules.tansuo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.activity.BaseFragment;
import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.activity.TansuoRecyclerViewItemContentActivity;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.adapter.TansuoRecyclerViewAdapter;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.bean.TansuoBaseInfo;
import mytapp.xmz.com.mysteriousapp.modules.tansuo.utils.TansuoJsonParse;
import mytapp.xmz.com.mysteriousapp.network.HttpUtil;
import mytapp.xmz.com.mysteriousapp.widget.GifView;

/**
 * Created by Administrator on 2016/11/11.
 */
public class Tansuo_fragment extends BaseFragment {

    private RecyclerView tansuo_fragment_recyclerView;
    private SwipeRefreshLayout tansuo_fragment_srl;
    private List<TansuoBaseInfo> list;
    private int page =1;
    private TansuoRecyclerViewAdapter adapter =null;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayout tansuo_fragment_hide;
    private GifView tansuo_ftagment_anim;

    @Override
    protected void findViews(View view) {
        tansuo_fragment_recyclerView = (RecyclerView) view.findViewById(R.id.tansuo_fragment_recyclerView);
        tansuo_fragment_srl = (SwipeRefreshLayout) view.findViewById(R.id.tansuo_fragment_srl);
        tansuo_fragment_hide = (LinearLayout) view.findViewById(R.id.tansuo_fragment_hide);
        tansuo_ftagment_anim = (GifView)view.findViewById(R.id.tansuo_ftagment_anim);
    }

    @Override
    protected void initEvent() {
        tansuo_fragment_srl.setColorSchemeColors(Color.GREEN,Color.BLUE,Color.YELLOW);
        tansuo_fragment_srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //tansuo_fragment_srl.setRefreshing(true);
                page = 1;
                loadData();
            }
        });
        tansuo_fragment_recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState==RecyclerView.SCROLL_STATE_IDLE){
                    if(list.size()==linearLayoutManager.findLastCompletelyVisibleItemPosition()){
                        page++;
                        loadData();
                    }


                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void init() {
        list = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());
        tansuo_fragment_recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TansuoRecyclerViewAdapter(getContext(),list);
        tansuo_fragment_recyclerView.setAdapter(adapter);

        //adapter的监听事件
        adapter.setOnItemClickListener(new TansuoRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                TansuoBaseInfo baseInfo = list.get(position);
                final int item_type = baseInfo.getItem_type();
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), TansuoRecyclerViewItemContentActivity.class);
                        intent.putExtra("articleId",list.get(position).getArticle_id());
                        intent.putExtra("Article_title",list.get(position).getArticle_title());
                        startActivity(intent);
//                        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
//                        scaleAnimation.setDuration(250);
//                        scaleAnimation.setRepeatMode(Animation.REVERSE);
//                        v.startAnimation(scaleAnimation);
//
//                        //动画监听事件
//                        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
//                            @Override
//                            public void onAnimationStart(Animation animation) {
//
//                            }
//
//                            @Override
//                            public void onAnimationEnd(Animation animation) {
//                                //TODO 跳转页面
//                                Intent intent = new Intent(getContext(), TansuoRecyclerViewItemContentActivity.class);
//                                intent.putExtra("articleId",list.get(position).getArticle_id());
//                                intent.putExtra("Article_title",list.get(position).getArticle_title());
//
//                                startActivity(intent);
//
//
//                            }
//
//                            @Override
//                            public void onAnimationRepeat(Animation animation) {
//
//                            }
//                        });
                        switch (item_type) {
                            case TansuoBaseInfo.Type.ONEIMAGETYPE:
                                TextView tansuo_rva_one_title = (TextView) v.findViewById(R.id.tansuo_rva_one_title);
                                tansuo_rva_one_title.setTextColor(Color.GRAY);
                                break;
                            case TansuoBaseInfo.Type.THREEIMAGETYPE:
                                TextView tansuo_rva_three_title = (TextView) v.findViewById(R.id.tansuo_rva_three_title);
                                tansuo_rva_three_title.setTextColor(Color.GRAY);
                                break;
                            case TansuoBaseInfo.Type.ZEROIMAGETYPE:
                                TextView tansuo_rva_zero_title = (TextView) v.findViewById(R.id.tansuo_rva_zero_title);
                                tansuo_rva_zero_title.setTextColor(Color.GRAY);
                                break;
                        }

                    }
                });

            }
        });
    }

    @Override
    protected void loadData() {
        String method = "POST";
        String url = "http://api.data.jun360.com/api/list";
        HashMap<String, String> params = new HashMap<>();
        params.put("appnavId","25");
        params.put("now_page",page+"");
        params.put("plat","android");
        params.put("appId","6");
        params.put("apiCode","3");
        params.put("imei","000000000000000");
        params.put("version","20161025");
        HttpUtil.doHttpReqeust(method, url, params, new BaseCallBack() {
            @Override
            public void success(Object data) {
                if(data!=null){
                    String jsonDate = (String) data;
                    List<TansuoBaseInfo> tempList = TansuoJsonParse.doParse(jsonDate);
                    if(page ==1 && tempList!=null){
                        list.clear();
                        tansuo_fragment_srl.setRefreshing(false);
                        list.addAll(tempList);
                        adapter.notifyDataSetChanged();
                        tansuo_ftagment_anim.setVisibility(View.GONE);
                        tansuo_fragment_hide.setVisibility(View.VISIBLE);
                        return;
                    }
                    if(tempList!=null){
                        list.addAll(tempList);
                        adapter.notifyDataSetChanged();
                        tansuo_ftagment_anim.setVisibility(View.GONE);
                        tansuo_fragment_hide.setVisibility(View.VISIBLE);
                    }
                    //取消掉脚布局
                    if(tempList.size()<20){
                        //adapter 监听事件取消脚步
                        adapter.setOnCreateFooterHolderListener(new TansuoRecyclerViewAdapter.OnCreateFooterHolderListener() {
                            @Override
                            public void onCreateFooterHolder(View footerView) {
                                ViewGroup.LayoutParams params = footerView.getLayoutParams();
                                params.height = 0;
                                footerView.setLayoutParams(params);
                                footerView.setVisibility(View.GONE);
                            }
                        });
                    }

                }

            }

            @Override
            public void failed(int errorCode, Object data) {

            }
        });


    }

    @Override
    protected int setLayoutId() {
        return R.layout.tansuo_fragment;
    }
}
