
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServidor {

    private final int PORTA;
    private ServerSocket servidor;
    private final String IP = BuscadorIPV4.getIPV4();

    public SocketServidor(int porta) {
        this.PORTA = porta;
        abreServidor();
    }

    private void abreServidor() {
        try {
            servidor = new ServerSocket(PORTA);

            System.out.println("Servidor iniciado no IP " + IP + " na porta " + PORTA);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviaIdCliente(int id) {
        PrintStream saida;
        Socket cliente = null;

        while (cliente == null) {
            cliente = esperaCliente(id);
        }

        try {
            saida = new PrintStream(cliente.getOutputStream());
            saida.println(id);
        } catch (IOException ex) {
            Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Socket esperaCliente(int id) {
        try {
            Socket socket = servidor.accept();
            System.out.println("Nova conexão com o cliente " + socket.getInetAddress().getHostAddress() + " responsável pelo pedaco " + id + " da imagem");
            return socket;
        } catch (IOException ex) {
            Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
