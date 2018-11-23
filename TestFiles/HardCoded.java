/**
 * Created by beeber8 on 11/11/18.
 */
public class HardCoded {

    String secretUsername = "username";
    String secretPassword = "password"

    webapp.ldap.username=secretUsername;
    webapp.ldap.password=secretPassword;

    DriverManager.getConnection(url, "scott", "tiger");

    public void hello() {
        System.out.println("Hello");
    }
}
