
import java.awt.image.BufferedImage;
import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.*;

public class Servidor {

    private static final int PORTA_RMI = 1099;
    private static final int PORTA_SOCKET = 12345;

    private static final int QUANTIDADE_CLIENTES = 5; //número de clientes

    public static void main(String args[]) {

        BufferedImage partes[] = divideImagem("C:\\Users\\Usuário\\Downloads\\leao.png");

        SocketServidor socketServidor = abreServidor();

        atribuiPedacoParaClientes(partes, socketServidor);
        System.out.println("\nTodos os serviços RMI foram inicializados com sucesso!");

        socketServidor.aguardaClientesProcessarem();
        System.out.println("\nTodos os clientes terminaram o processamento de sus respectivas partes da imagem");

        salvaImagemAgrupada();
        System.out.println("Os pedaços foram agrupados e a imagem foi salva!");
    }

    private static BufferedImage[] divideImagem(String caminhoImagem) {
        DivisorImagem imagemTotal = new DivisorImagem(caminhoImagem);

        BufferedImage partes[] = imagemTotal.divideImagemPor(QUANTIDADE_CLIENTES);

        //imagemTotal.salvaPartes(partes); //Salva a divisão de partes no servidor, serve para teste, deixar comentado na execução verdadeira
        return partes;
    }

    private static void criaServicoRmi(BufferedImage parte, int id) {
        try {
            InterfaceRemota pedacoImagem = new PedacoImagem(parte, id);
            LocateRegistry.createRegistry(PORTA_RMI + id);
            Naming.rebind("pedacoImagem" + id, pedacoImagem);
        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }

    private static SocketServidor abreServidor() {
        SocketServidor socketServidor = new SocketServidor(PORTA_SOCKET, QUANTIDADE_CLIENTES);
        System.out.println("Servidor RMI iniciado no mesmo IP na porta base " + PORTA_RMI);

        return socketServidor;
    }

    private static void atribuiPedacoParaClientes(BufferedImage[] partes, SocketServidor socketServidor) {
        for (int i = 0; i < QUANTIDADE_CLIENTES; i++) {
            socketServidor.enviaIdCliente(i);

            criaServicoRmi(partes[i], i);
        }
    }

    private static void salvaImagemAgrupada() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
