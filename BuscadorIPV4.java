
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuscadorIPV4 {

    public static String getIPV4() {
        String ip = "";

        Enumeration camadasRede = getCamadasRede();

        while (camadasRede.hasMoreElements()) {
            NetworkInterface camada = (NetworkInterface) camadasRede.nextElement();
            Enumeration enumarationInterna = camada.getInetAddresses();

            ip = concatenaEnderecos(ip, enumarationInterna);
        }
        
        ip = validaIp(ip);
        
        return ip;

    }

    private static Enumeration getCamadasRede() {
        Enumeration camadasRede = null;

        try {
            camadasRede = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException ex) {
            Logger.getLogger(BuscadorIPV4.class.getName()).log(Level.SEVERE, null, ex);
        }

        return camadasRede;
    }

    private static String concatenaEnderecos(String ip, Enumeration enumarationInterna) {

        while (enumarationInterna.hasMoreElements()) {
            InetAddress enderecoRede = (InetAddress) enumarationInterna.nextElement();
            ip += enderecoRede.getHostAddress() + " --- ";
        }
        return ip;
    }

    private static String validaIp(String ip) {
        String camadas[] = ip.split(" --- ");

        for (String camada : camadas) {
            if (seIPV4(camada)) {
                return camada;
            }
        }
        return null;
    }

    private static boolean seIPV4(String s) {
        return !seLocalhostIPV4(s) && !seIPV6(s) && !seLocalHostIPV6(s);
    }

    private static boolean seLocalhostIPV4(String s) {
        return s.startsWith("127.");
    }

    private static boolean seIPV6(String s) {
        return s.contains("\\D");
    }

    private static boolean seLocalHostIPV6(String s) {
        return s.contains(":");
    }
}