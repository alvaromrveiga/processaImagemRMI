# Processamento distribuído de imagens cartográficas usando Java RMI

A classe com o processamento deve ser uma interface que extende Remote, assim como a classe Hello. 
Para acessar e processar um método pelo cliente uma implementação da classe remota deve ser registrada no *Naming.rebind* do Servidor.

Para executar o cliente em uma máquina diferente da do servidor use o ip retornado pelo método da classe estática *BuscadorIPV4.getIPV4()* e jogue no lugar de *localhost* na chamada *Naming.lookup* da classe Cliente.
