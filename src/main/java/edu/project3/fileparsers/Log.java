package edu.project3.fileparsers;

import java.time.OffsetDateTime;
import java.util.Objects;

@SuppressWarnings("ParameterNumber")
public class Log {
    private String remoteAddress;
    private String remoteUser;
    private OffsetDateTime date;
    private String method;
    private String pathToResource;
    private String resource;
    private String protocol;
    private int status;
    private int bodyBytesSent;
    private String httpReferer;
    private String httpUserAgent;

    public Log(
        String remoteAddress,
        String remoteUser,
        OffsetDateTime date,
        String request,
        int status,
        int bodyBytesSent,
        String httpReferer,
        String httpUserAgent
    ) {
        this.remoteAddress = remoteAddress;
        this.remoteUser = remoteUser;
        this.date = date;
        splitRequest(request);
        this.status = status;
        this.bodyBytesSent = bodyBytesSent;
        this.httpReferer = httpReferer;
        this.httpUserAgent = httpUserAgent;
    }

    private void splitRequest(String request) {
        String[] partsOfTheRequest = request.split(" ");
        this.method = partsOfTheRequest[0];
        this.protocol = partsOfTheRequest[2];
        String path = partsOfTheRequest[1];
        int slashIndex = path.lastIndexOf('/');
        if (slashIndex == -1) {
            this.pathToResource = "";
            this.resource = path;
        } else {
            this.pathToResource = path.substring(0, slashIndex);
            this.resource = path.substring(slashIndex);
        }
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public String getMethod() {
        return method;
    }

    public String getPathToResource() {
        return pathToResource;
    }

    public String getResource() {
        return resource;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getStatus() {
        return status;
    }

    public int getBodyBytesSent() {
        return bodyBytesSent;
    }

    public String getHttpReferer() {
        return httpReferer;
    }

    public String getHttpUserAgent() {
        return httpUserAgent;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Log log = (Log) o;
        return status == log.status && bodyBytesSent == log.bodyBytesSent
            && Objects.equals(remoteAddress, log.remoteAddress) && Objects.equals(remoteUser, log.remoteUser)
            && Objects.equals(date, log.date) && Objects.equals(method, log.method)
            && Objects.equals(pathToResource, log.pathToResource) && Objects.equals(resource, log.resource)
            && Objects.equals(protocol, log.protocol) && Objects.equals(httpReferer, log.httpReferer)
            && Objects.equals(httpUserAgent, log.httpUserAgent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            remoteAddress,
            remoteUser,
            date,
            method,
            pathToResource,
            resource,
            protocol,
            status,
            bodyBytesSent,
            httpReferer,
            httpUserAgent
        );
    }
}
