<h1>Adega de vinhos com Spring Boot</h1>

Trata-se de uma API construída com Spring Boot. Ela faz o CRUD, persistindo as informações num banco de dados MySql, que roda em um container no Docker.

O projeto possui testes unitários usando H2 e Mockito e testes de integração para a classe de controller.

Criei um CI para que, ao receber um novo commit na main, sejam rodados vários testes de modo a garantir que as novas alterações não quebram nada do que já existe no projeto.
