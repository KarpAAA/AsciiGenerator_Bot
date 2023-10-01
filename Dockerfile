FROM openjdk
ADD build/libs/AsciiGenerator_Bot-0.0.1-SNAPSHOT.jar AsciiGenerator_Bot.jar
ENTRYPOINT ["java", "-jar", "AsciiGenerator_Bot.jar"]
