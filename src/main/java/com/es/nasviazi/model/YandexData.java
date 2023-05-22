package com.es.nasviazi.model;

import java.util.List;

public class YandexData {
    private String project;
    private String advertCampaign;
    private String targetUrl;
    private String keywords;
    private String visibleUrl;

    private List<Variant> variants;

    public YandexData(String project, String advertCampaign, String targetUrl, String keywords, String visibleUrl) {
        this.project = project;
        this.advertCampaign = advertCampaign;
        this.targetUrl = targetUrl;
        this.keywords = keywords;
        this.visibleUrl = visibleUrl;
    }

    public YandexData() {
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

    public String getVisibleUrl() {
        return visibleUrl;
    }

    public void setVisibleUrl(String visibleUrl) {
        this.visibleUrl = visibleUrl;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }
}
