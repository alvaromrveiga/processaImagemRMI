
import java.awt.Color;
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

    @Override
    public void processaImagem() throws RemoteException {
        Color cor = new Color(10, 50, 100);
        for (int x = 0; x < imagem.getWidth(); x++) {
            for (int y = 0; y < imagem.getHeight(); y++) {
                imagem.setRGB(x, y, imagem.getRGB(x, y) + cor.getRGB());
            }
        }
    }
}
