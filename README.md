# jfrogartifactsinfo

<h4>To Pull the Top 2 Downloaded Mvn Artifacts (jar) from the given Artifacts Repository</h4>

<b>Java Required Arguments</b>: <i>artifactoryUrl, repositoryName, username </i>

For Security Reasons Password is optional while running the Commd line/Console.

Example running the jar with Dependencies: 

java -jar jfrog-artifactsinfo-1.7-SNAPSHOT-jar-with-dependencies.jar http://serverName/artifactory jcenter-cache userName

Example running the main class directly: 

java MostDownloadedArtifacts http://serverName/artifactory jcenter-cache userName password

While Running from IDE, Console is not available to get the password input. So Please enter the Password as 4th argument in the IDE Program Arguments.
