package com.example.anchitchawla.news;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static android.view.View.GONE;
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private static String link="http://content.guardianapis.com/search";
    final static int loader_id=1;
    private CustomAdapter adapter;
    private TextView empty_view;
    private ProgressBar progressBar;
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView=(ListView) findViewById(R.id.mylist);
        progressBar=(ProgressBar)findViewById(R.id.loading_indicator);
        adapter=new CustomAdapter(MainActivity.this,new ArrayList<News>());


        empty_view=(TextView)findViewById(R.id.empty);
        listView.setEmptyView(empty_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News news=adapter.getItem(i);
                Uri uri=Uri.parse(news.getAnInt());
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        ConnectivityManager connectivityManager =(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(isConnected)
        {
            listView.setAdapter(adapter);
            LoaderManager manager=getLoaderManager();
            manager.initLoader(loader_id,null,this);
        }
        else
        {
            empty_view.setText(R.string.no_connextion);
            progressBar.setVisibility(GONE);
        }
        }
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {


        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String topic = sharedPrefs.getString(
                getString(R.string.settings_topic_k),
                getString(R.string.settings_topic_d));
        Uri baseUri = Uri.parse(link);
        Uri.Builder uriBuilder = baseUri.buildUpon();


        uriBuilder.appendQueryParameter("api-key","31348b4c-ec34-4a51-98d4-dfc8471f2a6c");
        uriBuilder.appendQueryParameter("q",topic);
        return new loader(this,uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        adapter.clear();
        if (news  != null && !news.isEmpty()) {
            adapter.addAll(news);
        }

        empty_view.setText(R.string.no_news);

        View progress = findViewById(R.id.loading_indicator);
        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }
}
    