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

## Instalação 

```
git clone https://github.com/brrodrigues/desafio.git
cd desafio
```

## Profile de execução

O profile padrao para execucao deste desafio esta definido para mysql. 

### Profile: postgresql
```
mvn clean spring-boot:run --spring.profiles.active=postgresql-db
```
### Profile: mysql(Padrao)
```
mvn clean spring-boot:run --spring.profiles.active=mysql-db
```
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
