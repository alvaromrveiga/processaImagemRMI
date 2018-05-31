
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String args[]) {
        try {
            Hello serv = (Hello) Naming.lookup("//192.168.0.105/ServidorHello");
            String retorno = serv.sayHello();
            System.out.println(retorno);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
