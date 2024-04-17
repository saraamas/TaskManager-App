package dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserDao {
    private final FirebaseFirestore db;

    public UserDao(FirebaseFirestore firestore) {
        db = firestore;
    }

    public Task<DocumentReference> addUser(String email, String password, String phoneNumber, String username) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", password); // Note: Storing passwords in plain text is not recommended; this is for demonstration only
        userData.put("phoneNumber", phoneNumber);
        userData.put("username", username);

        return db.collection("users").add(userData);
    }
}
