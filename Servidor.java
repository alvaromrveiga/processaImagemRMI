
import java.awt.image.BufferedImage;
import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.*;

public class Servidor {

    private static final int PORTA_RMI = 1099;
    private static final int PORTA_SOCKET = 12345;

    private static final int DIVIDE_IMAGEM_EM = 10; //número de clientes

    public static void main(String args[]) {

        BufferedImage partes[] = divideImagem("C:\\Users\\alvaromrveiga\\Downloads\\arvore.jpg");
        //imagemTotal.salvaPartes(partes); //Salva a divisão de partes no servidor, serve para teste, deixar comentado na execução verdadeira

        SocketServidor socketServidor = abreServidor();

        atribuiPedacoParaClientes(partes, socketServidor);

        System.out.println("\nTodos os serviços RMI foram inicializados com sucesso!");
    }

    private static BufferedImage[] divideImagem(String caminhoImagem) {
        DivisorImagem imagemTotal = new DivisorImagem(caminhoImagem);
        return imagemTotal.divideImagemPor(DIVIDE_IMAGEM_EM);
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
        SocketServidor socketServidor = new SocketServidor(PORTA_SOCKET);
        System.out.println("Servidor RMI iniciado no mesmo IP na porta base " + PORTA_RMI);

        return socketServidor;
    }

    private static void atribuiPedacoParaClientes(BufferedImage[] partes, SocketServidor socketServidor) {
        for (int i = 0; i < DIVIDE_IMAGEM_EM; i++) {
            socketServidor.enviaIdCliente(i);

            criaServicoRmi(partes[i], i);
        }
    }
}
