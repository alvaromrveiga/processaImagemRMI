
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class AgrupadorImagem {

    private final BufferedImage partes[];
    private final String caminhoOriginal;

    public AgrupadorImagem(BufferedImage partes[], String caminho) {
        this.partes = partes;
        this.caminhoOriginal = caminho;
    }

    public void agrupaSalvaImagem() {
        BufferedImage imagemAgrupada = agrupaPartes();

        salvaImagem(imagemAgrupada);
    }

    private BufferedImage agrupaPartes() {
        int alturaImagem = partes[0].getHeight(); //todas as imagens têm a mesma altura
        int comprimentoImagem = getComprimentoImagem();

        BufferedImage imagemAgrupada = new BufferedImage(comprimentoImagem, alturaImagem, BufferedImage.TYPE_INT_RGB);

        imagemAgrupada = concatenaPartes(imagemAgrupada);

        return imagemAgrupada;
    }

    private BufferedImage concatenaPartes(BufferedImage imagemAgrupada) {
        int comprimentoAtual = 0;
        Graphics2D g2d = imagemAgrupada.createGraphics();

        for (BufferedImage parte : partes) {
            g2d.drawImage(parte, comprimentoAtual, 0, null);
            comprimentoAtual += parte.getWidth();
        }
        g2d.dispose();

        return imagemAgrupada;
    }

    private int getComprimentoImagem() {
        int comprimentoImagem = 0;

        for (BufferedImage parte : partes) {
            comprimentoImagem += parte.getWidth();
        }

        return comprimentoImagem;
    }

    private void salvaImagem(BufferedImage imagemAgrupada) {
        try {
            ImageIO.write(imagemAgrupada, getTipoImagem(), new File(alteraNomeImagem()));
        } catch (IOException ex) {
            Logger.getLogger(AgrupadorImagem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String alteraNomeImagem() {
        String caminhoNomeAtualizado = caminhoOriginal;

        caminhoNomeAtualizado = caminhoNomeAtualizado.replaceAll("." + getTipoImagem(), "");
        caminhoNomeAtualizado += "_Processado";
        caminhoNomeAtualizado += "." + getTipoImagem();

        return caminhoNomeAtualizado;
    }

    private String getTipoImagem() {
        String tipoImagem = caminhoOriginal.substring(caminhoOriginal.length() - 3, caminhoOriginal.length()); //são as 3 últimas letras. Ex.: png

        return tipoImagem;
    }
}
