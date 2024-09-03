package com.umcs.eegspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@Controller
public class ImageController {
    @GetMapping("/image")
    public String getImage(@RequestParam String username, @RequestParam int electrode, Model model) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\project\\java\\eeg\\egg\\src\\main\\resources\\usereeg.db")) {
            String selectDataSQL = "SELECT image FROM user_eeg WHERE username = ? AND electrode_number = ?";
            PreparedStatement statement = connection.prepareStatement(selectDataSQL);
            statement.setString(1, username);
            statement.setInt(2, electrode);

            String image = statement.executeQuery().getString("image");
            model.addAttribute("username", username);
            model.addAttribute("electrode", electrode);
            model.addAttribute("image", image);

            return "eegimage";
        } catch (Exception e) {
            System.err.println("Error getting image: " + e.getMessage());
            return "error";
        }
    }
}
