package com.artronics.chapar.domain.utilities;

import com.artronics.chapar.domain.entities.SensorLink;

import java.util.HashSet;
import java.util.Set;

public class SensorLinkUtils {

    public static Set<SensorLink> merge(Set<SensorLink> oldLinks, Set<SensorLink> newLinks, Set<SensorLink> removedLinks){
        if (removedLinks == null) {
            removedLinks = new HashSet<>();
        }
        else {
            removedLinks.clear();
        }
        Set<SensorLink> mergedSensorLinks = new HashSet<>();

        //First update merged links
        mergedSensorLinks.addAll(newLinks);
        //second we add old links. union of sets is not added again. so value of
        //weight got updated
        mergedSensorLinks.addAll(oldLinks);

        removedLinks.addAll(oldLinks);
        removedLinks.removeAll(newLinks);

        return mergedSensorLinks;
    }

    public static Set<SensorLink> merge(Set<SensorLink> oldSensorLinks, Set<SensorLink> newSensorLinks){
        return merge(oldSensorLinks, newSensorLinks,null);
    }
}
