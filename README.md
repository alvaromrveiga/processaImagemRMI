# Processamento distribuído de imagens cartográficas usando Java RMI

### Passos de execução:
1. Alterar o endereço da imagem na classe Servidor;
2. Alterar a variável *QUANTIDADE_CLIENTES* para a quantidade desejada de clientes no processamento da imagem;
3. Executar servidor;
4. Alterar a variável *IP* na classe cliente para o IP impresso na execução da classe Servidor;
5. Executar clientes

### Explicação do funcionamento
A classe com o processamento deve ser uma interface que extende Remote, assim como a classe PedacoImagem. 
Para acessar e processar um método pelo cliente uma implementação da classe remota deve ser registrada no *Naming.rebind* do Servidor.

Para adicionar acesso a algum método pelo cliente, o método desejado deve ser criado na classe *InterfaceRemota* e deve dar *throws RemoteException* e depois implementado na classe PedacoImagem.
