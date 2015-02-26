package com.newsnap;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.newsnap.endpoint.NewsnapEndpoint;
import com.newsnap.services.NewsnapService;
import com.newsnap.services.ServiceGenerator;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NewReplyActivity extends ActionBarActivity {

    String threadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reply);

        ButterKnife.inject(this);

        Intent intent = getIntent();
        threadId = intent.getStringExtra(ThreadActivity.EXTRA_THREAD_ID);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_reply, menu);
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


    @OnClick(R.id.submit_new_reply_button)
    public void onSubmitNewReply(View view) {

        String name = ((EditText) findViewById(R.id.edit_text_name)).getText().toString();
        String email = ((EditText) findViewById(R.id.edit_text_email)).getText().toString();
        String body = ((EditText) findViewById(R.id.edit_text_body)).getText().toString();

        NewsnapService newsnapService = ServiceGenerator.createService(
                NewsnapService.class, new NewsnapEndpoint());

        newsnapService.createNewReply(threadId, threadId, name, email, body, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                Log.i(getClass().getSimpleName(), "createNewReply success!" + response.getStatus());
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(getClass().getSimpleName(), "createNewReply failure!" + error.getMessage());
                Log.e(getClass().getSimpleName(), error.getCause().toString());
                Log.e(getClass().getSimpleName(), error.getResponse().getReason());
                Log.e(getClass().getSimpleName(), error.getResponse().getHeaders().toString());
            }
        });
    }
}
