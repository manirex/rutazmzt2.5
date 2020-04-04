package kitaoka;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcos Kitaoka Castro on 02/10/2016.
 */

public class Global {
    public static int idusuario;
    public static String usuario;
    public static String correo;
    public static String password;
    public static String phone;
    public static String emeregency_phone;

    public static String comentarios;
    public static String domicilio1;
    public static String domicilio2;

    public static Boolean origen_mapa;
    public static Boolean servicio_local;
    public static List<List<String>> PetList = new ArrayList<>();
    public static int opcion;
    public static Marker origen;
    public static Marker destino;

    public static List<ArrayList> ListMascotas = new ArrayList<ArrayList>();
    public static int tipoUnidad;
    public static Double millas;
    public static Double precio;
}
