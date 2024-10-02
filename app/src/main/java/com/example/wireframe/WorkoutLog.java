package com.example.wireframe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

public class WorkoutLog extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner mNewWorkoutSelector;
    Boolean mQuest1, mQuest2, mQuest3, mQuest4;
    String mSelectedItem;

    /**
     * Sets up the view
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.new_workout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.new_workout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        configureSpinner();
        // Set the values for the quests
        getCheckboxStates();
    }

    /**
     * Setup the selection spinner for the workouts
     */
    private void configureSpinner() {
        // Find the selection spinner for the workouts
        mNewWorkoutSelector = findViewById(R.id.workout_selection);

        // Set the values for the selection spinner as the items in workouts array
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.workouts,
                        android.R.layout.simple_spinner_item);

        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mNewWorkoutSelector.setAdapter(staticAdapter);
        mNewWorkoutSelector.setOnItemSelectedListener(this);
    }

    /**
     * Add new workouts button click event handler
     * @param view
     */
    public void onAddNewWorkouts(android.view.View view){
        setResults();
        setCheckboxStates();
    }

    private void setResults() {
        switch(mSelectedItem) {
            case("Go for a run"):
                mQuest1 = true;
                break;
            case ("10 push ups"):
                mQuest2 = true;
                break;
            case("5 minutes of meditation"):
                mQuest3 = true;
                break;
            case("10 minutes of stretching"):
                mQuest4 = true;
                break;
        }
    }

    /**
     * Get the states for the checkboxes
     */
    private void getCheckboxStates() {
        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            mQuest1 = bundle.getBoolean("quest1");
            mQuest2 = bundle.getBoolean("quest2");
            mQuest3 = bundle.getBoolean("quest3");
            mQuest4 = bundle.getBoolean("quest4");
        }
    }

    /**
     * Set the states for the checkboxes
     */
    private void setCheckboxStates() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("quest1", mQuest1);
        intent.putExtra("quest2", mQuest2);
        intent.putExtra("quest3", mQuest3);
        intent.putExtra("quest4", mQuest4);

        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mSelectedItem = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        mSelectedItem = "";
    }
}
