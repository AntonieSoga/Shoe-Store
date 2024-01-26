package com.aciee.shoeStore.controller;

import com.aciee.shoeStore.model.ShoesEntity;
import com.aciee.shoeStore.service.ShoesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
public class AdminController {
    private final ShoesService shoesService;

    public AdminController(ShoesService shoesService) {
        this.shoesService = shoesService;
    }

    @GetMapping("")
    public String adminPage(Model model) {
        model.addAttribute("shoes", shoesService.getAllShoes());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")))
            return "admin";
        return "redirect:/clients";
    }

    @PostMapping("/import")
    public String importShoes(@RequestParam("file") MultipartFile file) {
        try {
            shoesService.importShoes(file);
            log.info("Shoes imported successfully.");
        } catch (Exception e) {
            log.error("Error importing shoes.", e);
        }
        return "redirect:/admin"; // Redirect back to the admin page
    }

    @GetMapping("/export")
    public String exportShoes(@RequestParam String format, Model model) {
        String exportDirectory = "exports/";
        String fileName = "shoes_export";

        if ("pdf".equals(format)) {
            String pdfFilePath = exportDirectory + "pdf/" + fileName + ".pdf";
            shoesService.exportShoesToPdf(pdfFilePath);
        } else if ("xls".equals(format) || "xlsx".equals(format)) {
            String excelFilePath = exportDirectory + "excel/" + fileName + ".xlsx";
            shoesService.exportShoesToExcel(excelFilePath);
        } else {
            log.warn("Unsupported export format: " + format);
        }

        model.addAttribute("showConfirmation", true);  // Add this attribute to show confirmation dialog
        return "redirect:/admin";
    }

    @PostMapping("/deleteShoe/{shoeId}")
    public String deleteShoe(@PathVariable Long shoeId) {
        try {
            shoesService.deleteShoe(shoeId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "redirect:/admin";
    }

    @GetMapping("/editShoe")
    public String showEditShoePage(@RequestParam Long shoeId, Model model) {
        ShoesEntity shoe = shoesService.getShoeById(shoeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid shoe ID: " + shoeId));

        model.addAttribute("shoeId", shoe.getShoeId());
        model.addAttribute("brand", shoe.getBrand());
        model.addAttribute("model", shoe.getModel());
        model.addAttribute("size", shoe.getSize());
        model.addAttribute("color", shoe.getColor());
        model.addAttribute("price", shoe.getPrice());
        model.addAttribute("quantity", shoe.getQuantity());
        model.addAttribute("imageUrl", shoe.getImageUrl());

        return "editShoe";
    }

    @PostMapping("/editShoe")
    public String editShoe(@RequestParam Long shoeId,
                           @RequestParam String brand,
                           @RequestParam String model,
                           @RequestParam Float size,
                           @RequestParam String color,
                           @RequestParam Double price,
                           @RequestParam Integer quantity,
                           @RequestParam String imageUrl) {

        ShoesEntity editedShoe = new ShoesEntity();
        editedShoe.setShoeId(shoeId);
        editedShoe.setBrand(brand);
        editedShoe.setModel(model);
        editedShoe.setSize(size);
        editedShoe.setColor(color);
        editedShoe.setPrice(price);
        editedShoe.setQuantity(quantity);
        editedShoe.setImageUrl(imageUrl);

        shoesService.updateShoe(shoeId, editedShoe);

        return "redirect:/admin";
    }

    @GetMapping("/insert")
    public String showInsertShoePage() {
        return "insert";
    }

    @PostMapping("/insertShoe")
    public String insertShoe(@RequestParam String brand,
                             @RequestParam String model,
                             @RequestParam Float size,
                             @RequestParam String color,
                             @RequestParam Double price,
                             @RequestParam Integer quantity,
                             @RequestParam String imageUrl) {

        ShoesEntity newShoe = new ShoesEntity();
        newShoe.setBrand(brand);
        newShoe.setModel(model);
        newShoe.setSize(size);
        newShoe.setColor(color);
        newShoe.setPrice(price);
        newShoe.setQuantity(quantity);
        newShoe.setImageUrl(imageUrl);

        shoesService.createShoe(newShoe);

        return "redirect:/admin";
    }

    @GetMapping("/reports")
    public String showReportsPage(Model model) {
        List<ShoesEntity> inventory = shoesService.getAllShoes();
        model.addAttribute("inventory", inventory);
        return "reports";
    }
}
