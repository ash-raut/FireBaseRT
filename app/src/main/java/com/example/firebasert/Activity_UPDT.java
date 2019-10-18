package com.example.firebasert;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

import static com.example.firebasert.Activity_UI.databaseReference;
import static com.example.firebasert.Activity_UI.uId;

public class Activity_UPDT extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;

    //Update Field.....
    EditText editTextTitleUpdate, editTextNotesUpdate;
    Button buttonUpdate, buttonDelete;

    String u_title, u_note, post_key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("TaskNote").child(uId);
        // Recycler View
        recyclerView = findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        /*//before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(Activity_UPDT.this).inflate(R.layout.custom_input_field, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_UPDT.this);


        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
*/


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View myview;

        public MyViewHolder(View itemView) {
            super(itemView);
            myview = itemView;
        }

        public void setTitle(String title) {
            TextView title_textView = myview.findViewById(R.id.rtitle);
            title_textView.setText(title);
        }

        public void setNote(String note) {
            TextView note_textView = myview.findViewById(R.id.note);
            note_textView.setText(note);
        }

        public void setdate(String date) {
            TextView date_textView = myview.findViewById(R.id.date);
            date_textView.setText(date);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Data, MyViewHolder> adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>(Data.class, R.layout.item_data, MyViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(MyViewHolder viewHolder, final Data model, final int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setNote(model.getNote());
                viewHolder.setdate(model.getDate());

                viewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(Activity_UPDT.this, "Done", Toast.LENGTH_SHORT).show();
                        post_key = getRef(position).getKey();
                        u_note = model.getNote();
                        u_title = model.getTitle();
                        updatedata();
                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);
    }


    public void updatedata() {
//before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup uviewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View udialogView = LayoutInflater.from(Activity_UPDT.this).inflate(R.layout.update_input_field, uviewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_UPDT.this);


        //setting the view of the builder to our custom view that we already inflated
        builder.setView(udialogView);

        //finally creating the alert dialog and displaying it
        //final AlertDialog ualertDialog = builder.create();
        final AlertDialog ualertDialog= builder.create();

        //Update
        editTextTitleUpdate = udialogView.findViewById(R.id.edit_Title_update);
        editTextNotesUpdate = udialogView.findViewById(R.id.edit_Notes_update);
        editTextTitleUpdate.setText(u_title);
        editTextTitleUpdate.setSelection(u_title.length());

        editTextNotesUpdate.setText(u_note);
        editTextNotesUpdate.setSelection(u_title.length());

        buttonDelete = udialogView.findViewById(R.id.btn_del);
        buttonUpdate = udialogView.findViewById(R.id.btn_Update);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                u_title = editTextTitleUpdate.getText().toString().trim();
                u_note = editTextNotesUpdate.getText().toString().trim();

                String uDate = DateFormat.getDateInstance().format(new Date());
                Data udata = new Data(u_title, u_note, uDate, post_key);
                databaseReference.child(post_key).setValue(udata);
                ualertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child(post_key).removeValue();
                ualertDialog.dismiss();
            }
        });


        ualertDialog.show();
    }

}


