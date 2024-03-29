package org.dionthorn.lifesimrpg;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Map extends Entity {

    private final String name;
    private final ArrayList<Place> places = new ArrayList<>();

    public Map(String mapName) {
        // load map data from resources ex input: Vanillaton
        this.name = mapName;
        String[] fileLines;
        if(FileOpUtils.JRT) {
            fileLines = FileOpUtils.getFileLines(URI.create(FileOpUtils.jrtBaseURI + "Maps/" + mapName + "/" + mapName + ".map"));
        } else {
            fileLines = FileOpUtils.getFileLines(URI.create(getClass().getResource("/Maps") + mapName + "/" + mapName + ".map"));
        }
        Place target = null;

        // loop through all fileLines
        for(String line: fileLines) {
            // $ indicates we want to connect the following lines to this location
            if(line.contains("$")) {
                String targetName = line.replaceAll("\\$", "");
                boolean isNew = true;
                for(Place place: places) {
                    if(place.getName().equals(targetName)) {
                        isNew = false;
                        target = place;
                    }
                }
                if(isNew) {
                    if(FileOpUtils.JRT) {
                        target = new Place(FileOpUtils.jrtBaseURI + "Maps/" + mapName + "/" + targetName);
                    } else {
                        target = new Place(getClass().getResource("/Maps") + mapName + "/" + targetName);
                    }
                    places.add(target);
                }
            } else {
                // if no $ then we connect this location to the last $ location
                if(line.contains(":END")) {
                    // EOF
                    break;
                } else if(!line.contains(":")) {
                    // check if connection is new or not
                    boolean isNew = true;
                    Place notNew = null;
                    for(Place place: places) {
                        if(place.getName().equals(line)) {
                            isNew = false;
                            notNew = place;
                            break;
                        }
                    }
                    if(isNew && target != null) {
                        // if a new one make a new one and add connections to target
                        Place newPlace;
                        if(FileOpUtils.JRT) {
                            newPlace = new Place(FileOpUtils.jrtBaseURI + "Maps/" + mapName + "/" + line);
                        } else {
                            newPlace = new Place(getClass().getResource("/Maps") + mapName + "/" + line);
                        }
                        places.add(newPlace);
                        target.addConnection(newPlace);
                        newPlace.addConnection(target);
                    } else if(target != null) {
                        // if not new then just establish connection
                        target.addConnection(notNew);
                        notNew.addConnection(target);
                    }
                }
            }
        }
    }

    // getters and setters

    public String getName() {
        return name;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    // returns a ArrayList of all character object in every Place object in the places ArrayList
    public ArrayList<Character> getAllCharacters() {
        return places.stream().flatMap(p -> p.getCharacters().stream()).collect(Collectors.toCollection(ArrayList::new));
    }

}
