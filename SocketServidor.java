
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServidor {

    private final int PORTA;
    private ServerSocket servidor;
    private final String IP = BuscadorIPV4.getIPV4();
    private final InputStream entradaMensagensClientes[];

    public SocketServidor(int porta, int quantidadeClientes) {
        this.PORTA = porta;
        abreServidor();
        entradaMensagensClientes = new InputStream[quantidadeClientes];
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
            entradaMensagensClientes[id] = socket.getInputStream();
            System.out.println("Nova conexão com o cliente " + socket.getInetAddress().getHostAddress() + " responsável pelo pedaco " + id + " da imagem");
            return socket;
        } catch (IOException ex) {
            Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void aguardaClientesProcessarem() {
        for (int i = 0; i < entradaMensagensClientes.length; i++) {
            if (isProcessamentoConcluido(entradaMensagensClientes[i], i)) {
            }
        }
    }

    private boolean isProcessamentoConcluido(InputStream cliente, int idCliente) {
        Scanner entradaMensagensCliente = new Scanner(cliente);

        while (true) {
            while (entradaMensagensCliente.hasNextLine()) {
                if (isIdCliente(entradaMensagensCliente.nextLine(), idCliente)) {
                    return true;
                }
            }
        }
    }

    private boolean isIdCliente(String mensagem, int idCliente) {
        return Integer.parseInt(mensagem) == idCliente;
    }
}
