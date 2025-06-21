package com.example.IoTQRDoorLockApp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.IoTQRDoorLockApp.R;
import com.example.IoTQRDoorLockApp.dataType.VisitLog;  // 모델 클래스

import java.util.ArrayList;
import java.util.List;

public class VisitLogAdapter
        extends RecyclerView.Adapter<VisitLogAdapter.VH> {

    private final List<VisitLog> logs = new ArrayList<>();

    /** 외부에서 로그 리스트를 갱신할 때 호출 */
    public void setLogs(List<VisitLog> newLogs) {
        logs.clear();
        if (newLogs != null) logs.addAll(newLogs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_visit_log, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(logs.get(position));
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        private final TextView tvVisitTime;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvVisitTime = itemView.findViewById(R.id.tvVisitTime);
        }

        public void bind(VisitLog log) {
            // 모델에 따라 메서드명(getVisitTime or getAccessTime) 조정하세요
            String raw = log.getVisit_time();
            String formatted;
            if (raw != null && raw.length() >= 19) {
                formatted = raw
                        .substring(0,10).replace('-','.')
                        + "  "
                        + raw.substring(11,19);
            } else {
                formatted = (raw != null ? raw : "");
            }
            tvVisitTime.setText(formatted);
        }
    }
}
