package com.example.webflix.controller;

import com.example.webflix.model.UserAccount;
import com.example.webflix.service.AuthService;
import com.example.webflix.service.MediaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
public class AuthController {

    private final AuthService authService;
    private final MediaService mediaService;

    public AuthController(AuthService authService, MediaService mediaService) {
        this.authService = authService;
        this.mediaService = mediaService;
    }

    @GetMapping("/giris")
    public String loginPage(@RequestParam(required = false) String cikis, Model model) {
        if (cikis != null) {
            model.addAttribute("infoMessage", "Başarıyla çıkış yaptınız.");
        }
        return "login";
    }

    @PostMapping("/giris")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        UserAccount user = authService.authenticate(username, password);
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Kullanıcı adı veya şifre hatalı!");
            return "redirect:/giris";
        }

        session.setAttribute("currentUser", user);

        if ("ADMIN".equals(user.getRole())) {
            return "redirect:/admin";
        }
        return "redirect:/";
    }

    @GetMapping("/kayit")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/kayit")
    public String doRegister(@RequestParam String fullName,
                             @RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (authService.usernameExists(username)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bu kullanıcı adı zaten alınmış.");
            return "redirect:/kayit";
        }

        UserAccount newUser = authService.registerUser(fullName, username, email, password);
        session.setAttribute("currentUser", newUser);
        return "redirect:/";
    }

    @GetMapping("/cikis")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/giris?cikis=true";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/hesabim")
    public String profilePage(HttpSession session, Model model) {
        UserAccount currentUser = (UserAccount) session.getAttribute("currentUser");
        Set<Long> watchlistIds = (Set<Long>) session.getAttribute("watchlist");
        model.addAttribute("user", currentUser);
        model.addAttribute("watchlistContents", mediaService.findByIds(watchlistIds));
        return "profile";
    }
}