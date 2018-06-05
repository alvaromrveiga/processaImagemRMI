
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String args[]) {
        try {
            InterfaceRemota imagem = (InterfaceRemota) Naming.lookup("//localhost/pedacoImagem");
            System.out.println(imagem.getId());
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
