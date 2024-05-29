FROM gradle:8.7-jdk21

WORKDIR /

COPY / .

RUN gradle installDist

CMD ./build/install/BackendMySiteApi/bin/BackendMySiteApi --spring.profiles.active=production