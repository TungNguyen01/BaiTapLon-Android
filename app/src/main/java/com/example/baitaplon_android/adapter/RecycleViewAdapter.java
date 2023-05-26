package com.example.baitaplon_android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baitaplon_android.R;
import com.example.baitaplon_android.model.CongViec;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder> {

    private List<CongViec> list;

    private ItemListener itemListener;

    public RecycleViewAdapter(){
        this.list = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<CongViec> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public CongViec getItem(int pos){
        return list.get(pos);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        CongViec item = list.get(position);
        holder.tvTen.setText(item.getTen());
        holder.tvNoiDung.setText(item.getNoiDung());
        holder.tvNgay.setText(item.getNgayHoanThanh());
        holder.tvTinhTrang.setText(item.getTinhTrang());
        holder.tvCongTac.setText(item.getCongTac());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTen, tvNoiDung, tvNgay, tvTinhTrang, tvCongTac;

        public HomeViewHolder(@NonNull View view) {
            super(view);
            tvTen = view.findViewById(R.id.tvTen);
            tvNoiDung = view.findViewById(R.id.tvNoiDung);
            tvNgay = view.findViewById(R.id.tvDate);
            tvTinhTrang = view.findViewById(R.id.tvTinhTrang);
            tvCongTac = view.findViewById(R.id.tvCongTac);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener != null){
                itemListener.onItemClick(view,getAdapterPosition());
            }
        }
    }

    public interface ItemListener{
        void onItemClick(View view,int pos);
    }
}

