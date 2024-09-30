EASTER_EGG_URLS

# HTML ANALYZER 

Este programa foi criado para o Teste técnico: Software Development Intern.


## Objetivo
No problema apresentado, o programa deve a partir de uma URL fornecida, obter o trecho de texto contido no nível mais profundo da estrutura HTML de seu conteúdo.

## Estrtutura

### Lógica da Solução

A solução implementada para este problema fundamenta-se no simples conceito de uma pilha, sendo que, nesta pilha são armazenadas as tags identificadas, caminhando-se cada vez mais na profundidade do documento HTML. Portanto, toda vez que o programa identifica uma tag de abertura ele a coloca na pilha. Quando identifica uma tag de fechamento, ele avalia se a pilha está vazia ou se nome da tag de fechamento é diferente do topo da pilha. Se for o caso ele retorna uma mensagem de HTML mal formatado. Senão, ele retira a tag da pilha e armazena a sentença se a profundidade do texto contido na tag for maior do que o que foi previamente armazenado. Por fim, retornando o trecho de texto contido no nível mais profundo do HTML.

#### DeeperSentence (classe)

Esta classe é utilizada para armazenar o valor da profundidade e o trecho do texto contido no nível mais profundo da estrututra do HTML lido.

#### TagReader (classe)

A classe TagReader é utilizado para identificar se o programa esta lendo uma tag ou um trecho de texto. Além disso, ela também é utilizada para armazenar o nome da tag lida por um StringBuilder e saber se a tag identificada se trata de uma closing tag ou opening tag.


#### findDeeperInnerSentence (método)

Este método é o controlador da pilha, do TagReader e do DeeperSentence. Nele está contido o loop principal do programa que lê o HTML do URl fornecido através de um InputStream e controla se o fluxo atual de caracteres devem ser adicionados ao StringBuilder de tags ou da frase.
