
package main.java.edu.ingsoft.colegio.gotitas.config;

/**
 *
 * @author informatica
 */
public class Credentials {
    /*
    public static final String URL_MYSQL_DB = "jdbc:mysql://localhost:3306/" + "colegio_gotitas_del_saber_in4bm";
    public static final String PASS_DB = "$DmynM4A";
    public static final String USER_DB = "IN4BM";
    */
    public static final String DATA_BASE = System.getenv("DATA_BASE");
    public static final String URL_MYSQL_DB =System.getenv("DB_URL") + "login_base_datos_in4bm";
    public static final String USER_DB =System.getenv("DB_USER");
    public static final String PASS_DB =System.getenv("DB_PASSWORD");
    

}
