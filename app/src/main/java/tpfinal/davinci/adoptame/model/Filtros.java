package tpfinal.davinci.adoptame.model;

import java.util.HashMap;
import java.util.Map;



public class Filtros {

    private String edad;
    private String ubicacion;
    private String tamanio;
    private String sexo;
    private String tipoMascota;

    public Map<String,String>  toMap(){
        Map<String, String> data = new HashMap<String,String>();
        if(edad!=null){
            data.put("edad",edad);
        }
        if(ubicacion!=null){
            data.put("ubicacion",ubicacion);
        }

        if(tamanio!=null){
            data.put("tamanio",tamanio);
        }
        if(sexo!=null){
            data.put("sexo",sexo);
        }
        if(tipoMascota!=null){
            data.put("tipoMascota",tipoMascota);
        }


       return data;

    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoMascota() {
        return tipoMascota;
    }

    public void setTipoMascota(String tipoMascota) {
        this.tipoMascota = tipoMascota;
    }




}
