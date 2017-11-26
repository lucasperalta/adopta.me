package tpfinal.davinci.adoptame;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import tpfinal.davinci.adoptame.model.Mascota;

//Es necesario que el adapter extienda de BaseAdapter
public class MisMascotasAdapter extends BaseAdapter {

    private List<Mascota> misMascotas;
    private Context context;

    public List<Mascota> getMisMascotas() {
        return misMascotas;
    }

    public void setMisMascotas(List<Mascota> misMascotas) {
        this.misMascotas = misMascotas;
    }

    private MisMascotasAdapter() {
    }


    public MisMascotasAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return misMascotas.size();
    }

    @Override
    public Object getItem(int i) {
        return misMascotas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return misMascotas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       //Usamos el LayoutInflater para obtener la vista del item
        view = LayoutInflater.from(context).inflate(R.layout.mis_mascota_item, viewGroup, false);

        //Las vistas asociadas al item
        TextView nombre = (TextView) view.findViewById(R.id.nombreMisMascota);
        TextView edad = (TextView) view.findViewById(R.id.edadMisMascotas);
        TextView raza = (TextView) view.findViewById(R.id.razaMisMascotas);
        SimpleDraweeView image = (SimpleDraweeView) view.findViewById(R.id.fotoMisMascotas);



        Mascota mascota= misMascotas.get(i);
        nombre.setText(mascota.getNombre());
        edad.setText(String.valueOf(mascota.getEdad()));
        raza.setText(mascota.getRaza());
        Uri uri = Uri.parse(mascota.getImgUrl());
        image.setImageURI(uri);


        return view;
    }
}