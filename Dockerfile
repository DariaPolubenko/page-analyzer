FROM gradle:8.7.0-jdk21

WORKDIR /app

COPY / .

RUN gradle installDist

EXPOSE 7070

CMD ./build/install/app/bin/app
