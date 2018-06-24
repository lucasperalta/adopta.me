package tpfinal.davinci.adoptame.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucas on 11/10/17.
 */

public class Persona {


    protected Integer id;

    protected String nombre;

    protected String apellido;

    protected String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
