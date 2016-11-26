package com.jyotitech.passwordskeeper.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.jyotitech.passwordskeeper.R;
import com.jyotitech.passwordskeeper.utils.SharedPreferenceUtils;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.Arrays;

import io.fabric.sdk.android.Fabric;

public class LauncherActivity extends AppCompatActivity {

    private static final String TAG = LauncherActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 1;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "h3pP8WMC0EZ1zpZhFAK3fQYzL";
    private static final String TWITTER_SECRET = "6vG5KqsSfRy8Ci3Y8sQ1aWXWw5E84oY8aWdX9eMNJfpXdmwMwN";

    private Context mAppContext;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_launcher);
        mAppContext = getApplicationContext();
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            startActivity(new Intent(this, MainActivity.class));
        } else {
            // not signed in
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                    new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                            .setIsSmartLockEnabled(false)
                            .setTheme(R.style.AppThemeNoActionBar)
                            .build(),
                    RC_SIGN_IN);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                SharedPreferenceUtils.getInstance().setFirebaseUid(auth.getCurrentUser().getUid());
                startActivity(new Intent(this, MainActivity.class));
                // user is signed in!
                finish();
                return;
            }

            // Sign in cancelled
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(mAppContext, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                return;
            }

            // No network
            if (resultCode == ResultCodes.RESULT_NO_NETWORK) {
                Toast.makeText(mAppContext, "Check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
}
