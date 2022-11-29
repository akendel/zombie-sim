# Requirements
- Gradle
- Java 17

# Getting Started
This programm simulates a combat between zombies and survivors.
```
usage: ./gradlew bootRun --args='--survivors 100000 --survivorAccuracy 50 --zombies 100000 --zombieAccuracy 50'
 -s,--survivors <arg>           Number of survivors
 -sa,--survivorAccuracy <arg>   Accuracy survivor attacks
 -z,--zombies <arg>             Number of zombies
 -za,--zombieAccuracy <arg>     Accuracy zombie attacks

```
A detailed log file is written in the folder ``/logs``
