package com.example.IoTQRDoorLockApp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.IoTQRDoorLockApp.adapter.VisitLogAdapter;
import com.example.IoTQRDoorLockApp.dataType.ApiResponse;
import com.example.IoTQRDoorLockApp.dataType.User;
import com.example.IoTQRDoorLockApp.dataType.VisitLog;
import com.example.IoTQRDoorLockApp.network.ApiService;
import com.example.IoTQRDoorLockApp.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailUserActivity extends AppCompatActivity {
    TextView tvDeviceId, tvDetailName, tvDetailNote, tvDetailPeriod, tvDetailCode;
    ImageButton btnCopyCode;
    RecyclerView rvAccessLog;
    VisitLogAdapter logAdapter; // 별도 어댑터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        // 1) 뷰 바인딩
        tvDeviceId      = findViewById(R.id.tvDeviceIdDetail);
        tvDetailName    = findViewById(R.id.tvDetailName);
        tvDetailNote    = findViewById(R.id.tvDetailNote);
        tvDetailPeriod  = findViewById(R.id.tvDetailPeriod);
        tvDetailCode    = findViewById(R.id.tvDetailCode);
        btnCopyCode     = findViewById(R.id.btnCopyCode);
        rvAccessLog     = findViewById(R.id.rvAccessLog);

        // 2) User 객체와 Device ID 받기
        Intent intent = getIntent();
        User user     = (User) intent.getSerializableExtra("user");
        String deviceId = intent.getStringExtra("device_id");

        tvDeviceId.setText(deviceId);
        tvDetailName.setText(user.getOutsider_name());
        tvDetailNote.setText(user.getNote());
        tvDetailPeriod.setText(
                user.getStart_date().substring(0,10)
                        + " ~ "
                        + user.getEnd_date().substring(0,10)
        );
        tvDetailCode.setText(user.getAuthentication_code());

        // 3) 코드 복사 기능
        btnCopyCode.setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager)
                    getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(
                    "authCode", tvDetailCode.getText());
            cm.setPrimaryClip(clip);
            Toast.makeText(this, "코드 복사됨", Toast.LENGTH_SHORT).show();
        });

        // 4) RecyclerView 초기화 (출입 기록)
        logAdapter = new VisitLogAdapter();
        rvAccessLog.setLayoutManager(new LinearLayoutManager(this));
        rvAccessLog.setAdapter(logAdapter);

        // 5) 서버에서 출입 로그 불러오기
        ApiService api = RetrofitClient.getInstance().create(ApiService.class);
        api.getVisitLog(user)
                .enqueue(new Callback<ApiResponse<List<VisitLog>>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<List<VisitLog>>> call,
                                           Response<ApiResponse<List<VisitLog>>> response) {
                        if (response.isSuccessful() && response.body().isSuccess()) {
                            logAdapter.setLogs(response.body().getContent());
                        } else {
                            Toast.makeText(DetailUserActivity.this,
                                    "서버 응답 오류 : " + response.code(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    public void onFailure(Call<ApiResponse<List<VisitLog>>> call, Throwable t) {
                        Toast.makeText(DetailUserActivity.this,
                                "네트워크 오류: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
