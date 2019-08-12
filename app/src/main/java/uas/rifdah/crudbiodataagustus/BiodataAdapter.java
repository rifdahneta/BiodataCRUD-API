package uas.rifdah.crudbiodataagustus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import uas.rifdah.crudbiodataagustus.model.DataBiodataItem;

public class BiodataAdapter extends RecyclerView.Adapter<BiodataAdapter.ViewHolder> {
   Context context;
   List<DataBiodataItem> biodataItems;

    public BiodataAdapter(Context context, List<DataBiodataItem> biodataItems) {
        this.context = context;
        this.biodataItems = biodataItems;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_biodata, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BiodataAdapter.ViewHolder holder, final int position) {

        final String id = biodataItems.get(position).getId();
        final String nama = biodataItems.get(position).getNama();
        final String jenkel = biodataItems.get(position).getJekel();
        final String hobi = biodataItems.get(position).getHobi();
        final String alamat = biodataItems.get(position).getAlamat();

        holder.nama.setText(nama);
        holder.jenisKelamin.setText(jenkel);
        holder.alamat.setText(alamat);
        holder.hobi.setText(hobi);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              DataBiodataItem dataBiodata = new DataBiodataItem();
              dataBiodata.setId(id);
              dataBiodata.setNama(nama);
              dataBiodata.setAlamat(alamat);
              dataBiodata.setHobi(hobi);
              dataBiodata.setJekel(jenkel);

              Intent intent = new Intent(context, UpdateDeleteActivity.class);
              intent.putExtra(UpdateDeleteActivity.key_biodata, dataBiodata);
              context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return biodataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama,jenisKelamin,alamat,hobi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tv_nama);
            jenisKelamin = itemView.findViewById(R.id.tv_jekel);
            alamat = itemView.findViewById(R.id.tv_alamat);
            hobi = itemView.findViewById(R.id.tv_hobi);
        }
    }
}
