
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PedacoImagem extends UnicastRemoteObject implements InterfaceRemota {

    private final BufferedImage imagem;
    private final int id;

    public PedacoImagem(BufferedImage imagem, int pedaco) throws RemoteException {
        super();

        this.imagem = imagem;
        id = pedaco;
    }

    public BufferedImage getImagem() {
        return imagem;
    }

    @Override
    public int getId() {
        return id;
    }
}
