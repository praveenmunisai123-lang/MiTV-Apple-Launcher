package com.example.mitvlauncher;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private final String[] names = {"Netflix", "YouTube", "Prime Video", "Hotstar", "Spotify"};
    private final String[] packages = {
        "com.netflix.ninja",
        "com.google.android.youtube.tv",
        "com.amazon.amazonvideo.livingroom",
        "in.startv.hotstar",
        "com.spotify.tv.android"
    };
    private final int[] colors = {0xFFE50914, 0xFFFF3333, 0xFF202A44, 0xFF392B88, 0xFF1DB954};
    private LinearLayout dock;
    private int selected = 0;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        buildScreen();
    }

    private GradientDrawable rounded(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        return d;
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private void buildScreen() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(48, 28, 48, 28);
        root.setBackground(new GradientDrawable(GradientDrawable.Orientation.TL_BR,
            new int[]{0xFF202B40, 0xFF101722, 0xFF080A0F}));
        setContentView(root);

        LinearLayout top = new LinearLayout(this);
        top.setGravity(Gravity.CENTER_VERTICAL);
        TextView logo = text("mi  TV", 22, Color.WHITE, true);
        top.addView(logo, new LinearLayout.LayoutParams(0, -2, 1));
        TextView clock = text("HOME", 13, 0xFFB8C0CC, true);
        top.addView(clock);
        root.addView(top);

        LinearLayout hero = new LinearLayout(this);
        hero.setOrientation(LinearLayout.VERTICAL);
        hero.setGravity(Gravity.CENTER_VERTICAL);
        hero.setPadding(28, 18, 28, 18);
        hero.setBackground(rounded(0x553E526D, 24));
        LinearLayout.LayoutParams hp = new LinearLayout.LayoutParams(-1, 0, 1.1f);
        hp.setMargins(0, 24, 0, 24);
        root.addView(hero, hp);
        hero.addView(text("YOUR ENTERTAINMENT", 12, 0xFFB9C6D8, true));
        TextView headline = text("Everything you love.\nAll in one place.", 30, Color.WHITE, true);
        headline.setLineSpacing(2, 1f);
        LinearLayout.LayoutParams htitle = new LinearLayout.LayoutParams(-2, -2);
        htitle.setMargins(0, 12, 0, 8);
        hero.addView(headline, htitle);
        hero.addView(text("Choose an app to start watching or listening.", 14, 0xFFD0D7E2, false));

        root.addView(text("YOUR APPS", 12, 0xFFB9C6D8, true));
        dock = new LinearLayout(this);
        dock.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams dp = new LinearLayout.LayoutParams(-1, 112);
        dp.setMargins(0, 12, 0, 8);
        root.addView(dock, dp);

        for (int i = 0; i < names.length; i++) {
            final int index = i;
            LinearLayout tile = new LinearLayout(this);
            tile.setOrientation(LinearLayout.VERTICAL);
            tile.setGravity(Gravity.CENTER);
            tile.setFocusable(true);
            tile.setFocusableInTouchMode(true);
            tile.setBackground(rounded(0xFF202632, 18));
            TextView symbol = text(names[i].equals("YouTube") ? "▶" :
                names[i].equals("Spotify") ? "♫" :
                names[i].equals("Netflix") ? "N" :
                names[i].equals("Prime Video") ? "prime" : "★",
                names[i].equals("Prime Video") ? 20 : 30, colors[i], true);
            tile.addView(symbol);
            TextView label = text(names[i], 11, Color.WHITE, true);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
            lp.setMargins(0, 7, 0, 0);
            tile.addView(label, lp);
            LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(0, -1, 1);
            tp.setMargins(5, 0, 5, 0);
            dock.addView(tile, tp);
            tile.setOnClickListener(v -> launch(index));
            tile.setOnFocusChangeListener((v, hasFocus) -> {
                v.setBackground(rounded(hasFocus ? 0xFF46566F : 0xFF202632, 18));
                if (hasFocus) selected = index;
            });
            tile.setOnKeyListener((v, key, event) -> {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                    (key == KeyEvent.KEYCODE_DPAD_CENTER || key == KeyEvent.KEYCODE_ENTER)) {
                    launch(index); return true;
                }
                return false;
            });
        }
        root.addView(text("Use the remote's arrow keys and OK to select an app.", 11, 0xFF8E9AAA, false));
        dock.getChildAt(0).requestFocus();
    }

    private void launch(int index) {
        Intent intent = getPackageManager().getLaunchIntentForPackage(packages[index]);
        if (intent != null) {
            try { startActivity(intent); }
            catch (Exception e) { Toast.makeText(this, "Could not open " + names[index], Toast.LENGTH_SHORT).show(); }
        } else {
            Toast.makeText(this, names[index] + " is not available on this TV.", Toast.LENGTH_LONG).show();
        }
    }
}
