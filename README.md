<h1>Adega de vinhos com Spring Boot</h1>

Trata-se de uma API construída com Spring Boot. Ela faz o CRUD, persistindo as informações em um banco de dados MySql, que roda em um container no Docker.

O projeto possui testes unitários usando H2 e Mockito.

Criei um CI para que, ao receber um novo commit na main, sejam rodados vários testes a fim de garantir que as novas alterações não quebram nada do que já existe no projeto.
