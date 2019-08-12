package uas.rifdah.crudbiodataagustus;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uas.rifdah.crudbiodataagustus.model.DataBiodataItem;
import uas.rifdah.crudbiodataagustus.model.ResponseGetBiodata;
import uas.rifdah.crudbiodataagustus.network.ApiClient;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvBiodata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });

        actionGetBiodata();

        rvBiodata = findViewById(R.id.rv_biodata);
    }

    private void actionGetBiodata(){
        ApiClient.service.actionGetBiodata().enqueue(new Callback<ResponseGetBiodata>() {
            @Override
            public void onResponse(Call<ResponseGetBiodata> call, Response<ResponseGetBiodata> response) {
                if (response.isSuccessful()){
                    String pesan = response.body().getPesan();
                    int status = response.body().getStatus();

                    List<DataBiodataItem> array = response.body().getDataBiodata();
                    BiodataAdapter adapter = new BiodataAdapter(MainActivity.this, array);
                    adapter.notifyDataSetChanged();

                    rvBiodata.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    rvBiodata.setHasFixedSize(true);
                    rvBiodata.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseGetBiodata> call, Throwable t) {

                Log.d("error", t.getMessage());
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionGetBiodata();
    }
}
