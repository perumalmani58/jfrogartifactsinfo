# jfrogartifactsinfo

<h4>To Pull the Top 2 Downloaded Mvn Artifacts (jar) from the given Artifacts Repository</h4>

<b>Java Required Arguments</b>: <i>artifactoryUrl repositoryName username password</i>

Example running the jar with Dependencies: 

java -jar jfrog-artifactsinfo-1.7-SNAPSHOT-jar-with-dependencies.jar http://serverName/artifactory jcenter-cache userName password

Example running the main class directly: 

java MostDownloadedArtifacts http://serverName/artifactory jcenter-cache userName password
