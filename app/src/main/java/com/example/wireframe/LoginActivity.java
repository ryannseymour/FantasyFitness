package com.example.wireframe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

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
    private final List<Boolean> mCheckboxStates = new ArrayList<>();

    /**
     * OnCreate method for the activity. Sets orientation of layout.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.login_page_land);
        } else {
            setContentView(R.layout.login_page);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mUsernameEditText = findViewById(R.id.username);
        mPasswordEditText = findViewById(R.id.password);

        SharedPreferences sharedPref = getSharedPreferences("fantasy_fitness_credentials", MODE_PRIVATE);
        mUsername = sharedPref.getString("username", "");
        mPassword = sharedPref.getString("password", "");

        mUsernameEditText.setText(mUsername);
        mPasswordEditText.setText(mPassword);

        Intent intent = getIntent();
        if (intent != null) {
            boolean quest1 = intent.getBooleanExtra("quest1", false);
            boolean quest2 = intent.getBooleanExtra("quest2", false);
            boolean quest3 = intent.getBooleanExtra("quest3", false);
            boolean quest4 = intent.getBooleanExtra("quest4", false);

            mCheckboxStates.add(quest1);
            mCheckboxStates.add(quest2);
            mCheckboxStates.add(quest3);
            mCheckboxStates.add(quest4);
        }
    }

    /**
     * Saves the instance in case the activity is destroyed or paused.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
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
        if (mCheckboxStates != null) {
            intent.putExtra("quest1", mCheckboxStates.get(0));
            intent.putExtra("quest2", mCheckboxStates.get(1));
            intent.putExtra("quest3", mCheckboxStates.get(2));
            intent.putExtra("quest4", mCheckboxStates.get(3));
        }
        startActivity(intent);
    }


}