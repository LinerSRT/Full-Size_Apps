package com.liner.full_sizeapps;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liner.full_sizeapps.AppUtility.AppAdapter;
import com.liner.full_sizeapps.AppUtility.AppHelper;
import com.liner.full_sizeapps.AppUtility.AppHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppAdapter appAdapter;
    private AppHelper appHelper;

    private List<AppHolder> holderList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout selectAll = findViewById(R.id.selectAll);
        TextView clearSelection = findViewById(R.id.clearAll);
        RecyclerView appRecycler = findViewById(R.id.appRecycler);
        Button saveBtn = findViewById(R.id.save);
        Button cancelBtn = findViewById(R.id.cancel);
        appHelper = new AppHelper(this);
        appAdapter = new AppAdapter(appHelper.getAppList(), this, new AppAdapter.ISelectionListener() {
            @Override
            public void onItemsSelected(List<AppHolder> selectedItems) {
                holderList = selectedItems;
            }
        });

        clearSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appAdapter.setSelected(false);
            }
        });

        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appAdapter.setSelected(true);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holderList != null) {
                    Intent broadcast = new Intent(getPackageName() + ".FULL_SIZE");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectedList", generateBroadcastList(true, holderList));
                    bundle.putSerializable("unselectedList", generateBroadcastList(false, holderList));
                    broadcast.putExtras(bundle);
                    sendBroadcast(broadcast);
                    appHelper.saveAppList(holderList);
                    holderList = null;
                    createDialog(getString(R.string.done_title), getString(R.string.saved_message), R.drawable.ic_done_black_24dp);
                } else {
                    createDialog(getString(R.string.warning_title), getString(R.string.warn_changes_message), R.drawable.ic_warning_black_24dp);
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        appRecycler.setLayoutManager(new LinearLayoutManager(this));
        appRecycler.setHasFixedSize(true);
        appRecycler.setAdapter(appAdapter);
    }

    private ArrayList<String> generateBroadcastList(boolean selected, List<AppHolder> items) {
        ArrayList<String> packages = new ArrayList<>();
        for (AppHolder item : items) {
            if (item.isSelected() == selected) {
                packages.add(item.getAppPackageName());
            }
        }
        return packages;
    }


    private void createDialog(String title, String message, int icon){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(icon);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
