# Configuração de dois datasources em projeto Spring Boot
Este é um projeto simples criado em Spring Boot, com o único intuito de exemplificar o funcionamento de dois datasources trabalhando juntos dentro de uma única aplicação usando o framework.
Por conta disso, só teremos duas entidades: `User` e `Department`. Cada uma usará um banco diferente.

O principal motivo para o projeto é a consulta e cópia posterior das classes de configuração localizadas em `src/main/java/br/com/datasource/config` , 
tendo em vista que os únicos pontos que mudarão para outras conexões subsequentes serão as informações de conexão,
estas sendo únicas para cada banco.

<br>

## Configurações
Os dados dos datasources estão localizados no `application.properties`, dentro da pasta `resources`.

Para o exemplo deste projeto escolhi os bancos `MySQL` e `PostgreSQL`, mas poderia ser qualquer um, incluindo os não-relacionais.

Para que a classe de configuração pegue a url e usuário corretos, geralmente é usado um prefixo para identificação, por exemplo:

> `spring.datasource.mysql` para configurações do MySQL

> `spring.datasource.postgres` para configurações do PostgreSQL

Podemos ver como o framework trabalha com essas informações visualizando a própria classe de configuração dos datasources. Temos uma para cada banco no projeto e ambas estão localizadas em `src/main/java/br/com/datasource/config`, como citado anteriormente. O padrão de nome 
usado nas classes são: `DataSourceBancoUsadoConfig`, como exemplo temos:
`DataSourceMySQLConfig`. 

> ATENÇÃO: Este nome não é obrigatório, apenas o nome que eu escolhi para as classes neste projeto.

<br>

## Arquitetura

É importante notar que as entidades e repositórios têm 2 pacotes diferentes, uma para cada banco de dados.
Isso acontece para o Spring saber exatamente qual será a conexão usada para cada instância desse repositório
e qual será a entidade a ser trabalhada.

Com a classe configurada, o Spring saberá oque deve ser injetado quando receber a anotação
`@Autowired`.

No exemplo da aplicação, tudo que estiver dentro do pacote `mysql` dentro de 
`src/main/java/br/com/datasource/model` e `src/main/java/br/com/datasource/repository` será gerenciado
pela conexão do mysql, e a mesma coisa acontece pro outro, no caso o postgres.

<br>

## Como funciona a classe de configuração do datasource

<br>

Para a melhor compreensão dos itens usarei o DataSourceMySQLConfig como exemplo. Mas esteja ciente que o mesmo processo acontece pra ambas as classes.

A anotação `@Configuration` indica que essa classe é uma configuração do Spring e define um ou mais beans que serão gerenciados pelo contêiner do Spring.

A anotação `@EnableTransactionManagement` habilita o suporte a transações, permitindo que os métodos anotados com `@Transactional` executem dentro de uma transação.

A anotação `@EnableJpaRepositories` indica que as interfaces de repositório JPA devem ser ativadas para permitir o acesso aos dados no MySQL. Ela especifica o pacote base onde os repositórios estão localizados (`br.com.datasource.domain.repository.mysql`) e também faz referência ao `entityManagerFactoryRef` e ao `transactionManagerRef`.

<br>

A classe possui três métodos anotados com `@Bean`, que são responsáveis por configurar e fornecer os beans gerenciados pelo Spring:

1. O método `mysqlDataSource()` configura e retorna um DataSource para o MySQL. Ele usa a anotação `@ConfigurationProperties` para definir as propriedades do banco de dados MySQL a serem lidas a partir do arquivo de propriedades do Spring (`application.properties`), utilizando o prefixo `spring.datasource.mysql`.

2. O método `mysqlEntityManagerFactory()` configura e retorna um `LocalContainerEntityManagerFactoryBean` para o MySQL. Ele recebe o `DataSource` configurado anteriormente e um `EntityManagerFactoryBuilder` como parâmetros. O `EntityManagerFactoryBuilder` é usado para construir o `EntityManagerFactory` com base nas configurações fornecidas. Nesse método, é especificado o pacote base onde as entidades JPA estão localizadas (`br.com.datasource.domain.model.mysql`).

3. O método `mysqlTransactionManager()` configura e retorna um `PlatformTransactionManager` para o MySQL. Ele recebe o `LocalContainerEntityManagerFactoryBean` configurado anteriormente como parâmetro e cria um `JpaTransactionManager` com base nesse `EntityManagerFactory`. O `JpaTransactionManager` é uma implementação do `PlatformTransactionManager` para uso com o JPA.

<br>

A anotação `@Primary` só é usada aqui porque o Spring precisa de um datasource que seja primário, caso contrário não funciona.
Esse ponto é importante pois apenas uma das classes de configuração deve ter essa anotação.

<br>

## Como testar

Passo-a-passo:

1. Para testar é necessário ter os bancos rodando na máquina local com o mesmo user e password do `application.properties`:

> POSTGRESQL

- Database: postgres
- Porta: 5432
- User: postgres
- Password: postgres

> MYSQL

- Database: mysql
- Porta: 3306
- User: root
- Password: root

> Para uma configuração mais fácil dos bancos de dados, é disponibilizado o arquivo `docker-compose.yml`.
> Para usá-lo, é só usar o comando `docker-compose up` no terminal, dentro da raiz do projeto.
> Dessa forma o Docker configurará todos os bancos com os dados informados anteriormente.

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
      "name": "Roberto"
    }

> **A Collection estará disponível junto do código fonte na raiz do projeto.**

## Tecnologias utilizadas

- Java 17
- Spring Boot 3.1.1
- Lombok
- MySQL
- PostgreSQL
