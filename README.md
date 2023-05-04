<h1>Adega de vinhos com Spring Boot</h1>

Trata-se de uma API construída com Spring Boot. Ela faz o CRUD, persistindo as informações num banco de dados MySql, que roda em um container no Docker.

O projeto possui testes unitários usando H2 e Mockito e testes de integração para a classe de controller.

Criei um CI para que, ao receber um novo commit na main, sejam rodados vários testes de modo a garantir que as novas alterações não quebram nada do que já existe no projeto.

Criei um SecuretyConfig, mas na versão final do projeto achei melhor deixar sem essa opção, pelo menos por enquanto. Estou optando por desenvolver mais coisas nessa API, incluindo outras entidades e voltarei a minha atenção a essa parte do Spring Security quando o restante do projeto estiver finalizado.