package com.example.databasedemo.models;

import java.util.List;

public class Post {
    List<String> posts;

    public Post(List<String> posts) {
        this.posts = posts;
    }

    public Post() {
    }

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }
}
