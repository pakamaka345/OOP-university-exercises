package com.umcs.controllers;

import com.umcs.models.Rectangle;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RectangleController {
    private List<Rectangle> rectangles = new ArrayList<>();
    @GetMapping("/rectangle")
    public Rectangle getRectangle() {
        return new Rectangle(20, 20, 150, 150, "red");
    }
    @PostMapping("/add")
    public int addRectangle(@RequestBody Rectangle rectangle) {
        rectangles.add(rectangle);

        return rectangles.size();
    }
    @GetMapping("/rectangles")
    public List<Rectangle> getRectangles() {
        return rectangles;
    }
    @GetMapping("/svg")
    public String getSvgRectangles() {
        StringBuilder svg = new StringBuilder("<svg xmlns=\"http://www.w3.org/2000/svg\">");

        for (Rectangle rectangle : rectangles) {
            svg.append(String.format("<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" style=\"fill:%s;\"/>",
                    rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), rectangle.getColor()));
        }

        svg.append("</svg>");

        return svg.toString();
    }
    @GetMapping("/rectangle/{id}")
    public Rectangle getRectangle(@PathVariable Long id) {
        if (id >= rectangles.size() || id < 0)
            return null;

        return rectangles.get(id.intValue());
    }
    @PutMapping("/update/{id}")
    public void updateRectangle(@PathVariable Long id, @RequestBody Rectangle rectangle) {
        if (id >= rectangles.size() || id < 0)
            return;

        rectangles.set(id.intValue(), rectangle);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteRectangle(@PathVariable Long id) {
        if (id >= rectangles.size() || id < 0)
            return;

        rectangles.remove(id.intValue());
    }
}
