package com.umcs.lessons;

import java.util.Locale;

public class TransformationDecorator extends ShapeDecorator{
    private boolean translate, rotate, scale;
    private Vec2 translateVector, rotateCenter, scaleVector;
    private double rotateAngle;
    public TransformationDecorator(Shape shape){
        super(shape);
    }
    public String toSvg(String indent){
        String translate, rotate, scale;
        if(this.translate){
            translate = String.format(Locale.ENGLISH, "translate(%f %f)", this.translateVector.x, this.translateVector.y);
        }else translate = "";
        if(this.rotate){
            rotate = String.format(Locale.ENGLISH, "rotate(%f %f %f)", this.rotateAngle, this.rotateCenter.x, this.rotateCenter.y);
        }else rotate = "";
        if(this.scale){
            scale = String.format(Locale.ENGLISH, "scale(%f %f)", this.scaleVector.x, this.scaleVector.y);
        }else scale = "";

        return decoratedShape.toSvg(String.format(Locale.ENGLISH, "transform=\"%s %s %s\" %s", translate, rotate, scale, indent));
    }
    public static class Builder{
        private boolean translate = false, rotate = false, scale = false;
        private Vec2 translateVector, rotateCenter, scaleVector;
        private double rotateAngle;
        private Shape shape;
        public Builder translate(Vec2 translateVector){
            translate = true;
            this.translateVector = translateVector;
            return this;
        }
        public Builder rotate(Vec2 rotateCenter, double rotateAngle){
            rotate = true;
            this.rotateAngle = rotateAngle;
            this.rotateCenter = rotateCenter;
            return this;
        }
        public Builder scale(Vec2 scaleVector){
            scale = true;
            this.scaleVector = scaleVector;
            return this;
        }
        public Builder shape(Shape shape){
            this.shape = shape;
            return this;
        }
        public TransformationDecorator build(){
            TransformationDecorator obj = new TransformationDecorator(shape);
            obj.translate = translate;
            obj.rotate = rotate;
            obj.scale = scale;
            obj.translateVector = translateVector;
            obj.rotateCenter = rotateCenter;
            obj.scaleVector = scaleVector;
            obj.rotateAngle = rotateAngle;
            return obj;
        }
    }
}
