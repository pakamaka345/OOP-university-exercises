package com.umcs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AnalogClock extends Clock{
    private final List<ClockHand> timeList = List.of(new HourHand(), new MinuteHand(), new SecondHand());
    public AnalogClock(City city){
        super(city);
    }
    public void toSvg(String path){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))){
            bufferedWriter.write("<svg width=\"200\" height=\"200\" viewBox=\"-100 -100 200 200\" xmlns=\"http://www.w3.org/2000/svg\">\n" +
                    "  <!-- Tarcza zegara -->\n" +
                    "  <circle cx=\"0\" cy=\"0\" r=\"90\" fill=\"none\" stroke=\"black\" stroke-width=\"2\" />\n" +
                    "  <g text-anchor=\"middle\">\n" +
                    "    <text x=\"0\" y=\"-80\" dy=\"6\">12</text>\n" +
                    "    <text x=\"80\" y=\"0\" dy=\"4\">3</text>\n" +
                    "    <text x=\"0\" y=\"80\" dy=\"6\">6</text>\n" +
                    "    <text x=\"-80\" y=\"0\" dy=\"4\">9</text>\n" +
                    "  </g>\n");

            for (ClockHand hand : timeList){
                hand.setTime(super.getTime());
                bufferedWriter.write(hand.toSvg() + "\n");

            }
            bufferedWriter.write("</svg>");
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
