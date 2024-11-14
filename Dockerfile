# Usar uma imagem base com Java 17
FROM openjdk:17-jdk-slim

# Configurar o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o arquivo JAR gerado pelo build do projeto para o container
COPY target/seu-aplicativo.jar app.jar

# Expor a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
