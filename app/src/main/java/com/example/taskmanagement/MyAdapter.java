package com.example.taskmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;

import model.Tache;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private LinkedList<Tache> taches;
    private Context context;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    NoteListener noteListener;


    public MyAdapter(LinkedList<Tache> taches, Context context) {
        this.taches = new LinkedList<Tache>();
        this.taches.addAll(taches);
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout, parent, false);
        return new MyViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Tache currentTache = taches.get(position);
        holder.title.setText(currentTache.getTitle());
        holder.deadline.setText(currentTache.getDeadline());

    }



    @Override
    public int getItemCount() {
        return taches.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView deadline;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            title = itemLayoutView.findViewById(R.id.title);
            deadline = itemLayoutView.findViewById(R.id.deadline);

        }
    }


    interface NoteListener{
        public void handleCheckChanged(boolean isChecked, DocumentSnapshot snapshot);

    }
}
