# Helidon Quickstart SE

Sample Helidon SE project that includes multiple REST operations.

## Build and run

With JDK11+
```bash
mvn package
java -jar target/project3-helidon.jar
```

## Exercise the application

```
curl -X GET http://localhost:8080/greet
{"message":"Hello World!"}

curl -X GET http://localhost:8080/greet/Joe
{"message":"Hello Joe!"}

curl -X PUT -H "Content-Type: application/json" -d '{"greeting" : "Hola"}' http://localhost:8080/greet/greeting

curl -X GET http://localhost:8080/greet/Jose
{"message":"Hola Jose!"}
```

## Try health and metrics

```
curl -s -X GET http://localhost:8080/health
{"outcome":"UP",...
. . .

# Prometheus Format
curl -s -X GET http://localhost:8080/metrics
# TYPE base:gc_g1_young_generation_count gauge
. . .

# JSON Format
curl -H 'Accept: application/json' -X GET http://localhost:8080/metrics
{"base":...
. . .

```

## Build the Docker Image

```
docker build -t project3-helidon .
```

## Start the application with Docker

```
docker run --rm -p 8080:8080 project3-helidon:latest
```

Exercise the application as described above

## Deploy the application to Kubernetes

```
kubectl cluster-info                        # Verify which cluster
kubectl get pods                            # Verify connectivity to cluster
kubectl create -f app.yaml                  # Deploy application
kubectl get service helidon-quickstart-se   # Get service info
```

## Build a native image with GraalVM

GraalVM allows you to compile your programs ahead-of-time into a native
 executable. See https://www.graalvm.org/docs/reference-manual/aot-compilation/
 for more information.

You can build a native executable in 2 different ways:
* With a local installation of GraalVM
* Using Docker

### Local build

Download Graal VM at https://www.graalvm.org/downloads, the versions
 currently supported for Helidon are `20.1.0` and above.

```
# Setup the environment
export GRAALVM_HOME=/path
# build the native executable
mvn package -Pnative-image
```

You can also put the Graal VM `bin` directory in your PATH, or pass
 `-DgraalVMHome=/path` to the Maven command.

See https://github.com/oracle/helidon-build-tools/tree/master/helidon-maven-plugin#goal-native-image
 for more information.

Start the application:

```
./target/project3-helidon
```

### Multi-stage Docker build

Build the "native" Docker Image

```
docker build -t project3-helidon-native -f Dockerfile.native .
```

Start the application:

```
docker run --rm -p 8080:8080 project3-helidon-native:latest
```

## Build a Java Runtime Image using jlink

You can build a custom Java Runtime Image (JRI) containing the application jars and the JDK modules 
on which they depend. This image also:

* Enables Class Data Sharing by default to reduce startup time. 
* Contains a customized `start` script to simplify CDS usage and support debug and test modes. 
 
You can build a custom JRI in two different ways:
* Local
* Using Docker


### Local build

```
# build the JRI
mvn package -Pjlink-image
```

See https://github.com/oracle/helidon-build-tools/tree/master/helidon-maven-plugin#goal-jlink-image
 for more information.

Start the application:

```
./target/project3-helidon/bin/start
```

### Multi-stage Docker build

Build the "jlink" Docker Image

```
docker build -t project3-helidon-jlink -f Dockerfile.jlink .
```

Start the application:

```
docker run --rm -p 8080:8080 project3-helidon-jlink:latest
```

See the start script help:

```
docker run --rm project3-helidon-jlink:latest --help
```
