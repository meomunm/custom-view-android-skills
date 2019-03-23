package techkids.vn.customviewskills;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import techkids.vn.customviewskills.adapters.RecyclerListCustomAdapter;
import techkids.vn.customviewskills.models.ModelListCustom;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.toString();
    private RecyclerView rlListCustom;
    private RecyclerListCustomAdapter recyclerListCustomAdapter;
    private List<ModelListCustom> listCustoms;
    private RelativeLayout rlRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        listener();
    }

    private void init() {
        rlListCustom = findViewById(R.id.rv_listCustomView);
        rlRoot = findViewById(R.id.rl_root_activity_main);
        listCustoms = getListCustoms();

        recyclerListCustomAdapter = new RecyclerListCustomAdapter(this, listCustoms);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rlListCustom.setLayoutManager(layoutManager);
        rlListCustom.setAdapter(recyclerListCustomAdapter);
    }

    private List<ModelListCustom> getListCustoms() {
        List<ModelListCustom> e = new ArrayList<>();
        e.add(new ModelListCustom("Page Indicator View", "Custom Page Indicator View Demo"));
        e.add(new ModelListCustom("Custom Chart UI", "Custom Chart UI View Demo"));
        return e;
    }

    private void listener() {
        recyclerListCustomAdapter.setOnItemClickedListener(new RecyclerListCustomAdapter.OnItemClickedListener() {
            @Override
            public void onItemClick(String title) {
                switch (title) {
                    case "Page Indicator View":
                        Intent intent = new Intent(MainActivity.this, ScrollPagerActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                        break;
                    case "Custom Chart UI":
                        Toast.makeText(MainActivity.this, title, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                Log.d(TAG, "onItemClick: clicked item recycler view");
            }
        });
    }
}
