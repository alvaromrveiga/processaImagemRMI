
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    private static final String IP = "172.22.81.25";
    private static final int PORTA_SOCKET = 12345;

    public static void main(String args[]) {

        SocketCliente socketCliente = inicializaSocketCliente();

        int id = getId(socketCliente);

        acessaServicoRmi(id);
    }

    private static int getId(SocketCliente socketCliente) {
        return Integer.parseInt(socketCliente.ouveMensagem());
    }

    private static void acessaServicoRmi(int id) {
        try {
            InterfaceRemota imagem = (InterfaceRemota) Naming.lookup("//" + IP + "/pedacoImagem" + id);
            System.out.println(imagem.getId());

            //System.out.println("Processando...");
            //while (true);

        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static SocketCliente inicializaSocketCliente() {
        try {
            return new SocketCliente(new Socket(IP, PORTA_SOCKET));
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
