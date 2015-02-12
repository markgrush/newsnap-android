package com.newsnap;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.newsnap.adapters.MyRecyclerViewAdapter;
import com.newsnap.adapters.ThreadRecyclerViewAdapter;
import com.newsnap.items.*;
import com.newsnap.items.Thread;
import com.newsnap.services.NewsnapService;
import com.newsnap.services.ServiceGenerator;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ThreadActivity extends ActionBarActivity {

    @InjectView(R.id.thread_recycler_view) RecyclerView recyclerView;
    private ThreadRecyclerViewAdapter threadRecyclerViewAdapter = null;
    private RecyclerView.LayoutManager layoutManager = null;
    private ThreadPost[] dataForRecyclerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        ButterKnife.inject(this);

        Intent intent = getIntent();
        String threadId = intent.getStringExtra(MyRecyclerViewAdapter.EXTRA_THREAD_ID);
        NewsnapService newsnapService = ServiceGenerator.createService(
                NewsnapService.class, "http://newsnap.herokuapp.com");
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (newsnapService != null) {
            newsnapService.getThread(threadId, new Callback<List<ThreadPost>>() {
                @Override
                public void success(List<ThreadPost> threads, Response response) {

                    dataForRecyclerView = new ThreadPost[threads.size()];
                    threads.toArray(dataForRecyclerView);

                    if (threadRecyclerViewAdapter == null) {
                        // init recycler view and provide it with the newly acquired data
                        threadRecyclerViewAdapter = new ThreadRecyclerViewAdapter(dataForRecyclerView);
                        recyclerView.setAdapter(threadRecyclerViewAdapter);
                    } else {
                        threadRecyclerViewAdapter.updateList(dataForRecyclerView);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thread, menu);
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
}
