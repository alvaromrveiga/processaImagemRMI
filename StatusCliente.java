
import java.io.InputStream;

public class StatusCliente {

    private final InputStream entradaMensagensServidor;
    private boolean finalizado;

    public StatusCliente(InputStream entrada) {
        entradaMensagensServidor = entrada;
        finalizado = false;
    }

    public InputStream getEntradaMensagensServidor() {
        return entradaMensagensServidor;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }
}
