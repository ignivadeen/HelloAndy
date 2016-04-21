package com.example.igniva_android_08.helloandy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Document htmlDocument;
    private String htmlPageUrl = "https://twitter.com/aditigoel0191/timelines/696590303332061184";
    WebView webViewSynopsis;
    private String htmlContentInStringFormat;
    ArrayList<TwitterPojo> mTweetsList =new ArrayList<TwitterPojo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //startActivity(new Intent(this,TestRadioButton.class));

        // Jsoup parsing
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
        // Start Webview

        webViewSynopsis=(WebView)findViewById(R.id.wv_test);
        webViewSynopsis.getSettings().setJavaScriptEnabled(true);
        webViewSynopsis.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

//        webViewSynopsis.loadData("<a class=\"twitter-grid\" href=\"https://twitter.com/aditigoel0191/timelines/696590303332061184\">Sports</a> <script async src=\"//platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>\n" +
//                "\t            <a class=\"twitter-timeline\"  href=\"https://twitter.com/aditigoel0191/timelines/696590303332061184\" data-widget-id=\"696592871298723840\">Sports</a>\n" +
//                "            <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+\"://platform.twitter.com/widgets.js\";fjs.parentNode.insertBefore(js,fjs);}}(document,\"script\",\"twitter-wjs\");</script>", "text/html", "UTF-8");

        webViewSynopsis.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                   /* progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("Please wait");
                    progressDialog.setMessage("Page is loading..");
                    progressDialog.show();*/
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Handle the error
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                   /* if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }*/
            }
        });




        // End Webview


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        // this is tets commetc
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private class JsoupAsyncTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int sizeOfTweets=0;
            try {
                htmlDocument = Jsoup.connect(htmlPageUrl).get();
                htmlContentInStringFormat = htmlDocument.title();

                // select your div with yor class
                Element divid = htmlDocument.select("div.Timeline-base").first();
                Elements divChildren = divid.children();
                Element first=divChildren.first();
                sizeOfTweets=first.childNodeSize();

                Elements allElements = first.children();
                Log.d("TAG",""+allElements);

                for (Element elem : allElements) {
                    String elementName=elem.tagName().toString();
                    Log.d("Tag name is " , elementName);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return sizeOfTweets;
        }

        @Override
        protected void onPostExecute(Integer result) {

//            for (int i=0;i<result;i++){
//                TwitterPojo twitterPojoObj=new TwitterPojo();
//
//
//                mTweetsList.add(twitterPojoObj);
//            }

        }
    }
}
