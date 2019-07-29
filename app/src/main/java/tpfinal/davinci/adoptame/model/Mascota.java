package tpfinal.davinci.adoptame.model;



public class Mascota {
    private int id;
    private int edad;
    private String tipoMascota;
    private String distancia;
    private String raza;
    private String mediano;
    private String nombre;
    private String descripcion;
    private Rescatista rescatista;
    private String estado;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private String imgUrl;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTipoMascota() {
        return tipoMascota;
    }

    public void setTipoMascota(String tipoMascota) {
        this.tipoMascota = tipoMascota;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getMediano() {
        return mediano;
    }

    public void setMediano(String mediano) {
        this.mediano = mediano;
    }

    public Rescatista getRescatista() {
        return rescatista;
    }

    public void setRescatista(Rescatista rescatista) {
        this.rescatista = rescatista;
    }



    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }



    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
