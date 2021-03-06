package formacion.ipartek.com.helloworld;


public class Ingrediente {


    private long id;

    private String nombre;

    private boolean gluten;

    private String cantidad;

    public Ingrediente() {
        super();
        this.id = -1;
        this.nombre = "";
        this.gluten = true;
        this.cantidad = "";
    }

    public Ingrediente(long id, String nombre, boolean gluten) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.gluten = gluten;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isGluten() {
        return gluten;
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Ingrediente [id=" + id + ", nombre=" + nombre + ", gluten=" + gluten + ", cantidad=" + cantidad + "]";
    }

}
