package com.newsnap.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.newsnap.R;
import com.newsnap.adapters.ThreadListRecyclerViewAdapter;
import com.newsnap.endpoint.NewsnapEndpoint;
import com.newsnap.items.Thread;
import com.newsnap.services.NewsnapService;
import com.newsnap.services.ServiceGenerator;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.Endpoint;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;


public class MainActivity extends AppCompatActivity {

    private Endpoint endpoint = null;
    private NewsnapService newsnapService = null;

    @Bind(R.id.main_recycler_view)
    RecyclerView recyclerView;

    private ThreadListRecyclerViewAdapter threadListRecyclerViewAdapter = null;
    private RecyclerView.LayoutManager layoutManager = null;
    private com.newsnap.items.Thread[] dataForRecyclerView = null;

    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // don't forget to add this line - otherwise annotations won't work
        ButterKnife.bind(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateListData();
            }
        });

        createList();
        createEndpoint();
        createNewsnapService();
        updateListData();
    }

    public void createEndpoint() {
        endpoint = new NewsnapEndpoint();
    }

    public void createNewsnapService() {
        newsnapService = ServiceGenerator.createService(NewsnapService.class, endpoint);
    }

    public void createList() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void updateListData() {
        if (newsnapService != null) {
            newsnapService.getThreads(new Callback<List<Thread>>() {
                @Override
                public void success(List<Thread> threads, Response response) {

                    dataForRecyclerView = new Thread[threads.size()];
                    threads.toArray(dataForRecyclerView);

                    if (threadListRecyclerViewAdapter == null) {
                        // init recycler view and provide it with the newly acquired data
                        threadListRecyclerViewAdapter = new ThreadListRecyclerViewAdapter(dataForRecyclerView);
                        recyclerView.setAdapter(threadListRecyclerViewAdapter);
                    } else {
                        threadListRecyclerViewAdapter.updateList(dataForRecyclerView);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(getClass().getSimpleName(), "update failed " + error.getMessage());
                }
            });
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // if we come back from submitting a new thread, then we want to update the list
        // to see the new thread right away, and not have to manually refresh it
        if (dataForRecyclerView != null) {
            updateListData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.new_thread_button)
    public void onClickedNewThread(final View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, NewThreadActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.random_thread_button)
    public void onClickedRandomThread(final View view) {

        Observable.just(new Random().nextInt(dataForRecyclerView.length))
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer randomNumber) {
                        return dataForRecyclerView[randomNumber].getThreadId();
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String threadId) {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, ThreadActivity.class);
                        intent.putExtra(ThreadListRecyclerViewAdapter.EXTRA_THREAD_ID, threadId);
                        context.startActivity(intent);
                    }
                });
    }
}
