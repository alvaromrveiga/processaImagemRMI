
import java.io.IOException;
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
    private final StatusCliente statusClientes[];

    public SocketServidor(int porta, int quantidadeClientes) {
        this.PORTA = porta;
        abreServidor();
        statusClientes = new StatusCliente[quantidadeClientes];
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
            statusClientes[id] = new StatusCliente(socket.getInputStream());
            System.out.println("Nova conexão com o cliente " + socket.getInetAddress().getHostAddress() + " responsável pelo pedaco " + id + " da imagem");
            return socket;
        } catch (IOException ex) {
            Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void aguardaClientesProcessarem() {
        for (StatusCliente statusCliente : statusClientes) {
            if (isProcessamentoConcluido(statusCliente)) {
            }
        }
    }

    private boolean isProcessamentoConcluido(StatusCliente cliente) {
        while (true) {
            dorme(100); //intervalo para verificar se o cliente já processou
            if (cliente.isFinalizado()) {
                return true;
            }
        }
    }

    private void dorme(long tempo_ms) {
        try {
            Thread.sleep(tempo_ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iniciaCanalMensagemCliente(int idCliente) {
        threadOuveMensagens(statusClientes[idCliente], idCliente);
    }

    private void threadOuveMensagens(final StatusCliente cliente, int idCliente) {

        Runnable runnable = () -> {
            ouveMensagens(cliente, idCliente);
        };

        //sem expressão lambda ficaria:
        /* 
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ouveMensagens(cliente, idCliente);
            }
        };
         */
        new Thread(runnable).start();
    }

    private boolean ouveMensagens(StatusCliente cliente, int idCliente) {
        Scanner leitorMensagens = new Scanner(cliente.getEntradaMensagensServidor());

        while (true) {
            while (leitorMensagens.hasNextLine()) {
                if (isIdCliente(leitorMensagens.nextLine(), idCliente)) {
                    cliente.setFinalizado(true);
                    return true;
                }
            }

        }
    }

    private boolean isIdCliente(String mensagem, int idCliente) {
        return Integer.parseInt(mensagem) == idCliente;
    }
}
