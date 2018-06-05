# Processamento distribuído de imagens cartográficas usando Java RMI

### Passos de execução:
1. Alterar o endereço da imagem na classe Servidor
2. Executar servidor
3. Alterar o endereço de rede no cliente de "localhost" para o IP impresso ao executar o servidor
4. Executar clientes

### Explicação do funcionamento
A classe com o processamento deve ser uma interface que extende Remote, assim como a classe PedacoImagem. 
Para acessar e processar um método pelo cliente uma implementação da classe remota deve ser registrada no *Naming.rebind* do Servidor.

Para adicionar acesso a algum método pelo cliente, o método desejado deve ser criado na classe *InterfaceRemota* e deve dar *throws RemoteException* e depois implementado na classe PedacoImagem.

#### A fazer
 - Adicionar loop no Servidor para criar *Naming.rebind* para cada parte da imagem;
 - Enviar uma mensagem para o cliente com o número do serviço que ele deve acessar contendo a imagem que ele deve processar, assim não será necessário mudar o nome do serviço no cliente antes de executar; (Usar Java Socket - exemplo na pasta do projeto no Google Drive)
 - Criar método de processamento nas classes *InterfaceRemota* e *PedacoImagem*.
