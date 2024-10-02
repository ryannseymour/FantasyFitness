package com.example.wireframe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The login activity for the app. This activity will be the first thing that the user sees when
 * they open the app. It will allow them to log in to the app, and will save their username and
 * password to the shared preferences.
 */
public class LoginActivity extends AppCompatActivity {

    private String mUsername;
    private EditText mUsernameEditText;
    private String mPassword;
    private EditText mPasswordEditText;

    /**
     * OnCreate method for the activity.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsernameEditText = findViewById(R.id.username);
        mPasswordEditText = findViewById(R.id.password);
    }

    /**
     * Saves the instance in case the activity is destroyed or paused.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        mUsername = mUsernameEditText.getText().toString();
        mPassword = mPasswordEditText.getText().toString();
        savedInstanceState.putString("username", mUsername);
        savedInstanceState.putString("password", mPassword);
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Restores the instance state if the activity is destroyed or paused.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mUsername = savedInstanceState.getString("username");
        mPassword = savedInstanceState.getString("password");
        mUsernameEditText.setText(mUsername);
        mPasswordEditText.setText(mPassword);
    }

    /**
     * Navigator for the Home View activity. This method will save the username and password to the
     * shared preferences, and will then navigate to the Home View activity.
     *
     * Called by the Login Button on the login activity screen.
     * @param view The view that called this method.
     */
    public void goToHome(android.view.View view) {
        // Save the username and password to the shared preferences.
        SharedPreferences sharedPref = getSharedPreferences("fantasy_fitness_credentials",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        mPassword = mPasswordEditText.getText().toString();
        mUsername = mUsernameEditText.getText().toString();

        String lastPassword = sharedPref.getString(mUsername, "");

        if (verifyCredentials(editor, lastPassword))
        {
            login();
        }
    }

    private boolean verifyCredentials(SharedPreferences.Editor editor, String lastPassword)
    {
        if (!lastPassword.isEmpty() && lastPassword.equals(mPassword)) {
            // Returning user. Save the remember me checkbox and username.
            editor.putString("username", mUsername);
            editor.apply();
        } else if (lastPassword.isEmpty() && !mPassword.isEmpty() && !mUsername.isEmpty()) {
            // First entry from user, so save it.
            editor.putString(mUsername, mPassword);
            editor.apply();
        }
        else if (mPassword.isEmpty() || mUsername.isEmpty()) {
            Toast.makeText(this, this.getString(R.string.empty_username_or_password), android.widget.Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            Toast.makeText(this, this.getString(R.string.wrong_username_or_password), android.widget.Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void login() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }


}