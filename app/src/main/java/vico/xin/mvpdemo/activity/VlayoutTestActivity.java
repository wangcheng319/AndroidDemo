package vico.xin.mvpdemo.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Binder;
import android.os.Bundle;
import android.os.Messenger;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vico.xin.mvpdemo.R;
/**
 * autour: wangc
 * date: 2017/7/19 15:04
 *
 * vlayout demo 主要用于复杂页面布局
 *
 *
 * 下面是实现方式比较麻烦------> http://www.jianshu.com/p/8744d180537c
 * 使用一个adapter传入List<LayoutHelper> 在adapter中做判断处理
*/
public class VlayoutTestActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_tab);
        recyclerView = (RecyclerView) findViewById(R.id.rl);
        final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);


        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager,true);
        recyclerView.setAdapter(delegateAdapter);

        List<DelegateAdapter.Adapter> adapterList = new ArrayList<>();


        /*FloatLayoutHelper*/
        FloatLayoutHelper layoutHelper = new FloatLayoutHelper();
        layoutHelper.setAlignType(FixLayoutHelper.BOTTOM_RIGHT);
        layoutHelper.setDefaultLocation(100, 400);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(150, 150);
        adapterList.add(new SubAdapter(this, layoutHelper, 1, layoutParams));

        /*LinearLayoutHelper*/
        LinearLayoutHelper layoutHelper1 = new LinearLayoutHelper();
        layoutHelper1.setAspectRatio(2.0f);
        LinearLayoutHelper layoutHelper2 = new LinearLayoutHelper();
        layoutHelper2.setAspectRatio(4.0f);
        layoutHelper2.setDividerHeight(10);
        layoutHelper2.setMargin(10, 30, 10, 10);
        layoutHelper2.setPadding(10, 30, 10, 10);
        layoutHelper2.setBgColor(0xFFF5A623);
        adapterList.add(new DelegateAdapter.Adapter() {
            @Override
            public LayoutHelper onCreateLayoutHelper() {
                return new LinearLayoutHelper();
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewholder(LayoutInflater.from(VlayoutTestActivity.this).inflate(R.layout.activity_login, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((MyViewholder) holder).text.setText(position + 1 + "");
                ((MyViewholder) holder).btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(VlayoutTestActivity.this,"hahhah",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return 2;
            }

            class MyViewholder extends RecyclerView.ViewHolder {

                private TextView text;
                private Button btn;

                public MyViewholder(View view) {
                    super(view);
                    text = (TextView) view.findViewById(R.id.text);
                    btn = (Button) view.findViewById(R.id.btn);
                }
            }
        });

        /*GridLayoutHelper */
        /**
         设置Grid布局
         */
        final GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3, 4);
        // 在构造函数设置每行的网格个数

        // 公共属性
        gridLayoutHelper.setItemCount(15);// 设置布局里Item个数
        gridLayoutHelper.setPadding(20, 20, 20, 20);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        gridLayoutHelper.setMargin(20, 20, 20, 20);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        gridLayoutHelper.setAspectRatio(6);// 设置设置布局内每行布局的宽与高的比

        // gridLayoutHelper特有属性（下面会详细说明）
        gridLayoutHelper.setVGap(20);// 控制子元素之间的垂直间距
        gridLayoutHelper.setHGap(20);// 控制子元素之间的水平间距
        gridLayoutHelper.setSpanCount(3);// 设置每行多少个网格
        // 通过自定义SpanSizeLookup来控制某个Item的占网格个数
        gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position > 7 ) {
                    return 3;
                    // 第7个位置后,每个Item占3个网格
                }else {
                    return 2;
                    // 第7个位置前,每个Item占2个网格
                }
            }
        });

        adapterList.add(new DelegateAdapter.Adapter() {

            @Override
            public LayoutHelper onCreateLayoutHelper() {
                return gridLayoutHelper;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewholder(LayoutInflater.from(VlayoutTestActivity.this).inflate(R.layout.item, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ((MyViewholder) holder).text.setText(position  + "");
            }

            @Override
            public int getItemCount() {
                return 15;
            }

            class MyViewholder extends RecyclerView.ViewHolder {

                private TextView text;
                private Button btn;

                public MyViewholder(View view) {
                    super(view);
                    text = (TextView) view.findViewById(R.id.title);
                }
            }
        });


        adapterList.add(new SubAdapter(this, layoutHelper2, 2));

        final GridLayoutHelper helper = new GridLayoutHelper(3, 4);
        helper.setBgColor(0xFF86345A);
        adapterList.add(new SubAdapter(this, helper, 4) {
            @Override
            public void onBindViewHolder(MainViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
                holder.itemView.setLayoutParams(layoutParams);
            }
        });



        delegateAdapter.setAdapters(adapterList);

    }




    static class SubAdapter extends DelegateAdapter.Adapter<MainViewHolder> {

        private Context mContext;

        private LayoutHelper mLayoutHelper;


        private ViewGroup.LayoutParams mLayoutParams;
        private int mCount = 0;


        public SubAdapter(Context context, LayoutHelper layoutHelper, int count) {
            this(context, layoutHelper, count, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        }

        public SubAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull ViewGroup.LayoutParams layoutParams) {
            this.mContext = context;
            this.mLayoutHelper = layoutHelper;
            this.mCount = count;
            this.mLayoutParams = layoutParams;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return mLayoutHelper;
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            // only vertical
            holder.itemView.setLayoutParams(
                    new ViewGroup.LayoutParams(mLayoutParams));
        }


        @Override
        protected void onBindViewHolderWithOffset(MainViewHolder holder, int position, int offsetTotal) {
//            ((TextView) holder.itemView.findViewById(R.id.title)).setText(Integer.toString(offsetTotal));
        }

        @Override
        public int getItemCount() {
            return mCount;
        }
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {

        public static volatile int existing = 0;
        public static int createdTimes = 0;

        public MainViewHolder(View itemView) {
            super(itemView);
            createdTimes++;
            existing++;
        }

        @Override
        protected void finalize() throws Throwable {
            existing--;
            super.finalize();
        }
    }
}
