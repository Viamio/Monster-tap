package pl.viamio.monstertap;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Aplikacja Monster Tap – wczytuje grę z GitHub Pages.
 * Dzięki temu każda aktualizacja gry trafia do aplikacji sama, bez ponownej instalacji.
 * Zapis gry (localStorage) zostaje w telefonie.
 */
public class MainActivity extends Activity {
    private static final String GAME_URL = "https://viamio.github.io/Monster-tap/";
    private static final String GAME_HOST = "viamio.github.io";
    private WebView web;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(0xFF14102A);
        getWindow().setNavigationBarColor(0xFF120E26);
        web = new WebView(this);
        web.setBackgroundColor(0xFF14102A);
        setContentView(web);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);          // potrzebne do zapisu gry
        s.setDatabaseEnabled(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest r) {
                return !GAME_HOST.equals(r.getUrl().getHost());   // nie otwieraj obcych stron
            }
        });

        if (state != null) web.restoreState(state);
        else web.loadUrl(GAME_URL);
    }

    @Override protected void onSaveInstanceState(Bundle out) { super.onSaveInstanceState(out); web.saveState(out); }
    @Override protected void onPause() { web.onPause(); super.onPause(); }
    @Override protected void onResume() { super.onResume(); web.onResume(); }
    @Override public void onBackPressed() { if (web.canGoBack()) web.goBack(); else super.onBackPressed(); }
}
