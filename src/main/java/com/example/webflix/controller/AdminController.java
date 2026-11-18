package com.example.webflix.controller;

import com.example.webflix.model.MediaContent;
import com.example.webflix.model.UserAccount;
import com.example.webflix.service.AuthService;
import com.example.webflix.service.MediaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final MediaService mediaService;
    private final AuthService authService;

    public AdminController(MediaService mediaService, AuthService authService) {
        this.mediaService = mediaService;
        this.authService = authService;
    }

    private boolean isAdmin(HttpSession session) {
        UserAccount user = (UserAccount) session.getAttribute("currentUser");
        return user != null && "ADMIN".equals(user.getRole());
    }

    @GetMapping
    public String dashboard(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bu alana erişmek için Yönetici (Admin) girişi yapmalısınız.");
            return "redirect:/giris";
        }

        model.addAttribute("contents", mediaService.findAll());
        model.addAttribute("movieCount", mediaService.findByType("FILM").size());
        model.addAttribute("seriesCount", mediaService.findByType("DIZI").size());
        model.addAttribute("userCount", authService.getAllUsers().size());
        model.addAttribute("suggestions", mediaService.getSuggestions());
        return "admin/dashboard";
    }

    @GetMapping("/yeni")
    public String createForm(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Yetkisiz erişim.");
            return "redirect:/giris";
        }

        model.addAttribute("content", new MediaContent());
        model.addAttribute("formTitle", "Yeni Film / Dizi Ekle");
        return "admin/form";
    }

    @GetMapping("/duzenle/{id}")
    public String editForm(@PathVariable Long id, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Yetkisiz erişim.");
            return "redirect:/giris";
        }

        MediaContent content = mediaService.findById(id);
        if (content == null) {
            return "redirect:/admin";
        }

        model.addAttribute("content", content);
        model.addAttribute("formTitle", "Yapımı Düzenle: " + content.getTitle());
        return "admin/form";
    }

    @PostMapping("/kaydet")
    public String saveContent(@ModelAttribute MediaContent content,
                              @RequestParam(required = false) String castCsv,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/giris";
        }

        boolean isNew = (content.getId() == null);
        mediaService.saveOrUpdate(content, castCsv);
        redirectAttributes.addFlashAttribute("successMessage",
                isNew ? "\"" + content.getTitle() + "\" kataloğa eklendi!"
                      : "\"" + content.getTitle() + "\" başarıyla güncellendi!");
        return "redirect:/admin";
    }

    @PostMapping("/sil/{id}")
    public String deleteContent(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/giris";
        }

        mediaService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Yapım katalogdan kalıcı olarak silindi.");
        return "redirect:/admin";
    }

    @PostMapping("/oneri-sil/{index}")
    public String deleteSuggestion(@PathVariable int index, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdmin(session)) {
            return "redirect:/giris";
        }

        mediaService.deleteSuggestion(index);
        redirectAttributes.addFlashAttribute("successMessage", "Öneri listeden kaldırıldı.");
        return "redirect:/admin";
    }
}