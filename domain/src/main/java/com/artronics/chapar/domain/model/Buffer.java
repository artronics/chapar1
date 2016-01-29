package com.artronics.chapar.domain.model;

import java.util.List;

public class Buffer {
    private List<Integer> content;

    public Buffer() {
    }

    public Buffer(List<Integer> content) {
        this.content = content;
    }

    public List<Integer> getContent() {
        return content;
    }

    public void setContent(List<Integer> content) {
        this.content = content;
    }

}
