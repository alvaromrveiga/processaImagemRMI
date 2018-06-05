
import java.awt.image.BufferedImage;
import java.net.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.logging.*;

public class Servidor {

    private static final int PORTA = 1099;
    private static final String IP = BuscadorIPV4.getIPV4();

    public static void main(String args[]) {

        DivisorImagem imagemTotal = new DivisorImagem("C:\\Users\\Usuário\\Downloads\\leao.png");
        BufferedImage partes[] = imagemTotal.divideImagemPor(10);
        
      //imagemTotal.salvaPartes(partes); //Salva a divisão de partes no servidor, serve para teste, deixar comentado na execução verdadeira

        try {
            InterfaceRemota pedacoImagem = new PedacoImagem(partes[0], 60);
            LocateRegistry.createRegistry(PORTA);
            Naming.rebind("pedacoImagem", pedacoImagem);
            System.out.println("Servidor remoto iniciado no IP " + IP + " na porta " + PORTA);
        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }
}
