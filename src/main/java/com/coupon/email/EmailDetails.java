package com.coupon.email;

import java.util.HashMap;
import java.util.Map;

import javax.activation.DataSource;

public class EmailDetails {
    private String[] toEmail;
    private String templatePath;
    private String title;
    private Map<String, Object> details = new HashMap<>();
    private String content;
    private Map<String, DataSource> attachments = new HashMap<>();
    private String[] cc;

    public String[] getToEmail() {
        return toEmail;
    }

    public void setToEmail(final String[] toEmail) {
        this.toEmail = toEmail;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(final String templatePath) {
        this.templatePath = templatePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(final Map<String, Object> details) {
        this.details = details;
    }

    public Map<String, DataSource> getAttachments() {
        return attachments;
    }

    public void setAttachments(final Map<String, DataSource> attachments) {
        this.attachments = attachments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(final String[] cc) {
        this.cc = cc;
    }
}
