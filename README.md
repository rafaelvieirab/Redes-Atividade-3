# Redes-Atividade-3
1° Atividade Prática Remota de Redes

Implementação do Algoritmo de Dijkstra, simulando o comportamento da Rede durante o estabelecimento das rotas.

Para criação do grafo e da criação as rotas, o sistema deverá receber como entrada um arquivo TXT que descreve a topologia da rede em questão. 
Neste arquivo estarão descritos: 
1) Os dispositivos/nós que compõe a rede (DEVICES); 
2) Os enlaces/arestas que compõe a rede e os pesos relacionados (LINKS);
3) A origem e o destino de algumas rotas para avaliação da corretude da implementação(ROUTES).

Exemplo do formato do arquivo:

DEVICES:x,y,z
LINKS:(x,y,2),(y,z,1),(x,z,7)
ROUTES:(x,z)


À esquerda é possível visualizar uma representação do arquivo TXT que define a rede a direita. Perceba
que, o arquivo inicia com a descrição dos dispositivos/nós através da chave “DEVICES:”, nesse trecho
são elencados os identificadores dos comutadores separados por virgulas (,). Em seguida, a chave
“LINKS:” define os enlaces/arestas que compõe a rede e os pesos relacionados separados por vírgula
(,). Atenção especial ao formato desse trecho, perceba que cada enlace é representado em formato de
tupla (delimitado por parênteses e seus valores separados por vírgulas). Cada valor da tupla representa,
nesta ordem: o identificador da origem do enlace, o identificador do destino do enlace e o peso entre
eles. Por fim, o último trecho ( “ROUTES:”) elenca as rotas a serem montadas/avaliadas (também
separadas por vírgulas e também em formato de tuplas). Cada valor da tupla representa, nesta ordem: o
identificador da origem do enlace e o identificador do destino do enlace.

Uma vez lido o arquivo de entrada, o código deverá simular o estabelecimento das rotas na Rede em
questão. Para isso, o código deverá implementar o Algoritmo de Dijkstra apresentado em sala de aula. A
cada iteração (incluindo Fases de Inicialização e Interação), o código deverá exibir as Tabelas de
Repasse montadas para cada um dos dispositivos/nós pertencentes a Rede até que ela chegue a uma
estabilidade. Ao final desta etapa, o código deverá apresentar as rotas solicitadas no último trecho. As
rotas deverão ser exibidas passo a passo da origem até o destino.

Fase de Inicialização
x: | y: | z:
y,(x,y),2 | x,(y,x),2 | x,(z,x),7
z,(x,z),7 | z,(y,z),1 | y,(z,y),1
(...)
Fase de Interação X
x: | y: | z:
y,(x,y),2 | x,(y,x),2 | x,(z,y),3
z,(x,y),3 | z,(y,z),1 | y,(z,y),1
ROTA (x,z): (x,y),(y,z)

