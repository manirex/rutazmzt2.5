package kitaoka;
/**
 * Created by Marcos Kitaoka Castro on 02/10/2016.
 */

public class item {
    private String texto1;
    private String texto2;
    private String texto3;
    private String texto4;

    public item(String nombre,String tipo,String raza,String comentario){
        this.texto1 = nombre;
        this.texto2 = tipo;
        this.texto3 = raza;
        this.texto4 = comentario;
    }

    public String getNombre() {
        return texto1;
    }

    public String getTipo() {
        return texto2;
    }

    public String getRaza() { return texto3; }

    public String getComentario() { return texto4; }

}
