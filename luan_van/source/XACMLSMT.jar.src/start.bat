set M2_HOME=C:\apache-maven-3.6.0
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_201
set PATH=%M2_HOME%\bin;%JAVA_HOME%\bin;%PATH%
set ECLIPSE_FOLDER=C:\Users\PC\eclipse\jee-2019-032\eclipse
set PROJECT_FOLDER=D:\ReseachAccessControl\source\XACMLSMT.jar.src

doskey build=mvn clean install -DskipTests
doskey updateEclipse=mvn eclipse:eclipse
doskey ecl=%ECLIPSE_FOLDER%\eclipse.exe -clean -data "C:\Users\PC\eclipse-workspace\ResearchGroup"

cd %PROJECT_FOLDER%

cls
cmd