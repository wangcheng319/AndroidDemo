package vico.xin.mvpdemo.activity;

import android.graphics.Rect;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vico.xin.mvpdemo.R;
import vico.xin.mvpdemo.adapter.RecyclerViewAdapter;

/**
 * CoordinatorLayout基本使用
 */
public class CoordinatorLayoutTestActivity extends AppCompatActivity {
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbar;
    RecyclerView recyclerView;
    List<String> datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_test);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar.setTitle("Collapsing");

        InitRecyclerView();
    }

    private void InitRecyclerView() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        initData();
        final RecyclerViewAdapter adapter=new RecyclerViewAdapter(datas);
        recyclerView.setAdapter(adapter);

        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v) {
                Snackbar.make(v, "Click Item "+v.getTag(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;
        public SpacesItemDecoration(int space) {
            this.space=space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=space;
            }
        }
    }

    private void initData() {
        datas = new ArrayList<String>();
        for(int i =0;i<17;i++){
            datas.add("item "+i);
        }
    }
}
