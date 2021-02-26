# get basic image with AWS JDK 1.8
FROM amazoncorretto:8

# copy artifacts and set workdir
COPY ./build/libs/hoolah-fat.jar /usr/app/
WORKDIR /usr/app/

# run container
ENTRYPOINT ["java", "-jar", "hoolah-fat.jar"]
