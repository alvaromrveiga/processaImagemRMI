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
 - Criar método de processamento nas classes *InterfaceRemota* e *PedacoImagem*.
 - Cliente envia mensagem avisando o servidor que terminou o processamento.
