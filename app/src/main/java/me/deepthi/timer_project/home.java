package me.deepthi.timer_project;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class home extends AppCompatActivity {

    private TextView timerDisplay;
    private EditText inputHours, inputMinutes, inputSeconds;
    private Button startButton, pauseButton, resetButton;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private boolean timerRunning;
    private long initialTimeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        timerDisplay = findViewById(R.id.timerDisplay);
        inputHours = findViewById(R.id.inputHours);
        inputMinutes = findViewById(R.id.inputMinutes);
        inputSeconds = findViewById(R.id.inputSeconds);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(v -> startTimer());
        pauseButton.setOnClickListener(v -> pauseTimer());
        resetButton.setOnClickListener(v -> resetTimer());
    }

    private void startTimer() {
        if (!timerRunning) {
            int hours = getInputValue(inputHours);
            int minutes = getInputValue(inputMinutes);
            int seconds = getInputValue(inputSeconds);
            initialTimeInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000;
            timeLeftInMillis = initialTimeInMillis;

            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateTimerDisplay();
                }

                @Override
                public void onFinish() {
                    timerRunning = false;
                    playNotification();
                    Toast.makeText(home.this, "Time's up!", Toast.LENGTH_SHORT).show();
                }
            }.start();

            timerRunning = true;
        }
    }

    private void pauseTimer() {
        if (timerRunning) {
            countDownTimer.cancel();
            timerRunning = false;
        }
    }

    private void resetTimer() {
        if (timerRunning) {
            countDownTimer.cancel();
            timerRunning = false;
        }
        timeLeftInMillis = initialTimeInMillis;
        updateTimerDisplay();
    }



    private void updateTimerDisplay() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timerDisplay.setText(timeFormatted);
    }

    private void playNotification() {
        SharedPreferences sharedPreferences = getSharedPreferences("TimerPreferences", MODE_PRIVATE);
        int selectedSound = sharedPreferences.getInt("selectedSound", R.raw.notification_sound1);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, selectedSound);
        mediaPlayer.start();
    }

    private int getInputValue(EditText input) {
        String inputText = input.getText().toString();
        return inputText.isEmpty() ? 0 : Integer.parseInt(inputText);
    }
}