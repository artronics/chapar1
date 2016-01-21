package com.artronics.chapar.core.entities;

import org.apache.log4j.Logger;

import java.util.List;

public class Buffer {
    private final static Logger log = Logger.getLogger(Buffer.class);

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
