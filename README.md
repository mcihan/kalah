## Kalah game project
This is  Kalah game project with 6 pits and in each one 6 stones,
but it's configurable, you can change it to **n** pits and **m** stones.
To change them please have a look to service.properties file.
Also enterGame can be changed to receive pits and stones count.

## Run
This project can be run by two ways:

1. Run controller module (com.backbase.kalah.KalahApplication class) from your IDE.

2. Build controller module by `mvn clean package` command and 
then run generated *.jar file by `java -jar controller-${version}.jar`

**Note** if you don't have installed maven instead of mvn use mvnw or mvnw.cmd depends on your OS.

**Note** instead of ${version} should be written right version of the project. E.g. 0.0.1-SNAPSHOT

No database installation is needed, as for this test project is chosen in memory H2 DB. 

## Usage
Aster running the project it can be used by any rest client or swagger.
You can open Swagger by this link: **http://localhost:8080/swagger-ui.html**

To play two player should enter to game by POST:/games endpoint. Endpoint is returning
GameBean. Save playerId from returned json. Game can start when there are two entered players.
This is multyplayer, each two players can start the separate game.
**startGame** field is showing whether second player is joined to the game.
If it's **false** call GET:/games endpoint by providing playerId till the **startGame** field change to **true**.
To play call POST:/games/play by providing playerId and position in range [0,5].
**myTurn** field is showing weather it's player turn or not. When it's **false** call GET:/GAMES by providing playerId
till it change to **true**. **finished** field is showing is game finished or not.