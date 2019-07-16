set M2_HOME=E:\tools\apache-maven-3.5.4
set JAVA_HOME=E:\tools\jdk1.8.0_151
set PATH=%M2_HOME%\bin;%JAVA_HOME%\bin;%PATH%
set ECLIPSE_FOLDER=E:\eclipse\eclipse
set PROJECT_FOLDER=E:\Source_2\source\XACMLSMT.jar.src

doskey build=mvn clean install -DskipTests
doskey updateEclipse=mvn eclipse:eclipse
doskey ecl=%ECLIPSE_FOLDER%\eclipse.exe -clean -data "E:\EclipseWorkSpace\ResearchGroup\XACML_SMT"

cd %PROJECT_FOLDER%

cls
cmd