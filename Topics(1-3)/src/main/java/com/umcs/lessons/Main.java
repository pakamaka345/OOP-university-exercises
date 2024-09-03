package com.umcs.lessons;

public class Main {
    public static void main(String[] args) {
        /*Vec2 p1 = new Vec2(150, 50);
        Vec2 p2 = new Vec2(200, 100);

        Segment line = new Segment(p1, p2);
        System.out.println(line.toSvg());

        Vec2 p3 = new Vec2(5,5);
        Segment[] arr = Segment.perpendicural(line,p3);

        System.out.println("x= " + arr[0].getP2().x + " y= " + arr[0].getP2().y);
        System.out.println("xp= " + arr[1].getP2().x + " yp= " + arr[1].getP2().y);

        Vec2[] vec2s = new Vec2[4];
        vec2s[0] = p1;
        vec2s[1] = p2;
        vec2s[2] = new Vec2(200, 200);
        vec2s[3] = new Vec2(0, 200);

        Polygon square = Polygon.square(new Segment(new Vec2(50,50),new Vec2(100,100)), new Style("red","black",2));

        Polygon polygon = new Polygon(vec2s);
        SvgScene svgScene = new SvgScene();
        svgScene.add(polygon);
        svgScene.add(square);

        Ellipse ellipse = new Ellipse(new Vec2(250,250), 100,50,
                new Style("blue", "black", 5) );
        svgScene.add(ellipse);
        svgScene.save("out.html");
        System.out.println(polygon.toSvg(""));*/

//        Shape polygon = new Polygon(new Vec2[]{new Vec2(120,60),new Vec2(270,280),new Vec2(240,320), new Vec2(110,80)});
//        Shape ellipse = new Ellipse(new Vec2(300,400),200,50);
//
//        polygon = new SolidFillShapeDecorator(polygon,"red");
//        ellipse = new SolidFillShapeDecorator(ellipse,"green");
//        System.out.println(polygon.toSvg(""));
//        System.out.println(ellipse.toSvg(""));
//
//        polygon = new StrokeShapeDecorator(polygon, "black", 2);
//        System.out.println(polygon.toSvg(""));
//
//        ellipse = new TransformationDecorator.Builder()
//                .shape(ellipse)
//                .rotate(new Vec2(300, 400), 90)
//                .scale(new Vec2(2, 1))
//                .build();
//        System.out.println(ellipse.toSvg(""));
//        ellipse = new DropShadowDecorator(ellipse);
//        System.out.println(ellipse.toSvg(""));
//
//        Shape square = new Polygon(new Vec2[]{new Vec2(50,50), new Vec2(100, 50), new Vec2(100,100), new Vec2(50, 100)});
//        square = new TransformationDecorator.Builder()
//                .shape(square)
//                .rotate(new Vec2(75, 75), 45)
//                .build();
//        square = new GradientFillShapeDecorator.Builder()
//                .shape(square)
//                .addStop(0, "red")
//                .addStop(1, "blue")
//                .build();
//        square = new DropShadowDecorator(square);
//
//        SvgScene.getInstance().addShape(square);
//        SvgScene.getInstance().addShape(polygon);
//        SvgScene.getInstance().addShape(ellipse);
        Shape polygon = new Polygon(new Vec2[]{new Vec2(120,60),new Vec2(270,280),new Vec2(240,320), new Vec2(110,80)});
        ShapeDecorator solidFillShapeDecorator = new SolidFillShapeDecorator(polygon, "Red");

        SvgScene.getInstance().addShape(solidFillShapeDecorator);
        Shape ellipse = new SolidFillShapeDecorator(new Ellipse(new Vec2(300,400),200,50), "Blue");
        SvgScene.getInstance().addShape(ellipse);
        SvgScene.getInstance().saveHtml("out.html");

    }
}
