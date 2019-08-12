package uas.rifdah.crudbiodataagustus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uas.rifdah.crudbiodataagustus.model.ResponseGetBiodata;
import uas.rifdah.crudbiodataagustus.network.ApiClient;

public class InsertActivity extends AppCompatActivity {

    EditText edtNama, edtAlamat;
    Button btnInsert;
    RadioButton rdLaki, rdPerempuan;
    RadioGroup rdJekel;
    Spinner spHobi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        edtNama= findViewById(R.id.et_nama);
        edtAlamat = findViewById(R.id.et_alamat);
        btnInsert = findViewById(R.id.btn_insert);
        rdLaki = findViewById(R.id.rb_lakilaki);
        rdPerempuan = findViewById(R.id.rb_perempuan);
        spHobi = findViewById(R.id.spin_hobi);

        String hobi [] = {"Pilih Hobi","Memanah", "Membaca", "Memancing", "Makan"};

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item,hobi);
        spHobi.setAdapter(adapterSpinner);



        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nama = edtNama.getText().toString().trim();
                String alamat = edtAlamat.getText().toString().trim();
                String hobi = spHobi.getSelectedItem().toString();

                String jenkel = null;
                if (rdLaki.isChecked()){
                    jenkel = rdLaki.getText().toString();

                } else if(rdPerempuan.isChecked()); {
                    jenkel = rdPerempuan.getText().toString();
                }
                // todo buat validasi
                if (TextUtils.isEmpty(nama)){
                    edtNama.setError("Nama tidak boleh kosong");
                } else if (TextUtils.isEmpty(jenkel)){
                    Toast.makeText(InsertActivity.this, "pilih jenis kelamin", Toast.LENGTH_SHORT).show();
                }else if (hobi.equals("Pilih Hobi")){
                    Toast.makeText(InsertActivity.this, "pilih hobi", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(alamat)){
                    edtAlamat.setError("alamat tidak boleh kosong");
                } else {

                    // action insert
                insertBiodata(nama,jenkel,hobi,alamat);
                }
            }
        });

    }

    private void insertBiodata(String nama, String jekel, String hobi, String alamat){
        ApiClient.service.actionInsert(nama, jekel, hobi, alamat).enqueue(new Callback<ResponseGetBiodata>() {
            @Override
            public void onResponse(Call<ResponseGetBiodata> call, Response<ResponseGetBiodata> response) {
                if (response.isSuccessful()) {
                    String pesan = response.body().getPesan();
                    int status = response.body().getStatus();

                    if (status == 1) {
                        Toast.makeText(InsertActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else if (status==0){
                        Toast.makeText(InsertActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseGetBiodata> call, Throwable t) {
                Toast.makeText(InsertActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
