package org.jfrog.artifacts;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.ArtifactoryClientBuilder;
import org.jfrog.artifactory.client.ArtifactoryRequest;
import org.jfrog.artifactory.client.ArtifactoryResponse;
import org.jfrog.artifactory.client.impl.ArtifactoryRequestImpl;
import org.jfrog.model.RepoItem;
import org.jfrog.model.RepoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MostDownloadedArtifacts {
    private Logger log = LoggerFactory.getLogger(MostDownloadedArtifacts.class);

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            throw new IllegalArgumentException("Arguments Required, Usage: " +
                  "java MostDownloadedArtifacts.class <artifactoryUrl> <username> <password> \n" +
                  "Ex: java MostDownloadedArtifacts.class http://serverName/artifactory userName password");
        }
        MostDownloadedArtifacts mostDownloadedArtifacts = new MostDownloadedArtifacts();
        List<RepoItem> repoItems = mostDownloadedArtifacts
              .getTopTwoDownloadedArtifacts(args[0], args[1], args[2]);
        repoItems.forEach(System.out::println);
    }

    public List<RepoItem> getTopTwoDownloadedArtifacts(String artifactoryUrl, String userName, String password)
        throws Exception {
        Artifactory artifactory = createArtifactory(userName, password, artifactoryUrl);
        if (artifactory == null) {
            throw new RuntimeException("Artifactory creation failed");
        }

        ArtifactoryRequest repositoryRequest = new ArtifactoryRequestImpl().apiUrl("/api/search/aql")
              .method(ArtifactoryRequest.Method.POST)
              .requestBody("items.find( { \"repo\": {\"$eq\":\"jcenter-cache\"},\"name\" : {\"$match\":\"*.jar\"} })" +
                    ".include(\"path\", \"name\", \"stat.downloads\")")
              .responseType(ArtifactoryRequest.ContentType.JSON);

        try {
            ArtifactoryResponse response = artifactory.restCall(repositoryRequest);
            if (!response.isSuccessResponse()) {
                String errorMessage = "Exception while retrieving the top two doanloaded artifacts, " +
                      "Unable to connect to artifactory: " + response.getStatusLine().toString();
                log.error(errorMessage);
                throw new Exception(errorMessage);
            }
            RepoResponse repoResponse = response.parseBody(RepoResponse.class);
            return repoResponse.getResults().stream()
                  .sorted(Comparator.comparingInt(RepoItem::getDownloads).reversed())
                  .limit(2).collect(Collectors.toList());
        }
        catch (Exception e) {
            log.error("Exception while retrieving the top two doanloaded artifacts: " + e.getMessage());
            throw new Exception("Exception while retrieving the top two doanloaded artifacts: " + e.getMessage());
        }
    }

    private Artifactory createArtifactory(String username, String password, String artifactoryUrl) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(artifactoryUrl)){
            throw new IllegalArgumentException("Arguments passed are not valid");
        }

        return ArtifactoryClientBuilder.create()
              .setUrl(artifactoryUrl)
              .setUsername(username)
              .setPassword(password)
              .build();
    }
}