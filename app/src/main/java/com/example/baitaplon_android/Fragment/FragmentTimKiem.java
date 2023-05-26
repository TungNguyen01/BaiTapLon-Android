package com.example.baitaplon_android.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitaplon_android.R;
import com.example.baitaplon_android.adapter.RecycleViewAdapter;
import com.example.baitaplon_android.model.CongViec;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentTimKiem extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private Button btSearch;
    private SearchView searchView;
    private Spinner spTinhTrang;
    private RecycleViewAdapter adapter;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_tim_kiem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter = new RecycleViewAdapter();
        databaseReference = FirebaseDatabase.getInstance().getReference("CongViec");

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<CongViec> list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CongViec congViec = snapshot.getValue(CongViec.class);
                    list.add(congViec);
                }
                adapter.setList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase
            }
        };

        databaseReference.addValueEventListener(valueEventListener);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                databaseReference.orderByChild("ten").startAt(newText).endAt(newText + "\uf8ff")
                        .addListenerForSingleValueEvent(valueEventListener);
                return true;
            }
        });

        btSearch.setOnClickListener(this);

        spTinhTrang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String cate = spTinhTrang.getItemAtPosition(pos).toString();
                if (!cate.equalsIgnoreCase("all")) {
                    databaseReference.orderByChild("tinhTrang").equalTo(cate)
                            .addListenerForSingleValueEvent(valueEventListener);
                } else {
                    databaseReference.addListenerForSingleValueEvent(valueEventListener);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycleView);
        btSearch = view.findViewById(R.id.btSearch);
        searchView = view.findViewById(R.id.search);
        spTinhTrang = view.findViewById(R.id.spTinhTrang);
        String[] arr = getResources().getStringArray(R.array.tinh_trang);
        String[] arr1 = new String[arr.length + 1];
        arr1[0] = "All";
        System.arraycopy(arr, 0, arr1, 1, arr.length);
        spTinhTrang.setAdapter(new ArrayAdapter<>(getContext(), R.layout.item_spinner, arr1));
    }

    @Override
    public void onClick(View view) {
        // Xử lý sự kiện khi button btSearch được click
    }

    @Override
    public void onResume() {
        super.onResume();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<CongViec> list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CongViec congViec = snapshot.getValue(CongViec.class);
                    list.add(congViec);
                }
                adapter.setList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FragmentTimKiem", "Database error: " + databaseError.getMessage());
            }
        });
    }
}

