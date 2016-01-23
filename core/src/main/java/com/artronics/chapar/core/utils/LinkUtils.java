package com.artronics.chapar.core.utils;

import com.artronics.chapar.core.entities.Link;

import java.util.HashSet;
import java.util.Set;

public class LinkUtils {

    public static Set<Link> merge(Set<Link> srcLinks,Set<Link> dstLinks,Set<Link> addedLinks){
        Set<Link> mergedLinks = new HashSet<>();

        if (addedLinks == null) {
            addedLinks = new HashSet<>();
        }
        else {
            addedLinks.clear();
        }

        //First update merged links
        mergedLinks.addAll(dstLinks);
        mergedLinks.addAll(srcLinks);
        //new check if weight is different

        //make a copy of dst
        Set<Link> tempDstLinks = new HashSet<>(dstLinks);
        //remove what is common between src and dst
        tempDstLinks.removeAll(srcLinks);
        //add what is not present in srsLinks
        addedLinks.addAll(tempDstLinks);

        return mergedLinks;
    }

    public static Set<Link> merge(Set<Link> srcLinks,Set<Link> dstLinks){
        return merge(srcLinks,dstLinks,null);
    }
}
