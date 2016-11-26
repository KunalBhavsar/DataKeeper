package com.jyotitech.passwordskeeper.models;

/**
 * Created by Kunal on 06/11/16.
 */

public class PasswordContent {
    private String title;
    private String content;

    public PasswordContent() {

    }

    public PasswordContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
