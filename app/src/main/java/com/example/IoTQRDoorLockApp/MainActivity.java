package com.example.IoTQRDoorLockApp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.IoTQRDoorLockApp.adapter.UserAdapter;
import com.example.IoTQRDoorLockApp.dataType.Admin;
import com.example.IoTQRDoorLockApp.dataType.ApiResponse;
import com.example.IoTQRDoorLockApp.dataType.User;
import com.example.IoTQRDoorLockApp.network.ApiService;
import com.example.IoTQRDoorLockApp.network.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvDeviceId;
    private Button btnMyInfo;
    private RecyclerView rvUserList;
    private FloatingActionButton fabAddUser;
    private UserAdapter adapter;
    private List<User> userList = new ArrayList<>();

    private final String deviceId = "QRLock458";
    private Admin admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(true);

        tvDeviceId = findViewById(R.id.tvDeviceId);
        btnMyInfo = findViewById(R.id.btnMyInfo);
        rvUserList = findViewById(R.id.rvUserList);
        fabAddUser = findViewById(R.id.fabAddUser);

        tvDeviceId.setText("기기 ID: " + deviceId);

        btnMyInfo.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "내 정보 화면은 아직 구현되지 않았습니다.", Toast.LENGTH_SHORT).show();
        });

        ActivityResultLauncher<Intent> registerLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            if (result.getResultCode() == RESULT_OK) {
                                fetchUserListFromServer();  // 등록 후 다시 API 호출해 목록 갱신
                            }
                        });

// 2) FAB 클릭 시
        FloatingActionButton fab = findViewById(R.id.fabAddUser);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterUserActivity.class);
            registerLauncher.launch(intent);
        });

        fetchUserListFromServer();

        rvUserList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(this, userList, (user, position) -> {
            removeUser(user, position);
        },(user, pos) -> {
            Intent intent = new Intent(this, DetailUserActivity.class);
            intent.putExtra("user", user);
            intent.putExtra("device_id", deviceId);
            startActivity(intent);
        });
        rvUserList.setAdapter(adapter);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.coordinatorLayout), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

    }


    private void fetchUserListFromServer() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Admin admin = new Admin();
        admin.setadmin_id("admin");
        Call<ResponseBody> call_ = apiService.getUsersByDevices(admin);
        Call<ApiResponse<List<User>>> call = apiService.getUsersByDevice(admin);

        call.enqueue(new Callback<ApiResponse<List<User>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<User>>> call, Response<ApiResponse<List<User>>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<User>> apiResponse = response.body();
                    if(apiResponse.isSuccess()) {
                        adapter.setUsers(apiResponse.getContent());
                    } else {
                        Toast.makeText(MainActivity.this,
                                "서버 응답 오류: " + apiResponse.getApiError().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,
                            "서버 응답 오류 : " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<User>>> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "네트워크 오류: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeUser(User user, int position) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<Void> call = apiService.removeUserById(user);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    adapter.removeItem(position);
                } else {
                    Toast.makeText(MainActivity.this,
                            "서버 응답 오류: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "네트워크 오류: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}