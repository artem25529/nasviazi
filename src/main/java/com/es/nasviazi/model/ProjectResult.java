package com.es.nasviazi.model;

import jakarta.persistence.*;

@Entity(name = "ProjectResult")
@Table(name = "project_results")
public class ProjectResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String project;

    private String username;

    private String date;

    @Lob
    @Column(name = "file", columnDefinition = "BLOB")
    private byte[] file;

    public ProjectResult() {
    }

    public ProjectResult(String project, String username, String date) {
        this.project = project;
        this.username = username;
        this.date = date;
    }

    public ProjectResult(String project, String username, String date, byte[] file) {
        this.project = project;
        this.username = username;
        this.date = date;
        this.file = file;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }
}
