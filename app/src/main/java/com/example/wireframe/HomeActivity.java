package com.example.wireframe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    CheckBox mQuest1, mQuest2, mQuest3, mQuest4;
    private ActivityResultLauncher<Intent> mActivityLauncher;

    /**
     * OnCreate method for the activity. Sets orientation of layout and any saved values.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mQuest1 = findViewById(R.id.quest1);
        mQuest2 = findViewById(R.id.quest2);
        mQuest3 = findViewById(R.id.quest3);
        mQuest4 = findViewById(R.id.quest4);

        mActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        boolean checkBox1State = data.getBooleanExtra("quest1", false);
                        boolean checkBox2State = data.getBooleanExtra("quest2", false);
                        boolean checkBox3State = data.getBooleanExtra("quest3", false);
                        boolean checkBox4State = data.getBooleanExtra("quest4", false);

                        mQuest1.setChecked(checkBox1State);
                        mQuest2.setChecked(checkBox2State);
                        mQuest3.setChecked(checkBox3State);
                        mQuest4.setChecked(checkBox4State);
                    }
                }
            }
        );

        Intent intent = getIntent();
        if (intent != null) {
            mQuest1.setChecked(intent.getBooleanExtra("quest1", false));
            mQuest2.setChecked(intent.getBooleanExtra("quest2", false));
            mQuest3.setChecked(intent.getBooleanExtra("quest3", false));
            mQuest4.setChecked(intent.getBooleanExtra("quest4", false));
        }

        if (savedInstanceState != null) {
            mQuest1.setChecked(savedInstanceState.getBoolean("quest1", false));
            mQuest2.setChecked(savedInstanceState.getBoolean("quest2", false));
            mQuest3.setChecked(savedInstanceState.getBoolean("quest3", false));
            mQuest4.setChecked(savedInstanceState.getBoolean("quest4", false));
        }

        setTitle();
    }

    /**
     * Customizes the title of the page with the user's username
     */
    public void setTitle() {
        SharedPreferences sharedPreferences =
                getSharedPreferences("fantasy_fitness_credentials", MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "");
        String title = username + "'s Quests";

        TextView titleTextView = findViewById(R.id.title);
        titleTextView.setText(title);
    }

    /**
     * Log out button click event handler
     * @param view android view layout
     */
    public void onLogoutClick(android.view.View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        saveStateAndLaunch(intent);
    }

    /**
     * New Workout Button click event handler.
     * @param view android view layout
     */
    public void onClickNewWorkout(android.view.View view) {
        Intent intent = new Intent(this, WorkoutLog.class);
        saveStateAndLaunch(intent);
    }

    /**
     * Saves the states of the checkboxes and launches new activity
     * @param intent Intent to launch
     */
    private void saveStateAndLaunch(Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("quest1", mQuest1.isChecked());
        bundle.putBoolean("quest2", mQuest2.isChecked());
        bundle.putBoolean("quest3", mQuest3.isChecked());
        bundle.putBoolean("quest4", mQuest4.isChecked());

        intent.putExtras(bundle);
        mActivityLauncher.launch(intent);
    }




}


