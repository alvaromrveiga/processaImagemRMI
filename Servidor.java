
import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.logging.*;

public class Servidor extends UnicastRemoteObject implements Hello {

    private static final int PORTA = 1099;
    private static String ip;

    public Servidor() throws RemoteException {
        super();
    }

    @Override
    public String sayHello() {
        return ("Oi cliente");
    }

    public static void main(String args[]) {

        try {
            Hello serv = new Servidor();
            // Registra nome do servidor
            ip = BuscadorIPV4.getIPV4();
            LocateRegistry.createRegistry(PORTA);
            Naming.rebind("ServidorHello", serv);
            System.out.println("Servidor remoto iniciado no IP " + ip + " na porta " + PORTA);
        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }
}
