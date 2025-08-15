# Usar a imagem baseada em Debian, que suporta o JDK 21
FROM eclipse-temurin:21-jdk

# Atualizar os pacotes e instalar o Maven
RUN apt-get update && apt-get install -y maven

# Definir o diretório de trabalho no container
WORKDIR /app

# Copiar o código-fonte para dentro do container
COPY . .

# Expor a porta da aplicação
EXPOSE 8080

# Comando para rodar a aplicação em modo desenvolvimento
CMD ["mvn", "spring-boot:run"]
