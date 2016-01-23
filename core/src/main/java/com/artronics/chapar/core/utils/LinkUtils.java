package com.artronics.chapar.core.utils;

import com.artronics.chapar.core.entities.Link;

import java.util.HashSet;
import java.util.Set;

public class LinkUtils {

    public static Set<Link> getRemovedLinks(Set<Link> oldSet, Set<Link> newSet){
        Set<Link> removedSet = new HashSet<>();


        return removedSet;
    }

    public static Set<Link> merge(Set<Link> oldLinks, Set<Link> newLinks, Set<Link> addedLinks){
        Set<Link> mergedLinks = new HashSet<>();

        if (addedLinks == null) {
            addedLinks = new HashSet<>();
        }
        else {
            addedLinks.clear();
        }

        //First update merged links
        mergedLinks.addAll(newLinks);
        mergedLinks.addAll(oldLinks);
        //new check if weight is different

        //make a copy of dst
        Set<Link> tempDstLinks = new HashSet<>(newLinks);
        //remove what is common between src and dst
        tempDstLinks.removeAll(oldLinks);
        //add what is not present in srsLinks
        addedLinks.addAll(tempDstLinks);

        return mergedLinks;
    }

    public static Set<Link> merge(Set<Link> oldLinks, Set<Link> newLinks){
        return merge(oldLinks, newLinks,null);
    }
}
