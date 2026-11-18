package com.example.webflix.controller;

import com.example.webflix.model.MediaContent;
import com.example.webflix.model.SuggestionForm;
import com.example.webflix.service.MediaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

@Controller
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @SuppressWarnings("unchecked")
    private Set<Long> getWatchlistIds(HttpSession session) {
        Set<Long> watchlist = (Set<Long>) session.getAttribute("watchlist");
        if (watchlist == null) {
            watchlist = new HashSet<>();
            session.setAttribute("watchlist", watchlist);
        }
        return watchlist;
    }

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute("featured", mediaService.findAll().get(0));
        model.addAttribute("movies", mediaService.findByType("FILM"));
        model.addAttribute("series", mediaService.findByType("DIZI"));
        model.addAttribute("watchlistIds", getWatchlistIds(session));
        return "index";
    }

    @GetMapping("/kesfet")
    public String catalog(@RequestParam(required = false) String tur,
                          @RequestParam(required = false) String kategori,
                          @RequestParam(required = false) String q,
                          Model model,
                          HttpSession session) {
        model.addAttribute("contents", mediaService.searchAndFilter(tur, kategori, q));
        model.addAttribute("genres", mediaService.getAllGenres());
        model.addAttribute("seciliTur", tur);
        model.addAttribute("seciliKategori", kategori);
        model.addAttribute("aramaMetni", q);
        model.addAttribute("watchlistIds", getWatchlistIds(session));
        return "catalog";
    }

    @GetMapping("/icerik/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {
        MediaContent item = mediaService.findById(id);
        if (item == null) {
            return "error/404";
        }
        model.addAttribute("item", item);
        model.addAttribute("similarContents", mediaService.findSimilar(item));
        model.addAttribute("inWatchlist", getWatchlistIds(session).contains(id));
        return "detail";
    }

    @GetMapping("/listem")
    public String watchlist(Model model, HttpSession session) {
        Set<Long> ids = getWatchlistIds(session);
        model.addAttribute("watchlistContents", mediaService.findByIds(ids));
        return "watchlist";
    }

    @PostMapping("/listem/toggle/{id}")
    public String toggleWatchlist(@PathVariable Long id,
                                  HttpSession session,
                                  HttpServletRequest request) {
        Set<Long> ids = getWatchlistIds(session);
        if (ids.contains(id)) {
            ids.remove(id);
        } else {
            ids.add(id);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/listem");
    }

    @GetMapping("/oner")
    public String suggestPage(Model model) {
        if (!model.containsAttribute("suggestionForm")) {
            model.addAttribute("suggestionForm", new SuggestionForm());
        }
        model.addAttribute("suggestions", mediaService.getSuggestions());
        return "suggest";
    }

    @PostMapping("/oner")
    public String submitSuggestion(@ModelAttribute SuggestionForm suggestionForm,
                                   RedirectAttributes redirectAttributes) {
        mediaService.addSuggestion(suggestionForm);
        redirectAttributes.addFlashAttribute("successMessage",
                "\"" + suggestionForm.getTitle() + "\" öneriniz başarıyla listeye eklendi!");
        return "redirect:/oner";
    }
}