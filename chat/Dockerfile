# ETAPA 1: Construcción (Build)
# Utilizamos una imagen de Maven con Java 21
FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app

# 1. Copia solo los archivos necesarios para descargar dependencias
# Esto optimiza el caché de Docker si solo cambias el código fuente.
COPY pom.xml .
COPY .mvn .mvn
# Corregido: Asegura que el nombre sea correcto (mvnw, no mvvn)
COPY mvnw .
COPY mvnw.cmd .
COPY src src

# Permisos de ejecución para el wrapper
RUN chmod +x mvnw

# Descarga de dependencias
# Nota: La carpeta 'src' debe estar copiada antes de este paso si tienes
# archivos de configuración en 'src/main/resources' que 'go-offline' necesita.
# Si solo necesitas el pom.xml para 'go-offline', mueve 'COPY src src' abajo.
RUN ./mvnw dependency:go-offline -B

# 2. Compilación y empaquetado del código
# La carpeta 'src' ya está copiada.
RUN ./mvnw clean package -DskipTests -B

---
# ETAPA 2: Imagen de Producción (Runtime)
# Imagen base más ligera para producción con Java 21
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app

# Crea usuario y grupo no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring

# 3. Copia el JAR generado
# Nota: Ajusta 'chat-0.0.1-SNAPSHOT.jar' si tu nombre de JAR es diferente.
COPY --from=build /app/target/chat-0.0.1-SNAPSHOT.jar /app/api-v1.jar

# Configuración de permisos y usuario
RUN chown spring:spring /app/api-v1.jar
USER spring

# Exposición del puerto
EXPOSE 8080

# Variables de entorno
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Punto de entrada para la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/api-v1.jar"]