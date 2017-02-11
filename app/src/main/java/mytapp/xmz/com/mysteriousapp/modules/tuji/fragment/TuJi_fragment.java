package mytapp.xmz.com.mysteriousapp.modules.tuji.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.activity.BaseFragment;
import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;
import mytapp.xmz.com.mysteriousapp.modules.tuji.activity.TujiChildActivity;
import mytapp.xmz.com.mysteriousapp.modules.tuji.adapter.MyAdapter;
import mytapp.xmz.com.mysteriousapp.modules.tuji.bean.TujiInfo;
import mytapp.xmz.com.mysteriousapp.modules.tuji.dao.TujiDao;
import mytapp.xmz.com.mysteriousapp.widget.GifView;

/**
 * Created by Administrator on 2016/11/11.
 */
public class TuJi_fragment extends BaseFragment {

    private ListView listView;

    private int page = 1;
    private List<TujiInfo> tujiInfos = null;
    private MyAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private View footer;
    private GifView gifView;

    @Override
    protected void findViews(View view) {
        listView = (ListView) view.findViewById(R.id.tuji_list);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.tuji_fresh);
        gifView = (GifView) view.findViewById(R.id.tuji_gif);
    }

    @Override
    protected void initEvent() {
        footer = getActivity().getLayoutInflater().inflate(R.layout.tuji_footer_layout,listView,false);
        tujiInfos = new ArrayList<>();
        adapter = new MyAdapter(getActivity(),tujiInfos);

        listView.setAdapter(adapter);
        listView.addFooterView(footer);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadData();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Picasso picasso = Picasso.with(getContext());
                if(scrollState == SCROLL_STATE_IDLE){
                    picasso.resumeTag("listview");
                    int lastVisiblePosition = listView.getLastVisiblePosition();
                    if(lastVisiblePosition == tujiInfos.size()){
                        View childView = listView.getChildAt(listView.getChildCount()-1);
                        if(childView.getBottom()==listView.getBottom()){
                            page++;
                            loadData();
                        }
                    }
                }else{
                    picasso.pauseTag("listview");
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TujiChildActivity.class);
                intent.putExtra("id",tujiInfos.get(position).getArticle_id());
                intent.putExtra("comment",tujiInfos.get(position).getComment_sum());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void init() {


    }

    @Override
    protected void loadData() {
        TujiDao.requestTujiInfo(page, new BaseCallBack() {
            @Override
            public void success(Object data) {
                refreshLayout.setRefreshing(false);
                List<TujiInfo> loadInfo = (List<TujiInfo>) data;
                if(page == 1){
                    tujiInfos.clear();
                }

                if (listView.getFooterViewsCount() == 0)
                {
                    listView.addFooterView(footer);
                }
                if(loadInfo.size()<20){
                    Toast.makeText(getActivity(),"数据加载完毕！",Toast.LENGTH_SHORT).show();
                    listView.removeFooterView(footer);
                }
                if (loadInfo != null)
                {
                    gifView.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.VISIBLE);
                    tujiInfos.addAll(loadInfo);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                refreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), data.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.tuji_fragment;
    }
}
