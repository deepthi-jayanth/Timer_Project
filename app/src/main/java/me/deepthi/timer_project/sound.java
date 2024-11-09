package me.deepthi.timer_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class sound extends AppCompatActivity {

    private RadioGroup soundOptions;
    private SharedPreferences sharedPreferences;
    private Button backButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sound);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        soundOptions = findViewById(R.id.soundOptions);
        backButton = findViewById(R.id.backButton);
        sharedPreferences = getSharedPreferences("TimerPreferences", MODE_PRIVATE);

        // Load saved sound option
        int savedSoundOption = sharedPreferences.getInt("selectedSound", R.raw.notification_sound1);
        if (savedSoundOption == R.raw.notification_sound1) {
            soundOptions.check(R.id.soundOption1);
        } else if (savedSoundOption == R.raw.notification_sound2) {
            soundOptions.check(R.id.soundOption2);
        } else if (savedSoundOption == R.raw.notification_sound3) {
            soundOptions.check(R.id.soundOption3);
        }

        soundOptions.setOnCheckedChangeListener((group, checkedId) -> {
            int selectedSound;
            if (checkedId == R.id.soundOption1) {
                selectedSound = R.raw.notification_sound1;
            } else if (checkedId == R.id.soundOption2) {
                selectedSound = R.raw.notification_sound2;
            } else {
                selectedSound = R.raw.notification_sound3;
            }
            sharedPreferences.edit().putInt("selectedSound", selectedSound).apply();
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(sound.this, home.class);
            startActivity(intent);
        });
    }
}