
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Usuário
 */
public class BuscadorIPV4 {

    public static String getIPV4() {
        try {
            Enumeration<NetworkInterface> camadasDeRede = NetworkInterface.getNetworkInterfaces();

            while (camadasDeRede.hasMoreElements()) {
                NetworkInterface camada = camadasDeRede.nextElement();
                if (isLocalHost(camada) || !isInterfaceAtiva(camada)) {
                    continue;
                }

                String ipv4 = procuraIPV4naCamada(camada);
                if (ipv4 != null) {
                    return ipv4;
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static String procuraIPV4naCamada(NetworkInterface camada) {
        Enumeration<InetAddress> addresses = camada.getInetAddresses();
        while (addresses.hasMoreElements()) {
            InetAddress addr = addresses.nextElement();

            if (isIPV4(addr)) {
                return addr.getHostAddress();
            }
        }
        return null;
    }

    private static boolean isLocalHost(NetworkInterface nI) {
        try {
            return nI.isLoopback();
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private static boolean isInterfaceAtiva(NetworkInterface nI) {
        try {
            return nI.isUp();
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private static boolean isIPV4(InetAddress addr) {
        return addr instanceof Inet4Address;
    }

}
