package tpfinal.davinci.adoptame;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.List;

import tpfinal.davinci.adoptame.model.Mascota;

class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {

    private List<Mascota> mascotaList;
    private Context context;

    public List<Mascota> getMascotaList() {
        return mascotaList;
    }

    public void setMascotaList(List<Mascota> mascotaList) {
        this.mascotaList = mascotaList;
    }

    public MascotaAdapter(Context context) {

        this.context = context;
    }

    @Override
    public MascotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MascotaViewHolder(LayoutInflater.from(context).inflate(R.layout.mascota_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MascotaViewHolder holder, int position) {
            fillContent(holder, mascotaList.get(position));
    }

    @Override
    public int getItemCount() {
        return mascotaList.size();
    }

    public void fillContent(MascotaViewHolder mascotaViewHolder, Mascota mascota) {
        mascotaViewHolder.nombre.setText(mascota.getNombre());
        mascotaViewHolder.edad.setText(Integer.toString(mascota.getEdad()));
        mascotaViewHolder.raza.setText(mascota.getRaza());

        mascotaViewHolder.descripcion.setText(mascota.getDescripcion());

        Uri uri = Uri.parse(mascota.getFoto_url());

        mascotaViewHolder.image.setImageURI(uri);
    }

    static class MascotaViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre;
        private TextView edad;
        private TextView raza;
        private TextView descripcion;
        private SimpleDraweeView image;

        public MascotaViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombreMascota);
            edad = itemView.findViewById(R.id.edadMascota);
            raza = itemView.findViewById(R.id.razaMascota);
            descripcion = itemView.findViewById(R.id.descripcionMascota);
            image = itemView.findViewById(R.id.fotoMascota);
        }
    }
}
