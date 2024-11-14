# Etapa de construção (Build Stage)
FROM maven:3-openjdk-17 AS builder
LABEL author="Winyc"

# Definindo o diretório de trabalho para a construção
WORKDIR /build

# Copiando apenas os arquivos de configuração do Maven para cache eficiente
COPY pom.xml ./
COPY src ./src

# Fazendo o build do projeto, ignorando testes e checkstyle
RUN mvn clean package -DskipTests -Dcheckstyle.skip=true

# Etapa de execução (Run Stage)
FROM openjdk:17-jdk-slim

# Definindo o diretório de trabalho para a execução
WORKDIR /app

# Copiando o arquivo JAR gerado da etapa anterior
COPY --from=builder /build/target/*.jar app.jar

# Expondo a porta padrão da aplicação (alterar conforme necessidade)
EXPOSE 8080

# Definindo o comando de inicialização
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
