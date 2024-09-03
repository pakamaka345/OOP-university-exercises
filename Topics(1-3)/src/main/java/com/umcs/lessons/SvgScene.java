package com.umcs.lessons;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class SvgScene {
    private static SvgScene instance = null;
    private static List<String> defs;
    private static List<Shape> shapes;
    public static SvgScene getInstance(){
        if (instance == null){
            instance = new SvgScene();
        }
        if (defs == null){
            defs = new ArrayList<>();
        }
        if (shapes == null){
            shapes = new ArrayList<>();
        }
        return instance;
    }
    public void addShape(Shape shape){
        shapes.add(shape);
    }
    public void addDef(String def){
        defs.add(def);
    }
    public void saveHtml(String filename){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
            bufferedWriter.write("<html> \n    <head> \n        <title>Paint</title> \n    </head> \n    <body> \n");
            bufferedWriter.write("        <svg width=\"500\" height=\"500\" xmlns=\"http://www.w3.org/2000/svg\"> \n");
            bufferedWriter.write("            <defs> \n");
            for (String def : defs){
                bufferedWriter.write("                " + def + "\n");
            }
            bufferedWriter.write("            </defs> \n");
            for (Shape polygon : shapes){
                bufferedWriter.write("            " + polygon.toSvg("") + "\n");
            }
            bufferedWriter.write("        </svg> \n    </body> \n</html>");
            bufferedWriter.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
