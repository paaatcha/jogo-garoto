# Instruções de uso

1. Configurar o banco de dados
+ O nome padrão do banco está `garoto`, mas você pode mudar no applications.properties
+ Lembre de setar login e senha do banco

2. Você pode usar a API no http://localhost:8080/swagger-ui.html
+ Vai no `entry-controller` que já está mastigado

3. As estatisticas são:
+ `/api/stats/getChocoFreq`: Retorna todas as frequências de todos os chocolates 
+ `/api/stats/getChocoFreqByMonth`: Dado um nome de chocolate, retorna a frequência mensal do mesmo
+ `/api/stats/getChocoFreqByDayOfWeek`: Dado um nome de chocolate, retorna a frequência por dia da semana do mesmo
+ `/api/stats/getFreqByMonth`: Retorna a frequências de todos os chocolates para um dado mês (mês = `int` de 1 a 12)
+ `/api/stats/getFreqByDayOfWeek`: Retorna a frequências de todos os chocolates para um dado dia da semana (dia da semana = `string` com dia em inglês, ex: "tuesday")