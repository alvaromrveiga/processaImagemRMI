
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketCliente {

    private final Socket socket;

    public SocketCliente(Socket socket) {
        this.socket = socket;

    }

    public String ouveMensagem() {
        Scanner entrada = inicializaEntrada();

        while (entrada.hasNextLine()) {
            return entrada.nextLine();
        }
        
        return null;
    }

    private Scanner inicializaEntrada() {
        Scanner entrada = null;

        try {
            entrada = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (entrada != null) {
            return entrada;
        } else {
            new RuntimeException("Erro ao inicializar a entrada de mensagens do cliente no método inicializaEntrada()");
            return null;
        }
    }
}
