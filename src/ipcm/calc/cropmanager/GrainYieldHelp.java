package ipcm.calc.cropmanager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;

/**
 * Created by Owner on 8/12/13.
 */
public class GrainYieldHelp extends Activity {

    WebView webview;

    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_grainyieldhelp);

        webview = (WebView)findViewById(R.id.grainyield_help_webview);

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(webview.getWindowToken(), 0);

        webview.loadUrl("file:///android_asset/grainyield_help.html");// Loads the WebView's content from the assets

    }

}
