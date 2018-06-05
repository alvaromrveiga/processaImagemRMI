
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceRemota extends Remote {

    public int getId()throws RemoteException; //se tirar o throws dá erro para o UnicastRemoteObject na classe PedacoImagem
}
