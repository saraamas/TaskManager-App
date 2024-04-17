package dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import model.Tache;

public class TacheDao {
    private final CollectionReference tasksCollection;

    public TacheDao(FirebaseUser currentUser, FirebaseFirestore firestore) {
        tasksCollection = firestore.collection("users").document(currentUser.getUid()).collection("tasks");
    }

    public Task<DocumentReference> addTache(Tache tache) {
        return tasksCollection.add(tache);
    }
    public Task<Void> updateTache(String taskId, Tache tache) {
        return tasksCollection.document(taskId).set(tache);
    }

    public Task<Void> deleteTache(String taskId) {
        return tasksCollection.document(taskId).delete();
    }

    public Task<QuerySnapshot> getAllTaches() {
        return tasksCollection.get();
    }

}
