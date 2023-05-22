package com.es.nasviazi.model;

import java.util.List;

public class GoogleData {
    private String project;
    private String advertCampaign;
    private String targetUrl;
    private String keywords;
    private List<String> visibleUrls;
    private List<String> additionalHeaders;
    private List<String> positions;
    private List<String> descriptions;

    public GoogleData() {
    }

    public GoogleData(String project, String advertCampaign, String targetUrl, String keywords) {
        this.project = project;
        this.advertCampaign = advertCampaign;
        this.targetUrl = targetUrl;
        this.keywords = keywords;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getAdvertCampaign() {
        return advertCampaign;
    }

    public void setAdvertCampaign(String advertCampaign) {
        this.advertCampaign = advertCampaign;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<String> getVisibleUrls() {
        return visibleUrls;
    }

    public void setVisibleUrls(List<String> visibleUrls) {
        this.visibleUrls = visibleUrls;
    }

    public List<String> getAdditionalHeaders() {
        return additionalHeaders;
    }

    public void setAdditionalHeaders(List<String> additionalHeaders) {
        this.additionalHeaders = additionalHeaders;
    }

    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }
}
