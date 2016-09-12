cd target
java -Xmx4G -Xms512M -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -cp * com.game.main.InjectionLoader
pause