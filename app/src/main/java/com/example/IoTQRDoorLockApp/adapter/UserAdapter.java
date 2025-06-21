package com.example.IoTQRDoorLockApp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.IoTQRDoorLockApp.R;
import com.example.IoTQRDoorLockApp.dataType.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    /** 삭제 클릭 리스너 */
    public interface OnRemoveClickListener {
        void onRemoveClick(User user, int position);
    }

    /** 아이템 클릭 리스너 */
    public interface OnItemClickListener {
        void onItemClick(User user, int position);
    }

    private final Context context;
    private final List<User> userList;
    private final OnRemoveClickListener removeListener;
    private final OnItemClickListener itemClickListener;

    /**
     * @param context           컨텍스트
     * @param userList          사용자 리스트
     * @param removeListener    삭제 버튼 클릭 리스너
     * @param itemClickListener 아이템 전체 클릭 리스너
     */
    public UserAdapter(Context context,
                       List<User> userList,
                       OnRemoveClickListener removeListener,
                       OnItemClickListener itemClickListener) {
        this.context = context;
        this.userList = userList;
        this.removeListener = removeListener;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull UserViewHolder holder, int position) {

        User user = userList.get(position);
        holder.tvName.setText(user.getOutsider_name());
        holder.tvNote.setText(user.getNote());
        holder.tvPeriod.setText(
                user.getStart_date() + " ~ " + user.getEnd_date()
        );

        // 1) 삭제 버튼 클릭
        holder.btnRemove.setOnClickListener(v -> {
            if (removeListener != null) {
                removeListener.onRemoveClick(user, position);
            }
        });

        // 2) 아이템 전체 클릭 (세부 화면 이동 등)
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(user, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder
            extends RecyclerView.ViewHolder {
        TextView tvName, tvNote, tvPeriod;
        Button btnRemove;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName   = itemView.findViewById(R.id.tvName);
            tvNote   = itemView.findViewById(R.id.tvNote);
            tvPeriod = itemView.findViewById(R.id.tvPeriod);
            btnRemove= itemView.findViewById(R.id.btnRemove);
        }
    }

    /** 특정 위치 아이템 삭제 */
    public void removeItem(int position) {
        userList.remove(position);
        notifyItemRemoved(position);
    }

    /** 데이터 전체 교체 */
    public void setUsers(List<User> users) {
        if (users == null) return;
        userList.clear();
        userList.addAll(users);
        notifyDataSetChanged();
    }
}
