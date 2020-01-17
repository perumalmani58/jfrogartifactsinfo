package com.test.artifacts;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;
import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.ArtifactoryClientBuilder;
import org.jfrog.artifactory.client.ArtifactoryRequest;
import org.jfrog.artifactory.client.ArtifactoryResponse;
import org.jfrog.artifactory.client.impl.ArtifactoryRequestImpl;

import com.test.model.RepoItem;
import com.test.model.RepoResponse;

public class MostDownloadedArtifacts {

    private static String userName = "admin";
    private static String password = "VXoyYWN045";
    private static String artifactoryUrl = "http://35.239.232.218/artifactory/";


    public static void main(String[] args) throws Exception {
        Artifactory artifactory = createArtifactory(userName, password, artifactoryUrl);

        if (artifactory == null) {
            throw new RuntimeException("artifactory creation failed");
        }

        ArtifactoryRequest repositoryRequest = new ArtifactoryRequestImpl().apiUrl("api/search/aql")
              .method(ArtifactoryRequest.Method.POST)
              .requestBody("items.find( { \"repo\": {\"$eq\":\"jcenter-cache\"},\"name\" : {\"$match\":\"*.jar\"} })" +
                    ".include(\"path\", \"name\", \"stat.downloads\")")
              .responseType(ArtifactoryRequest.ContentType.JSON);

        ArtifactoryResponse response = artifactory.restCall(repositoryRequest);
        RepoResponse repoResponse = response.parseBody(RepoResponse.class);

        repoResponse.getResults().stream()
              .sorted(Comparator.comparingInt(RepoItem::getDownloads).reversed())
              .limit(2)
              .forEach(System.out::println);
    }


    private static Artifactory createArtifactory(String username, String password, String artifactoryUrl) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(artifactoryUrl)){
            throw new IllegalArgumentException("Arguments passed to createArtifactory are not valid");
        }

        return ArtifactoryClientBuilder.create()
              .setUrl(artifactoryUrl)
              .setUsername(username)
              .setPassword(password)
              .build();
    }
}