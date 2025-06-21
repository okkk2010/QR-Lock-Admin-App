package com.example.IoTQRDoorLockApp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.IoTQRDoorLockApp.dataType.ApiResponse;
import com.example.IoTQRDoorLockApp.dataType.User;
import com.example.IoTQRDoorLockApp.network.ApiService;
import com.example.IoTQRDoorLockApp.network.RetrofitClient;

import java.time.LocalDate;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserActivity extends AppCompatActivity {
    Spinner spinnerDevice;
    EditText etName, etNote, etStartDate, etEndDate;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // 뷰 바인딩
        spinnerDevice = findViewById(R.id.spinnerDevice);
        etName        = findViewById(R.id.etName);
        etNote        = findViewById(R.id.etNote);
        etStartDate   = findViewById(R.id.etStartDate);
        etEndDate     = findViewById(R.id.etEndDate);
        btnRegister   = findViewById(R.id.btnRegister);

        // (1) Spinner 초기화
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                Collections.singletonList("QRLock458")  // 실제 목록으로 교체 가능
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDevice.setAdapter(adapter);

        // (2) DatePickerDialog 설정
        View.OnClickListener pickDate = v -> {
            EditText et = (EditText)v;
            new DatePickerDialog(this, (dp, y, m, d) ->
                    et.setText(String.format("%04d.%02d.%02d", y, m+1, d)),
                    LocalDate.now().getYear(),
                    LocalDate.now().getMonthValue()-1,
                    LocalDate.now().getDayOfMonth()
            ).show();
        };
        etStartDate.setOnClickListener(pickDate);
        etEndDate.setOnClickListener(pickDate);

        // (3) 등록 버튼 클릭
        btnRegister.setOnClickListener(v -> doRegister());

        // (선택) 툴바 Up 버튼
//        Toolbar tb = findViewById(R.id.toolbar);
//        if (tb != null) {
//            setSupportActionBar(tb);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doRegister() {
        // 입력 값 수집
        String deviceId = spinnerDevice.getSelectedItem().toString();
        String name     = etName.getText().toString().trim();
        String note     = etNote.getText().toString().trim();
        String adminId = "admin";
        String start    = etStartDate.getText().toString();
        String end      = etEndDate.getText().toString();

        // TODO: 입력 검증

        // DTO
        User user = new User();
        user.setDevice_id(deviceId); user.setOutsider_name(name); user.setNote(note); user.setStart_date(start); user.setEnd_date(end); user.setAdmin_id(adminId);
        ApiService api = RetrofitClient.getInstance().create(ApiService.class);
        api.addOutside(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    setResult(RESULT_OK);      // 목록 갱신 신호
                    finish();                  // MainActivity로 돌아감
                } else {
                    Toast.makeText(RegisterUserActivity.this,
                            "서버 응답 오류: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterUserActivity.this,
                        "네트워크 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

