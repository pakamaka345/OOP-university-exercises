package com.example.NikitaLysiuk.controller;

import com.example.NikitaLysiuk.model.Pixel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import static com.example.NikitaLysiuk.controller.RegisterController.image;

@Controller
public class ImageController {
    @GetMapping("/image")
    public String getImage(Model model) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        System.out.println(base64Image + " image ");
        model.addAttribute("image", base64Image);
        return "image";
    }

}
