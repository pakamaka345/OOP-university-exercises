package com.umcs.controllers;

import com.umcs.models.Image;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Controller
@RequestMapping("/imageform")
public class ImageFormController {
    @GetMapping("/")
    public String getMainPage(){
        return "index";
    }
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("image") MultipartFile imageFile, @RequestParam("brightness") String brightnessStr, Model model){
        try {
            int brightness = Integer.parseInt(brightnessStr);
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageFile.getBytes()));
            changeBrightness(bufferedImage, brightness);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            model.addAttribute("image", base64Image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "image";
    }

    private void changeBrightness(BufferedImage originalImage, int brightness) {
        for (int y = 0; y < originalImage.getHeight(); y++){
            for (int x = 0; x < originalImage.getWidth(); x++){
                Color color = new Color(originalImage.getRGB(x, y));
                int r = clamp(color.getRed() + brightness);
                int g = clamp(color.getGreen() + brightness);
                int b = clamp(color.getBlue() + brightness);
                Color newColor = new Color(r, g, b);
                originalImage.setRGB(x, y, newColor.getRGB());
            }
        }
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(value, 255));
    }
}
