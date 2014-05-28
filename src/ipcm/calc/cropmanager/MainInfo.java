package ipcm.calc.cropmanager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;

/*
Just shows the html file
 */

public class MainInfo extends Activity {

    WebView webview;

    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_maininfo);

        webview = (WebView)findViewById(R.id.maininfo_webview);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(webview.getWindowToken(), 0);

        webview.loadUrl("file:///android_asset/main_info.html");// Loads the WebView's content from the assets

    }

}
