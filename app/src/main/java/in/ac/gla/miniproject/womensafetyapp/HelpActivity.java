package in.ac.gla.miniproject.womensafetyapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class HelpActivity extends Activity {
    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        browser = findViewById(R.id.myHelpWebView);

        WebSettings settings = browser.getSettings();
        settings.setJavaScriptEnabled(true);


        ProgressDialog mLoading = new ProgressDialog(HelpActivity.this);
        mLoading.setMessage("Loading...");
        mLoading.setCancelable(false);
        mLoading.show();
        String url = "file:///android_asset/index.html";
        String about = getIntent().getStringExtra("about");
        if (about != null) {
            url = "file:///android_asset/about.html";

        }

        browser.loadUrl(url);
        mLoading.dismiss();


        final ProgressDialog pd = ProgressDialog.show(HelpActivity.this, "", "Please wait, page is being processed...", true);

        browser = findViewById(R.id.myHelpWebView);

        browser.getSettings().setJavaScriptEnabled(true); // enable javascript
        browser.getSettings().setSupportZoom(false);

        browser.getSettings().setLoadWithOverviewMode(true);
        browser.getSettings().setUseWideViewPort(true);
        browser.getSettings().setBuiltInZoomControls(true);


        browser.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(HelpActivity.this, description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pd.show();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();

                String webUrl = browser.getUrl();

            }

        });


    }


    private class LoadLocalHelpPage extends
            AsyncTask<String, Void, String> {
        ProgressDialog mLoading;

        public LoadLocalHelpPage() {
            // Create progress dialog and set it up
            mLoading = new ProgressDialog(HelpActivity.this);
            mLoading.setMessage("Loading...");
            mLoading.setCancelable(false);

            // Show it
            mLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // Temporary string that will hold the registration result


            try {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        browser.loadUrl("file:///android_asset/index.html");
                    }
                });

            } catch (Exception exc) {
                // Show error instead
                Toast.makeText(HelpActivity.this, "Error: " + exc.toString(), Toast.LENGTH_SHORT).show();
            }
            // Write to log


            // Return result
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // Activity died?
            if (isFinishing()) {
                return;
            }

            // Hide progress bar
            mLoading.dismiss();

            // Display it
            System.out.println("===============REG ID=======================");
            System.out.println(result);


//            generateBarcode(result);
            System.out.println("======================================");

//            new RegisterIDOnServer().execute();
        }

    }


}
