package tpfinal.davinci.adoptame.model;

import java.util.HashMap;
import java.util.Map;



public class Usuario {

    private String usuario;
    private String password;


    public Usuario(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public Map<String,String> toMap(){
        Map<String, String> data = new HashMap<String,String>();
        if(usuario!=null){
            data.put("usuario",usuario);
        }
        if(password!=null){
            data.put("password",password);
        }




        return data;

    }



    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
