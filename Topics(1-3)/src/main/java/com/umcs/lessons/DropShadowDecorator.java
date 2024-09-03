package com.umcs.lessons;

import java.util.Locale;

public class DropShadowDecorator extends ShapeDecorator{
    private static int index = 0;
    public DropShadowDecorator(Shape shape){
        super(shape);
        SvgScene.getInstance().addDef(String.format(Locale.ENGLISH,
                "<filter id=\"f%d\" x=\"-100%%\" y=\"-100%%\" width=\"300%%\" height=\"300%%\">\n" +
                        "\t\t\t\t\t<feOffset result=\"offOut\" in=\"SourceAlpha\" dx=\"5\" dy=\"5\" />\n" +
                        "\t\t\t\t\t<feGaussianBlur result=\"blurOut\" in=\"offOut\" stdDeviation=\"5\" />\n" +
                        "\t\t\t\t\t<feBlend in=\"SourceGraphic\" in2=\"blurOut\" mode=\"normal\" />\n" +
                        "\t\t\t\t</filter>", index));
        index++;
    }
    public String toSvg(String parameters){
        return decoratedShape.toSvg(String.format(Locale.ENGLISH, "filter=\"url(#f%d)\" %s", index-1, parameters));
    }
}
