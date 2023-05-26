package com.example.baitaplon_android.db;

import com.example.baitaplon_android.model.CongViec;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class FirebaseHelper {
    private DatabaseReference databaseReference;

    public FirebaseHelper() {
        // Khởi tạo Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Tham chiếu đến node "items"
        databaseReference = firebaseDatabase.getReference("items");
    }

    // Thêm một item vào Firebase Realtime Database
    public Task<Void> addItem(CongViec item) {
        // Tạo một key mới cho item
        String key = databaseReference.push().getKey();
        // Tham chiếu đến key tương ứng với item mới
        DatabaseReference itemRef = databaseReference.child(key);
        // Ghi dữ liệu của item lên Firebase Realtime Database
        return itemRef.setValue(item);
    }

    // Cập nhật một item trong Firebase Realtime Database
    public Task<Void> updateItem(CongViec item) {
        // Tham chiếu đến key tương ứng với item cần cập nhật
        DatabaseReference itemRef = databaseReference.child(String.valueOf(item.getId()));
        // Ghi dữ liệu của item lên Firebase Realtime Database
        return itemRef.setValue(item);
    }

    // Xóa một item khỏi Firebase Realtime Database
    public Task<Void> deleteItem(int id) {
        // Tham chiếu đến key tương ứng với item cần xóa
        DatabaseReference itemRef = databaseReference.child(String.valueOf(id));
        // Xóa item khỏi Firebase Realtime Database
        return itemRef.removeValue();
    }

    // Lấy danh sách tất cả các items từ Firebase Realtime Database
    public DatabaseReference getAllItems() {
        return databaseReference;
    }

    // Lấy danh sách items theo tên từ Firebase Realtime Database
    public Query searchByTen(String key) {
        return databaseReference.orderByChild("ten").startAt(key).endAt(key + "\uf8ff");
    }

    // Lấy danh sách items theo nội dung từ Firebase Realtime Database
    public Query searchByNoiDung(String key) {
        return databaseReference.orderByChild("noiDung").startAt(key).endAt(key + "\uf8ff");
    }

    // Lấy danh sách items theo tình trạng từ Firebase Realtime Database
    public Query searchByTinhTrang(String category) {
        return databaseReference.orderByChild("tinhTrang").equalTo(category);
    }
}

