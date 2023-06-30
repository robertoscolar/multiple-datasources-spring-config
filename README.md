# Configuração de dois datasources em projeto Spring Boot
Este é um projeto simples criado em Spring Boot, com o único intuito de exemplificar o funcionamento de dois datasources trabalhando juntos dentro de uma única aplicação usando o framework.
Por conta disso, só teremos duas entidades: `User` e `Department`. Cada uma usará um banco diferente.

O principal motivo para o projeto é a consulta e cópia posterior das classes de configuração localizadas em `src/main/java/br/com/datasource/config` , 
tendo em vista que os únicos pontos que mudarão para outras conexões serão as informações únicas de cada banco.


## Configurações
As configurações dos datasources estão localizadas no `application.properties`, dentro da pasta `resources`.

Para o exemplo deste projeto escolhi os bancos `MySQL` e `PostgreSQL`, mas poderia ser qualquer incluindo não-relacionais.

Para que a classe de configuração pegue a url e usuário corretos, geralmente é usado um prefixo para identificação, por exemplo:

> `spring.datasource.mysql` para configurações do MySQL

> `spring.datasource.postgres` para configurações do PostgreSQL

Podemos ver como o framework pega essas informações na própria classe de configuração dos datasources. Temos uma para cada banco no projeto e estão
localizadas em `src/main/java/br/com/datasource/config`, como citado anteriormente. O padrão de nome usado nas classes serão: `DataSourceBancoUsadoConfig`, por exemplo:
`DataSourceMySQLConfig`. 

**ATENÇÃO: Este nome não é obrigatório, apenas o nome que eu escolhi para as classes neste projeto**

Ainda

## Como testar

Passo-a-passo:

1. Para testar é necessário ter os bancos rodando na máquina local com o mesmo user e password do `application.properties` e ter os databases correspondentes criados:

> POSTGRESQL

- Database: datasource_test
- Porta: 5432
- User: postgres
- Password: postgres

> MYSQL

- Database: datasource_test
- Porta: 3306
- User: root
- Password: root

<br>
<br>

2. Logo após isso, é só iniciar a aplicação e o próprio hibernate criará as tabelas necessárias. Assim
que subir é só testar os endpoints:

- /users - **GET**
- /users/{id} - **GET**
- /users - **POST**

- /departments - **GET**
- /departments/{id} - **GET**
- /departments - **POST**

<br>
<br>

> Os posts esperam um body em json, estes só contém o atributo name. Exemplo:

{

    { 
      "name": "Roberto"
    }
    
}
