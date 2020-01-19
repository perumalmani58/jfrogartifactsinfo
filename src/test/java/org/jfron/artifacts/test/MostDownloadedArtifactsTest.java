package org.jfron.artifacts.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.apache.http.conn.HttpHostConnectException;
import org.jfrog.artifactory.client.Artifactory;
import org.jfrog.artifactory.client.ArtifactoryRequest;
import org.jfrog.artifactory.client.ArtifactoryResponse;
import org.jfrog.artifactory.client.impl.ArtifactoryResponseImpl;
import org.jfrog.artifacts.MostDownloadedArtifacts;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MostDownloadedArtifactsTest {

    MostDownloadedArtifacts mostDownloadedArtifacts = new MostDownloadedArtifacts();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidArguments() throws Exception {
        mostDownloadedArtifacts.getTopTwoDownloadedArtifacts("", "", "");
    }

    @Test
    public void testInvalidHost() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Exception while retrieving the top two doanloaded artifacts");
        mostDownloadedArtifacts.getTopTwoDownloadedArtifacts("http://127.0.0.1/artifactor", "test", "test");
    }
}
