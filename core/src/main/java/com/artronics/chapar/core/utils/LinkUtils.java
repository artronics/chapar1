package com.artronics.chapar.core.utils;

import com.artronics.chapar.core.entities.Link;

import java.util.HashSet;
import java.util.Set;

public class LinkUtils {

    public Set<Link> merge(Set<Link> srcLinks,Set<Link> dstLinks,Set<Link> addedLinks){
        Set<Link> mergedLinks = new HashSet<>();

        if (addedLinks == null) {
            addedLinks = new HashSet<>();
        }
        else {
            addedLinks.clear();
        }

        return mergedLinks;
    }

    public Set<Link> merge(Set<Link> srcLinks,Set<Link> dstLinks){
        return merge(srcLinks,dstLinks,null);
    }
}
