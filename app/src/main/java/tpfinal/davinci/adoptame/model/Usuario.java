package tpfinal.davinci.adoptame.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucas on 11/10/17.
 */

public class Usuario extends Persona {

    private String estado;


    private String password;


    private  String rol;

    public Usuario(String email,String password) {
        this.email = email;
        this.password=password;
    }

    public Map<String,String> toMap(){
        Map<String, String> data = new HashMap<String,String>();
        if(getEmail()!=null){
            data.put("usuario",getEmail());
        }
        if(password!=null){
            data.put("password",password);
        }




        return data;

    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
