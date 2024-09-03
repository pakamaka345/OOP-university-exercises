package com.umcs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.umcs.model.Point;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapParser {
    static public final class Svg {
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("rect")
        private List<Map<String, String>> rects;
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("polygon")
        private List<Map<String, String>> polygons;
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("text")
        private List<Map<String, String>> texts;
        @JacksonXmlElementWrapper(useWrapping = false)
        @JsonProperty("circle")
        private List<Map<String, String>> circles;
    }

    private record Label(Point point, String text) {}
    private List<Label> labels = new ArrayList<>();
    private List<Land> lands = new ArrayList<>();
    private List<City> cities = new ArrayList<>();

    private void parseText(Map<String, String> params) {
        addLabel(params.get(""), new Point (Double.parseDouble(params.get("x")), Double.parseDouble(params.get("y"))));
    }

    private void addLabel(String text, Point bottomLeft) {
        labels.add(new Label(bottomLeft, text));
    }
    private void addLand(List<Point> points){
        lands.add(new Land(points));
    }
    private void addCity(Point center, String name, double lengthWall){
        cities.add(new City(center, name, lengthWall));
    }
    public List<Land> getLands(){
        return lands;
    }


    void matchLabelsToTowns() {
        // TODO Krok 14
    }

    void addCitiesToLands() {
        // TODO Krok 15
    }

    void parse(String path) {
        XmlMapper xmlMapper = new XmlMapper();
        File file = new File(path);
        try {
            Svg svg = xmlMapper.readValue(file, Svg.class);
            for(var item : svg.texts)
                parseText(item);
            // TODO: Krok 13
            matchLabelsToTowns();
            addCitiesToLands();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
