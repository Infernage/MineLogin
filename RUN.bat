@echo off



if not exist "C:\Program Files\Java\jre6\bin\java.exe" goto JAVA32
echo Ejecutando Minecraft (x64)
"C:\Program Files\Java\jre6\bin\java.exe" -Xmx1024M -Xms1024M -jar Mineshafter-proxy.jar
goto END

:JAVA32
if not exist "C:\Program Files (x86)\Java\jre6\bin\java.exe" goto JAVAXP
echo Ejecutando Minecraft (x86)
"C:\Program Files (x86)\Java\jre6\bin\java.exe" -Xmx1024M -Xms1024M -jar Mineshafter-proxy.jar
goto END

:JAVAXP
if not exist "C:\Archivos de Programa\Java\jre6\bin\java.exe" goto JAVAMISSING
echo Ejecutando Minecraft (x86)
"C:\Program Files (x86)\Java\jre6\bin\java.exe" -Xmx1024M -Xms1024M -jar Mineshafter-proxy.jar
goto END

:JAVAMISSING
echo No has instalado correctamente la version de Java.
echo Dirigete a http://java.sun.com para instalar Java 6.
pause

:END
echo Cerrado correctamente