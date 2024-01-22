package v19;

import dev.ResourceType;
import battlecode.common.*;

// Other imports if needed

public class WellInfo {
    private MapLocation mapLocation;
    private ResourceType resourceType;
    // Other properties

    public WellInfo(MapLocation mapLocation, ResourceType resourceType) {
        this.mapLocation = mapLocation;
        this.resourceType = resourceType;
    }

    // Getters and setters
    public MapLocation getMapLocation() {
        return mapLocation;
    }

    public void setMapLocation(MapLocation mapLocation) {
        this.mapLocation = mapLocation;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    // Other methods and properties
}