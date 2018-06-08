
import java.awt.image.BufferedImage;
import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.*;

public class Servidor {

    private static final int PORTA_RMI = 1099;
    private static final int PORTA_SOCKET = 12345;

    private static final int QUANTIDADE_CLIENTES = 5; //número de clientes

    public static void main(String args[]) {

        DivisorImagem divisorImagem = new DivisorImagem("C:\\Users\\Usuário\\Downloads\\leao.png");
        BufferedImage partes[] = divisorImagem.divideImagemPor(QUANTIDADE_CLIENTES);
        //divisorImagem.salvaPartes(partes); //Salva a divisão de partes no servidor, serve para teste, deixar comentado na execução verdadeira

        SocketServidor socketServidor = abreServidor();

        atribuiPedacoParaClientes(partes, socketServidor);
        System.out.println("\nTodos os serviços RMI foram inicializados com sucesso!");

        socketServidor.aguardaClientesProcessarem();
        System.out.println("\nTodos os clientes terminaram o processamento de sus respectivas partes da imagem");

        salvaImagemAgrupada(partes, divisorImagem.getCaminhoImagem());
        System.out.println("\nOs pedaços foram agrupados e a imagem foi salva!");

        System.exit(0); //Encerra o programa com sucesso
    }

    private static void criaServicoRmi(BufferedImage parte, int id) {
        try {
            InterfaceRemota pedacoImagem = new PedacoImagem(parte, id);
            LocateRegistry.createRegistry(PORTA_RMI + id);
            Naming.bind("pedacoImagem" + id, pedacoImagem);
        } catch (RemoteException | MalformedURLException | AlreadyBoundException e) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
            System.exit(0);
        }
    }

    private static SocketServidor abreServidor() {
        SocketServidor socketServidor = new SocketServidor(PORTA_SOCKET, QUANTIDADE_CLIENTES);
        System.out.println("Servidor RMI iniciado no mesmo IP na porta base " + PORTA_RMI + "\n");

        return socketServidor;
    }

    private static void atribuiPedacoParaClientes(BufferedImage[] partes, SocketServidor socketServidor) {
        for (int i = 0; i < QUANTIDADE_CLIENTES; i++) {
            socketServidor.enviaIdCliente(i);

            socketServidor.iniciaCanalMensagemCliente(i);

            criaServicoRmi(partes[i], i);
        }
    }

    private static void salvaImagemAgrupada(BufferedImage partes[], String caminho) {
        new AgrupadorImagem(partes, caminho).agrupaSalvaImagem();
    }
}
