package com.pk.arrowadmin;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pk.arrowadmin.Utils.FirebaseUtils;
import com.pk.arrowadmin.retrofit.FirebaseApi;

public class MainActivity extends AppCompatActivity {

    // Vars
    private FirebaseUtils firebaseUtils;

    // UI Components
    private EditText etTitle, etMessage;
    private Spinner topicsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get views
        etTitle = findViewById(R.id.input_title);
        etMessage = findViewById(R.id.input_message);
        Button btnSendMessage = findViewById(R.id.send_message);
        topicsSpinner = findViewById(R.id.fcm_topic_spinner);

        // Firebase subscribe
        firebaseUtils = new FirebaseUtils();
        firebaseUtils.subscribeToDevice();
        firebaseUtils.subscribeToArrow();

        // onClick listener for send notification
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildMessage();
            }
        });
    }

    /**
     * Gets notification title and message from user
     * and sends message via {@link FirebaseUtils#sendMessage(Context, String, String, String)}
     */
    private void buildMessage() {
        // Get title and message

        String title = etTitle.getText().toString();
        String message = etMessage.getText().toString();
        if (title.isEmpty() || message.isEmpty()) {
            Toast.makeText(this, "Enter both fields", Toast.LENGTH_LONG).show();
            return;
        }

        // Get selected topic
        String topic;
        if (topicsSpinner.getSelectedItemPosition() == 0)
            topic = FirebaseApi.ARROW_TOPIC;
        else
            topic = FirebaseApi.DEVICE_TOPIC;

        // Send message
        firebaseUtils.sendMessage(this, topic, title, message);

    }
}
