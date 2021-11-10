# Uso l'immagine maven (contiene Java e contiene Maven)
# La parola chiave AS build-stage significa che l'immagine viene scaricata
# Poi viene usata per fare cose e poi viene "dimenticata"
FROM maven:latest AS build-stage
# Creo una directory di lavoro /app
WORKDIR /app
# Copio dentro /app il pom.xml
COPY pom.xml /app/
# Copio tutto il contenuto della cartella src dentro /app/src/
COPY src /app/src/
# Eseguo il comando maven NB: --file /app perché il POM non si trova qui ma dentro /app
# package perché non ho bisogno di fare clean, la cartella target non esiste
RUN mvn --file /app package

# Questo è quasi lo stesso Dockerfile di simple
FROM openjdk:11
# L'unica differenza è che il file da COPY non è nel filesystem dell'host
# ma si trova dentro l'immagine identificata con "AS build-stage"
COPY --from=build-stage /app/target/j20-biblioteca-1.0-SNAPSHOT.jar ./
ENTRYPOINT ["java", "-jar", "j20-biblioteca-1.0-SNAPSHOT.jar"]
