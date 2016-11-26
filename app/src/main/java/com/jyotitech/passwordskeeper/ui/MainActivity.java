package com.jyotitech.passwordskeeper.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jyotitech.passwordskeeper.R;
import com.jyotitech.passwordskeeper.models.PasswordContent;
import com.jyotitech.passwordskeeper.utils.SharedPreferenceUtils;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private TextView emptyRecyclerView;
    private Dialog dialogAddData;

    private EditText edtDataTitle;
    private EditText edtDataContent;
    private Button btnAddData;
    private Button btnCancelAddData;

    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab = (FloatingActionButton)findViewById(R.id.fab_add_new_data);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNewDataDialog();
            }
        });

        emptyRecyclerView = (TextView) findViewById(R.id.txt_empty_recycler_view);

        emptyRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNewDataDialog();
            }
        });

        dialogAddData = new Dialog(this);
        dialogAddData.setContentView(R.layout.dialog_add_new_data);
        dialogAddData.setCancelable(false);

        edtDataTitle = (EditText)dialogAddData.findViewById(R.id.edt_data_title);
        edtDataContent = (EditText)dialogAddData.findViewById(R.id.edt_data_content);
        Button btnAddData = (Button) dialogAddData.findViewById(R.id.btn_ok);
        Button btnCancelAddData = (Button) dialogAddData.findViewById(R.id.btn_cancel);

        btnCancelAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddData.hide();
            }
        });

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtDataTitle.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter title", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edtDataContent.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter content", Toast.LENGTH_SHORT).show();
                    return;
                }

                ref.push().setValue(new PasswordContent(edtDataTitle.getText().toString(), edtDataContent.getText().toString()));
                dialogAddData.hide();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child(SharedPreferenceUtils.getInstance().getFirebaseUid());

        FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<PasswordContent, PasswordContentHolder>(PasswordContent.class, android.R.layout.two_line_list_item, PasswordContentHolder.class, ref) {
            @Override
            public void populateViewHolder(PasswordContentHolder chatMessageViewHolder, PasswordContent chatMessage, int position) {
                chatMessageViewHolder.setName(chatMessage.getTitle());
                chatMessageViewHolder.setText(chatMessage.getContent());
                if (getItemCount() > 0) {
                    emptyRecyclerView.setVisibility(View.GONE);
                }
                else {
                    emptyRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        };

        recyclerView.setAdapter(mAdapter);

        proceedForPin();
    }

    private void showAddNewDataDialog() {
        //Show add new data dialog here.
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogAddData.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogAddData.show();
        dialogAddData.getWindow().setAttributes(lp);
    }

    private void proceedForPin() {
        if(SharedPreferenceUtils.getInstance().getPin() == 0) {

        }
    }

    public static class PasswordContentHolder extends RecyclerView.ViewHolder {
        View mView;

        public PasswordContentHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name) {
            TextView field = (TextView) mView.findViewById(android.R.id.text1);
            field.setText(name);
        }

        public void setText(String text) {
            TextView field = (TextView) mView.findViewById(android.R.id.text2);
            field.setText(text);
        }
    }
}
