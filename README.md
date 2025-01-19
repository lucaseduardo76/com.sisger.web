# Sisger - Sistema de Gestão de Equipes

Sisger é o back-end de um sistema desenvolvido com Java Spring Boot para gestão de equipes. Este projeto permite a criação e gerenciamento de empresas, gerentes, funcionários e atribuição de tarefas.

## Tecnologias Utilizadas
- **Java**: Linguagem principal (requer JDK 17)
- **Spring Boot**: Framework para criação do back-end.
- **JPA (Java Persistence API)**: Para mapeamento e gerenciamento de banco de dados.
- **JWT (JSON Web Token)**: Para autenticação e autorização.
- **MySQL**: Banco de dados utilizado.
- **Swagger**: Para documentação dos endpoints da API.

## Funcionalidades
- **Gestão de Empresas**: Cadastro de empresas.
- **Gestão de Usuários**: Criação e gerenciamento de gerentes e funcionários.
- **Gestão de Tarefas**: Atribuição de tarefas para os funcionários.

## Pré-requisitos
Antes de rodar o projeto, certifique-se de ter:
1. **JDK 17** instalado.
2. **MySQL** em execução na porta padrão (3306).

## Configuração do Banco de Dados
1. Crie um banco de dados no MySQL:
   ```sql
   create database sisgerdb;
   ```
2. Abra o arquivo `application.yml` na pasta `src/main/resources`.
3. Configure o nome, caminho e credenciais do banco de dados, se necessário.

Exemplo de configuração padrão (`application.yml`):
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/sisgerdb
    username: seu_usuario
    password: sua_senha
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

## Como Rodar o Projeto
1. Clone o repositório:
   ```bash
   git clone https://github.com/lucaseduardo76/com.sisger.web.git
   ```
2. Acesse a pasta do projeto:
   ```bash
   cd com.sisger.web
   ```
3. Compile e rode o projeto:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Acesse a API através de `http://localhost:8080`.

## Documentação da API
A aplicação utiliza o **Swagger** para documentar os endpoints da API. Após iniciar o projeto, acesse a documentação através de:
- `http://localhost:8080/swagger-ui.html` ou `http://localhost:8080/swagger-ui/`

> **Nota**: A autenticação via JWT é necessária para acessar as rotas protegidas.

## Contribuição
Sinta-se à vontade para contribuir com este projeto! Abra uma issue ou envie um pull request.
