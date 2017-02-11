package mytapp.xmz.com.mysteriousapp.modules.video.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.activity.BaseFragment;
import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;
import mytapp.xmz.com.mysteriousapp.modules.video.adapter.Video_Adapter;
import mytapp.xmz.com.mysteriousapp.modules.video.bean.VideoInfo;
import mytapp.xmz.com.mysteriousapp.modules.video.dao.Videodao;
import mytapp.xmz.com.mysteriousapp.widget.GifView;

/**
 * Created by Administrator on 2016/11/11.
 */
public class Video_fragment extends BaseFragment {

    private ListView listView;
    private List<VideoInfo> list;
    private int now_page = 1;
    private Video_Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View footer;
    private GifView gifView;
    @Override
    protected void findViews(View view) {
        footer = LayoutInflater.from(getContext()).inflate(R.layout.footer_layout, listView, false);
        gifView = (GifView) view.findViewById(R.id.video_gif);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.video_fragment_swf);
        listView = (ListView) view.findViewById(R.id.video_fragment_listview);

    }

    @Override
    protected void initEvent() {
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                now_page=1;
                loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void init() {
        list = new ArrayList<>();
        adapter = new Video_Adapter(getContext(), list);
        listView.addFooterView(footer);
        listView.setAdapter(adapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE){
                    int lastVisiblePosition = listView.getLastVisiblePosition();
                    if(lastVisiblePosition==list.size()){
                        View childAt = listView.getChildAt(listView.getChildCount() - 1);
                        if(childAt.getBottom()==listView.getBottom()){
                            now_page++;
                            loadData();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }
    @Override
    protected void loadData() {
        Videodao.getVideoJson(String.valueOf(now_page), new BaseCallBack() {
            @Override
            public void success(Object data) {
                List<VideoInfo> tempList = (List<VideoInfo>) data;
                if(now_page==1){
                    list.clear();
                }
                if(listView.getFooterViewsCount()==0){
                    listView.addFooterView(footer);
                }
                if(tempList.size()<10){
                    listView.removeFooterView(footer);
                }
                if(tempList!=null){
                    listView.setVisibility(View.VISIBLE);
                    gifView.setVisibility(View.GONE);
                    list.addAll(tempList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(getContext(),"加载失败："+errorCode+":"+data,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.video_fragment;
    }
}
