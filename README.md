# Desafio #1

Este desafio visava implementar as seguintes questões: 

* Terminar o desafio até 72 horas.
* Produzir e publicar o material no GitHub para torná-lo disponivel, funcional e executável por um simples comando. Como git pull, mvn package , etc.
* Utilizar as tecnologias: Spring MVC, Restful, JSON, Spring DB e MySQL
* Existirá uma thread que sera responsavel por extrair as informações do base de dados para um arquivo executado de 1 em 1 minuto.
* Criar um endpoint para registro de vendas.
* Os arquivos gerados não poderão ultrapassar 10 linhas
* Implementar uma tela de relatório contendo as vendas extradas para os arquivos.

## Prerequisitos

É necessário as ferramentas postgresql/mysql, maven e git estejam instalado na maquina.

## Variavel externa
Na maioria das vezes são necessárias configuraçes pré-definida para a execução de um determinada aplicaço, acesso ao banco, geração da saida de informações, etc. 
Para o criaremos variável que possam se atribuida em tempo de execução. São elas:

* DB_HOST     // IP/omínio de acesso ao banco de dados
* DB_SCHEMA   // Nome do esquema na qual deseja executar armazenas os dados da aplicação
* DB_USER     // Nome do usuário de acesso ao banco de dados
* DB_PORT     // Porta de acesso ao banco de dados
* DB_PASSWORD // Senha do usurio de acesso ao banco de dados
* OUTPUT_PATH // Caminho no qual ocorrerá a saída dos arquivos gerado
* DELAY       // O tempo em milisegundos que a ocorréra o evento de consulta e extração de dados

Existem dois pontos importante :

* É necessário que o usuário possua permissão de escrita no esquema definida na váriável DB_SCHEMA
* É necessário que o caminho definido na variável OUTPUT_PATH para a saído do arquivo possua permissão de acesso pelo usuário responsável pela execução da aplicação

## Criando um jar executável

```
git clone https://github.com/brrodrigues/desafio.git
cd desafio
mvn clean package
```

## Profile de execução
O profile padrao para execucao deste desafio esta definido para mysql.
### Profile: postgresql
```
--spring.profiles.active=postgresql
```
### Profile: mysql(Padrao)
```
--spring.profiles.active=mysql
```
## Exemplo de linha de comando
```
### Profile postgresql
java -DDB_HOST=localhost -DDB_PORT=5432 -DDB_SCHEMA=postgres -DDB_USER=postgres -DDB_PASSWORD=postgres \
-DDELAY=60000 -DOUTPUT_PATH=c:\\tmp -jar target/batchprogram-0.0.1-SNAPSHOT.jar --spring.profiles.active=postgresql
```
### Profile mysql
```
java -DDB_HOST=localhost -DDB_PORT=3306 -DDB_SCHEMA=mysql -DDB_USER=root -DDB_PASSWORD=123 \
-DDELAY=60000 -DOUTPUT_PATH=c:\\tmp -jar target/batchprogram-0.0.1-SNAPSHOT.jar --spring.profiles.active=mysql
```

A variável DELAY é opcional. Padrão definido: 30000 (30 seg)

## Endpoint - Registro de Venda
```
http://localhost:8080/vendas
```
## Report
```
http://localhost:8080/reportController
```



## Autor

* **Bruno Rodrigues** - (https://github.com/brrodrigues)
