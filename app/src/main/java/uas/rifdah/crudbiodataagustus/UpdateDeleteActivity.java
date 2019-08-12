package uas.rifdah.crudbiodataagustus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import uas.rifdah.crudbiodataagustus.model.DataBiodataItem;
import uas.rifdah.crudbiodataagustus.model.ResponseGetBiodata;
import uas.rifdah.crudbiodataagustus.network.ApiClient;

public class UpdateDeleteActivity extends AppCompatActivity {

    public static String  key_biodata = "key_biodata";
    EditText updateNama, updateAlamat;
    Button btnUpdate, btnDelete;
    RadioButton updateRdLaki, updateRdPerempuan;
    Spinner updateSpHobi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        updateNama = findViewById(R.id.update_et_nama);
        updateAlamat = findViewById(R.id.update_et_alamat);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete =  findViewById(R.id.btn_delete);
        updateRdLaki = findViewById(R.id.update_rb_lakilaki);
        updateRdPerempuan = findViewById(R.id.update_rb_perempuan);
        updateSpHobi = findViewById(R.id.update_spin_hobi);


        DataBiodataItem dataBiodataItem = getIntent().getParcelableExtra(key_biodata);
        updateNama.setText(dataBiodataItem.getNama());
        updateAlamat.setText(dataBiodataItem.getAlamat());

        if (dataBiodataItem.getJekel().equals("Laki-laki")){
            updateRdLaki.setChecked(true);
        }
        else if (dataBiodataItem.getJekel().equals("Perempuan")){
            updateRdPerempuan.setChecked(true);
        }

        String hobi [] = {"Pilih Hobi","Memanah", "Membaca", "Memancing", "Makan"};
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_dropdown_item,hobi);
        updateSpHobi.setAdapter(adapterSpinner);

        for(int i =0; i<adapterSpinner.getCount();i++){
            if (updateSpHobi.getItemAtPosition(i).equals(dataBiodataItem.getHobi())){
                updateSpHobi.setSelection(i);
            }
        }

        final String terimaId = dataBiodataItem.getId();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = updateNama.getText().toString().trim();
                String alamat = updateAlamat.getText().toString().trim();
                String hobi = updateSpHobi.getSelectedItem().toString();

                String jenkel = null;
                if (updateRdLaki.isChecked()){
                    jenkel = updateRdLaki.getText().toString();

                } else if(updateRdPerempuan.isChecked()); {
                    jenkel = updateRdPerempuan.getText().toString();
                }
                // todo buat validasi
                if (TextUtils.isEmpty(nama)){
                    updateNama.setError("Nama tidak boleh kosong");
                } else if (TextUtils.isEmpty(jenkel)){
                    Toast.makeText(UpdateDeleteActivity.this, "pilih jenis kelamin", Toast.LENGTH_SHORT).show();
                }else if (hobi.equals("Pilih Hobi")){
                    Toast.makeText(UpdateDeleteActivity.this, "pilih hobi", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(alamat)){
                    updateAlamat.setError("alamat tidak boleh kosong");
                } else {

                    // action insert
                    updateBiodata(terimaId, nama,jenkel,hobi,alamat);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBiodata(terimaId);
            }
        });

    }

    public void updateBiodata(String id, String nama,String jenkel, String hobi, String alamat)
    {
        ApiClient.service.actionUpdate(id, nama, jenkel, hobi, alamat).enqueue(new Callback<ResponseGetBiodata>() {
            @Override
            public void onResponse(Call<ResponseGetBiodata> call, Response<ResponseGetBiodata> response) {
                if (response.isSuccessful()) {
                    String pesan = response.body().getPesan();
                    int status = response.body().getStatus();

                    if (status == 1) {
                        Toast.makeText(UpdateDeleteActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else if (status==0){
                        Toast.makeText(UpdateDeleteActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseGetBiodata> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteBiodata (String id){
        ApiClient.service.actionDelete(id).enqueue(new Callback<ResponseGetBiodata>() {
            @Override
            public void onResponse(Call<ResponseGetBiodata> call, Response<ResponseGetBiodata> response) {
                if (response.isSuccessful()) {
                    String pesan = response.body().getPesan();
                    int status = response.body().getStatus();

                    if (status == 1) {
                        Toast.makeText(UpdateDeleteActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else if (status==0){
                        Toast.makeText(UpdateDeleteActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseGetBiodata> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
