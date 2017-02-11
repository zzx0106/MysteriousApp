package mytapp.xmz.com.mysteriousapp.modules.tuijian.fragment;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mytapp.xmz.com.mysteriousapp.R;
import mytapp.xmz.com.mysteriousapp.activity.BaseFragment;
import mytapp.xmz.com.mysteriousapp.basecallback.BaseCallBack;
import mytapp.xmz.com.mysteriousapp.modules.tuijian.activity.TuijianChildActivity;
import mytapp.xmz.com.mysteriousapp.modules.tuijian.adapter.MyViewPagerAdapter;
import mytapp.xmz.com.mysteriousapp.modules.tuijian.adapter.TJ_myListAdapter;
import mytapp.xmz.com.mysteriousapp.modules.tuijian.bean.Tuijian;
import mytapp.xmz.com.mysteriousapp.modules.tuijian.parser.Tuijian_Parser;
import mytapp.xmz.com.mysteriousapp.network.HttpUtil;
import mytapp.xmz.com.mysteriousapp.widget.GifView;

/**
 * Created by Administrator on 2016/11/11.
 */
public class TuiJian_fragment extends BaseFragment {

    private ListView listView;

    // POST请求需要的 url
    private String url = "http://api.data.jun360.com/api/list";
    // post参数：供后面使用时参考
    //private String post = "appnavId=108&now_page=1&plat=android&appId=6&apiCode=3&imei=000000000000000&version=20161025";

    private List<Tuijian> lists = new ArrayList<Tuijian>();
    // 当前的加载了的页面数
    private int page = 1;
    // ListView的适配器
    private TJ_myListAdapter adapter;

    private boolean isButtom = false;
    private SwipeRefreshLayout tj_refresh;
    private View footerview;
    private View headview;

    private String[] titles;
    private int[] images;
    private LinearLayout dotLayout;
    private ViewPager viewpager;
    private int position;
    private TextView tuijian_listview_head__title;
    private GifView animation;

    private int num=0;

    @Override
    protected void findViews(View view) {
        tj_refresh = (SwipeRefreshLayout) view.findViewById(R.id.tj_refresh);
        listView = (ListView) view.findViewById(R.id.tuijian_listview);
        animation = (GifView) view.findViewById(R.id.tuijian_fragment_animation);
    }

    @Override
    protected void initEvent() {
        //TODO 点击ListView条目，进入子界面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TuijianChildActivity.class);
                intent.putExtra("article_id", lists.get(position - 1).getArticle_id());
                startActivity(intent);
            }
        });

        //TODO 设置ListView的滚动监听
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 如果到了本次的最后一条数据，则再次请求数据
                if (scrollState == SCROLL_STATE_IDLE && isButtom == true) {
                    page++;
                    initData();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    isButtom = true;
                } else {
                    isButtom = false;
                }
            }
        });

        //TODO 设置下拉刷新
        tj_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 清除掉原先的数据
                lists.clear();
                // 重新加载第一页的数据
                page = 1;
                // 加载数据
                initData();
                // 消除正在刷新的图标
                tj_refresh.setRefreshing(false);
            }
        });
    }

    @Override
    protected void init() {
        // ListView的尾部视图
        footerview = View.inflate(getContext(), R.layout.tuijian_listview_footer, null);
        // ListView的头部视图
        headview = View.inflate(getContext(), R.layout.tuijian_listview_head, null);
        headview.setVisibility(View.GONE);

        // 找到布局里面的控件
        viewpager = (ViewPager) headview.findViewById(R.id.tuijian_listview_head_viewpager);

        // 设置ViewPager一共里面包含多少个ImageView和Title
        images = new int[]{R.mipmap.tuijian_viewpager1, R.mipmap.tuijian_viewpager2, R.mipmap.tuijian_viewpager3, R.mipmap.tuijian_viewpager4, R.mipmap.tuijian_viewpager5};
        titles = new String[]{"生活在200吨巨石下面，那到底是什么？", "中央高层不敢说！彭加木失踪真相遭泄", "11部精彩的独角戏电影，11种绝对孤独！", "8件网上能买到的奇葩商品：父母也能买？！", "这些建筑背后，是满满的人类执念"};

        // 设置默认的标题
        tuijian_listview_head__title = (TextView) headview.findViewById(R.id.tuijian_listview_head__title);
        tuijian_listview_head__title.setText(titles[0]);

        // 设置小圆点
        initDot();

        // 设置ViewPager的适配器
        MyViewPagerAdapter viewadapter = new MyViewPagerAdapter(images, getContext());
        // 设置ViewPager的或滑动监听
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tuijian_listview_head__title.setText(titles[position]);

                //更改小圆点的选中状态
                for (int i = 0; i < dotLayout.getChildCount(); i++) {
                    ImageView dotView = (ImageView) dotLayout.getChildAt(i);
                    if (i == position)//当前选中的页面的位置，
                        dotView.setImageResource(R.mipmap.page_now);
                    else
                        dotView.setImageResource(R.mipmap.page);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setAdapter(viewadapter);


            new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                viewpager.setCurrentItem(num);
                            }
                        });
                        num++;
                        num=num%titles.length;
                    }
                },1000,5000);
    }


    @Override
    protected void loadData() {
        // 设置ListView的适配器
        adapter = new TJ_myListAdapter(getContext(), lists);
        listView.addFooterView(footerview);
        listView.addHeaderView(headview);
        listView.setAdapter(adapter);

        initData();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.tuijian_fragment;
    }

    //TODO 加载推荐主界面ListView的数据
    public void initData() {
        // appnavId=108&now_page=1&plat=android&appId=6&apiCode=3&imei=000000000000000&version=20161025
        //TODO 进行POST请求数据
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appnavId", "108" );
        map.put("now_page", page+"");
        map.put("plat", "android");
        map.put("appId", "6" );
        map.put("apiCode", "3" );
        map.put("imei", "000000000000000");
        map.put("version", "20161025");
        HttpUtil.doHttpReqeust("POST", url, map, new BaseCallBack() {
            @Override
            public void success(Object data) {
                // 从网络上下载来的数据
                String jsonString = (String) data;

                //TODO 解析网络上来的数据
                Tuijian_Parser tj_parser = new Tuijian_Parser();
                tj_parser.parser(jsonString);
                tj_parser.setOnCompleteParser(new Tuijian_Parser.OnCompleteParser() {
                    @Override
                    public void onCompleteData(List<Tuijian> lists_complete) {
                        //TODO 获取到了解析之后的数据，数据保存在List集合中
                        lists.addAll(lists_complete);

                        // 如果数据太少则再次添加数据
                        if (lists.size() < 7) {
                            page++;
                            initData();
                        }

                        // 隐藏动画
                        tj_refresh.setVisibility(View.VISIBLE);
                        animation.setVisibility(View.GONE);
                        headview.setVisibility(View.VISIBLE);

                        // 更新数据
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void failed(int errorCode, Object data) {
                Toast.makeText(getContext(), "网络不给力...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //TODO 设置小圆点
    private void initDot() {
        dotLayout = (LinearLayout) headview.findViewById(R.id.tuijian_listview_head_dot);

        //LayoutParams对象是用于设置控件的宽高和外边距等属性的，
        //这些属性和父容器有关，所以控件要添加到哪个容器，就应该用那种容器中的LayoutParams对象
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;

        for (int i = 0; i < images.length; i++) {
            ImageView dotView = new ImageView(getContext());
            if (i == 0) {
                dotView.setImageResource(R.mipmap.page_now);
            } else {
                dotView.setImageResource(R.mipmap.page);
            }
            dotView.setLayoutParams(layoutParams);//设置外边距
//            dotView.setPadding(10,0,10,0);//设置的是内边距

            //设置每个小圆点的id
            dotView.setId(i);
            //需要将小圆点加入线性布局中

            dotView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //setCurrentItem方法用于设置ViewPager的当前页面
                    position = v.getId();
                    //平滑过渡
//                    viewPager.setCurrentItem(position);//viewPager.setCurrentItem(position,true)

                    //参数二：设置为false，就是立即定位到新位置  true:平滑过渡
                    viewpager.setCurrentItem(position, false);
                }
            });

            dotLayout.addView(dotView);
        }
    }
}
