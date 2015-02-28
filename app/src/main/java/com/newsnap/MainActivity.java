package com.newsnap;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.newsnap.adapters.MyRecyclerViewAdapter;
import com.newsnap.endpoint.NewsnapEndpoint;
import com.newsnap.items.Thread;
import com.newsnap.services.NewsnapService;
import com.newsnap.services.ServiceGenerator;

import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.Endpoint;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    private Endpoint endpoint = null;
    private NewsnapService newsnapService = null;

    @InjectView(R.id.main_recycler_view) RecyclerView recyclerView;
    private MyRecyclerViewAdapter myRecyclerViewAdapter = null;
    private RecyclerView.LayoutManager layoutManager = null;
    private com.newsnap.items.Thread[] dataForRecyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // don't forget to add this line - otherwise annotations won't work
        ButterKnife.inject(this);

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

                    if (myRecyclerViewAdapter == null) {
                        // init recycler view and provide it with the newly acquired data
                        myRecyclerViewAdapter = new MyRecyclerViewAdapter(dataForRecyclerView);
                        recyclerView.setAdapter(myRecyclerViewAdapter);
                    } else {
                        myRecyclerViewAdapter.updateList(dataForRecyclerView);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(getClass().getSimpleName(), "update failed " + error.getMessage());
                }
            });
        }
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

    @OnClick(R.id.refresh_threads_button)
    public void buttonClicked(View view) {

        updateListData();

    }

    @OnClick(R.id.new_thread_button)
    public void onClickedNewThread(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, NewThreadActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.random_thread_button)
    public void onClickedRandomThread(View view) {
        
        // get random thread id from available threads
        Random random = new Random();
        int threadIndex = random.nextInt(dataForRecyclerView.length);
        String randomThreadId = dataForRecyclerView[threadIndex].getThreadId();

        // start the thread
        Context context = view.getContext();
        Intent intent = new Intent(context, ThreadActivity.class);
        intent.putExtra(MyRecyclerViewAdapter.EXTRA_THREAD_ID, randomThreadId);
        context.startActivity(intent);
    }
}
