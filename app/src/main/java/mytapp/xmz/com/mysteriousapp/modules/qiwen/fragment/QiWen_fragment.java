package mytapp.xmz.com.mysteriousapp.modules.qiwen.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.activity.BaseFragment;
import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.activity.QiWenChildActivity;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.adapter.QiWenListAdapter;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.bean.QWItemInfo;
import mytapp.xmz.com.mysteriousapp.modules.qiwen.dao.QWdao;
import mytapp.xmz.com.mysteriousapp.widget.GifView;

/**
 * Created by Administrator on 2016/11/11.
 */
public class QiWen_fragment extends BaseFragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private List<QWItemInfo> list;
    private int page = 1;
    private QiWenListAdapter adapter;
    private View footer;
    private GifView qiwen_gifview;

    @Override
    protected void findViews(View view) {

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.qiwen_fragment_swf);
        listView = (ListView) view.findViewById(R.id.qiwen_fragment_listview);
        list = new ArrayList<>();
        qiwen_gifview = (GifView) view.findViewById(R.id.qiwen_gifview);
    }

    @Override
    protected void initEvent() {

        footer = LayoutInflater.from(getContext()).inflate(R.layout.footer_layout, listView, false);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        adapter = new QiWenListAdapter(getContext(), list);
        listView.addFooterView(footer);
        listView.setAdapter(adapter);
    }

    @Override
    protected void init() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), QiWenChildActivity.class);
                intent.putExtra("app_nav_id", list.get(position).getApp_nav_id());
                intent.putExtra("article_id", list.get(position).getArticle_id());
                intent.putExtra("article_type", list.get(position).getArticle_type());
                TextView tit = (TextView) view.findViewById(R.id.qiwen_item_one_title);
                if (tit != null) {
                    tit.setTextColor(Color.GRAY);
                }
                try {
                    String article_thumb_uri = list.get(position).getArticle_thumb().getString(0);
                    if (article_thumb_uri != null) {
                        intent.putExtra("article_thumb", article_thumb_uri);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = listView.getLastVisiblePosition();
                    if (lastVisiblePosition == list.size()) {
                        View childAt = listView.getChildAt(listView.getChildCount() - 1);
                        if (childAt.getBottom() == listView.getBottom()) {
                            page++;
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

        QWdao.getQWIJson(String.valueOf(page), new BaseCallBack() {
            @Override
            public void success(Object data) {
                List<QWItemInfo> templist = (List<QWItemInfo>) data;
                if (page == 1) {
                    list.clear();
                }
                if (listView.getFooterViewsCount() == 0) {
                    listView.addFooterView(footer);
                }
                if (templist.size() < 10) {
                    listView.removeFooterView(footer);
                }
                if (templist != null) {
                    qiwen_gifview.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                    list.addAll(templist);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(getContext(), "数据获取失败" + errorCode + ":" + data, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected int setLayoutId() {
        return R.layout.qiwen_fragment;
    }
}
