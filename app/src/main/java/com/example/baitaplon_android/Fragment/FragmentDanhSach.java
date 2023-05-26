package com.example.baitaplon_android.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitaplon_android.R;
import com.example.baitaplon_android.UpdateDeleteActivity;

import com.example.baitaplon_android.adapter.RecycleViewAdapter;
import com.example.baitaplon_android.db.FirebaseHelper;
import com.example.baitaplon_android.model.CongViec;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentDanhSach extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    private RecycleViewAdapter adapter;
    private DatabaseReference databaseReference;
    private List<CongViec> congViecList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_danh_sach, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        adapter = new RecycleViewAdapter();
        databaseReference = FirebaseDatabase.getInstance().getReference("congviec");
        congViecList = new ArrayList<>(); // Khởi tạo danh sách công việc trống
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
        adapter.setList(congViecList); // Gán danh sách công việc cho adapter
    }

    @Override
    public void onItemClick(View view, int pos) {
        CongViec item = adapter.getItem(pos);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                congViecList.clear(); // Xóa danh sách công việc hiện tại
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CongViec congviec = ds.getValue(CongViec.class);
                    congViecList.add(congviec); // Thêm công việc vào danh sách
                }
                adapter.notifyDataSetChanged(); // Cập nhật dữ liệu trong adapter
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FragmentDanhSach", "onCancelled: " + databaseError.getMessage());
            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }
}
