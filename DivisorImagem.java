
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class DivisorImagem {

    private BufferedImage imagem;
    private final File arquivoImagem;

    public DivisorImagem(String caminhoArquivo) {
        arquivoImagem = new File(caminhoArquivo);
        carregaImagem();
    }

    private void carregaImagem() {
        try {
            imagem = ImageIO.read(arquivoImagem);
        } catch (IOException ex) {
            Logger.getLogger(DivisorImagem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BufferedImage[] divideImagemPor(int divisor) {
        int comprimentoOriginal = imagem.getWidth();
        int altura = imagem.getHeight();
        int comprimentoDividido = comprimentoOriginal / divisor;

        BufferedImage partesDivididas[] = new BufferedImage[divisor];

        int xAtual = 0;

        for (int i = 0; i < divisor; i++) {
            partesDivididas[i] = divideImagem(xAtual, 0, comprimentoDividido, altura); //pega filetes com a altura total da imagem
            xAtual += comprimentoDividido; //passa para o próximo filete
        }

        return partesDivididas;
    }

    private BufferedImage divideImagem(int xInicial, int yInicial, int comprimentoParte, int alturaParte) {
        return imagem.getSubimage(xInicial, yInicial, comprimentoParte, alturaParte);
    }

    public void salvaPartes(BufferedImage[] partes) {

        String tipoImagem = getTipoImagem();
        String nomeImagemBase = arquivoImagem.getName().replaceAll("." + tipoImagem, "");
        String pastasImagem = arquivoImagem.getParent() + "\\";

        String caminho;
        for (int i = 0; i < partes.length; i++) {
            caminho = pastasImagem + nomeImagemBase + "_" + (i + 1) + "." + tipoImagem;
            salva(partes[i], caminho);
        }
    }

    private void salva(BufferedImage imagem, String caminhoArquivo) {

        String tipoImagem = getTipoImagem();

        try {
            ImageIO.write(imagem, tipoImagem.toUpperCase(), new File(caminhoArquivo));
        } catch (IOException ex) {
            Logger.getLogger(DivisorImagem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getTipoImagem() {
        String caminhoImagem = arquivoImagem.getAbsolutePath();
        String tipoImagem = caminhoImagem.substring(caminhoImagem.length() - 3, caminhoImagem.length()); //são as 3 últimas letras. Ex.: png

        return tipoImagem;
    }
}
