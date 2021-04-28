package com.pk.arrowadmin;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pk.arrowadmin.retrofit.FirebaseApi;
import com.pk.arrowadmin.retrofit.NotificationModel;
import com.pk.arrowadmin.retrofit.RetrofitService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseApi firebaseApi;
    private EditText etTitle, etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.input_title);
        etMessage = findViewById(R.id.input_message);


        // THIS GIVES 'X00T', now we can subscribe to topic
        Log.e(TAG, "DEVICE: " + Build.DEVICE);

        initRetrofit();

        Button btnSendMessage = findViewById(R.id.send_message);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void initRetrofit() {

        Retrofit retrofit = RetrofitService.getInstance();
        firebaseApi = retrofit.create(FirebaseApi.class);


        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseApi.UNIVERSAL_TOPIC)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "subscribe to topic failed!");
                        } else
                            Log.e(TAG, "subscribe to topic successful!");
                    }
                });
    }

    private void sendMessage() {

        String title = etTitle.getText().toString();
        String message = etMessage.getText().toString();
        if (title.isEmpty() || message.isEmpty()) {
            Toast.makeText(this, "Enter both fields", Toast.LENGTH_LONG).show();
            return;
        }


        firebaseApi.sendNotification(new NotificationModel(FirebaseApi.UNIVERSAL_TOPIC, title, message)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e(TAG, "onResponse: URL: " + call.request().url());


                if (response.isSuccessful())
                    Log.e(TAG, "onResponse: call successful");
                else
                    Log.e(TAG, "onResponse: call failed! CODE: " + response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: call failed! " + t.getMessage());
            }
        });
    }
}