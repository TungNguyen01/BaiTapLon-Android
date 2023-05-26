package com.example.baitaplon_android;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.baitaplon_android.model.CongViec;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spTinhTrang;
    private EditText txtId,txtName,txtContent, txtDate;
    private RadioGroup gCongTac;

    private RadioButton rbMotMinh,rbLamChung;
    private Button btUpdate,btDel, btBack;
    private CongViec item;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = database.getReference("congviec");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        btUpdate.setOnClickListener(this);
        btDel.setOnClickListener(this);
        btBack.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        Intent intent = getIntent();
        item = (CongViec) intent.getSerializableExtra("item");
        txtId.setText(item.getId()+"");
        txtName.setText(item.getTen());
        txtContent.setText(item.getNoiDung());
        txtDate.setText(item.getNgayHoanThanh());
        int p = 0;
        for(int i = 0 ; i <spTinhTrang.getCount();i++){
            if(spTinhTrang.getItemAtPosition(i).toString().equalsIgnoreCase(item.getTinhTrang())){
                p = i;
                break;
            }
        }
        spTinhTrang.setSelection(p);
        if (item.getCongTac().equalsIgnoreCase(rbMotMinh.getText().toString())){
            rbMotMinh.setChecked(true);
        }
        else{
            rbLamChung.setChecked(true);
        }
    }

    private void initView() {
        spTinhTrang = findViewById(R.id.spTinhTrang);
        txtId = findViewById(R.id.txtID);
        txtName = findViewById(R.id.txtName);
        txtContent = findViewById(R.id.txtContent);
        txtDate = findViewById(R.id.txtDate);
        gCongTac = findViewById(R.id.gCongTac);
        rbMotMinh = findViewById(R.id.rbMotMinh);
        rbLamChung = findViewById(R.id.rbLamchung);
        btUpdate = findViewById(R.id.btUpdate);
        btDel = findViewById(R.id.btDel);
        btBack = findViewById(R.id.btBack);
        spTinhTrang.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,
                getResources().getStringArray(R.array.tinh_trang)));
    }

    @Override
    public void onClick(View view) {

        if(view == txtDate){
            // Tương tự như trong code gốc
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date ="";
                    if(m>8){
                        date = d+"/" +(m+1)+"/"+y;
                    }else{
                        date = d+"/0" +(m+1)+"/"+y;
                    }
                    txtDate.setText(date);
                }
            },year,month,day);
            dialog.show();
        }
        if(view == btBack){
            finish();
        }
        if(view == btUpdate){
            String n = txtName.getText()+"";
            String ct = txtContent.getText()+"";
            String d = txtDate.getText()+"";
            String tinhTrang = spTinhTrang.getSelectedItem().toString();
            int congTacId = gCongTac.getCheckedRadioButtonId();
            if(!n.isEmpty() && !ct.isEmpty() && congTacId != -1){
                int id = item.getId();
                RadioButton congTacRadio = findViewById(congTacId);
                String congTacStr = congTacRadio.getText().toString();

                // Tạo đối tượng CongViec
                CongViec i = new CongViec(id,n,ct,d,tinhTrang,congTacStr);

                // Cập nhật dữ liệu lên Firebase database
                DatabaseReference myRef = database.getReference("cong_viec").child(String.valueOf(id));
                myRef.setValue(i);

                finish();
            }
        }
        if(view == btDel){
            int id = item.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Thong  bao xoa");
            builder.setMessage("Ban co chac muong xoa "+item.getTen()+" khong?");
            builder.setIcon(R.drawable.ic_delete);
            builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Xóa dữ liệu trên Firebase database
                    DatabaseReference myRef = database.getReference("cong_viec").child(String.valueOf(id));
                    myRef.removeValue();

                    finish();
                }
            });
            builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

}
