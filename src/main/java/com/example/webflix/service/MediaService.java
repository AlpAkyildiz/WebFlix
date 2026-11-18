package com.example.webflix.service;

import com.example.webflix.model.MediaContent;
import com.example.webflix.model.SuggestionForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class MediaService {

    private final List<MediaContent> contents = new ArrayList<>();
    private final List<SuggestionForm> suggestions = new ArrayList<>();
    private long nextId = 9L;

    public MediaService() {
        contents.add(new MediaContent(
                1L, "Inception", "FILM", "Bilim Kurgu", 2010, 8.8, "2s 28dk",
                "Christopher Nolan", "/images/inception.jpg",
                "https://www.youtube.com/embed/YoHD9XEInc0",
                "Rüyaların derinliklerine sızarak bilinçaltından sır çalan usta bir hırsız, bu kez bir fikri zihne yerleştirmek gibi imkansız bir görev üstlenir.",
                List.of("Leonardo DiCaprio", "Joseph Gordon-Levitt", "Elliot Page", "Tom Hardy")
        ));

        contents.add(new MediaContent(
                2L, "Severance", "DIZI", "Gerilim", 2022, 8.7, "2 Sezon",
                "Ben Stiller", "/images/severance.jpg",
                "https://www.youtube.com/embed/xEQP4VVuyrY",
                "Lumon Industries çalışanlarının iş ve özel hayat anıları cerrahi bir operasyonla birbirinden tamamen ayrılmıştır. Ancak ofis duvarlarının ardında karanlık bir gizem yatmaktadır.",
                List.of("Adam Scott", "Britt Lower", "Zach Cherry", "John Turturro")
        ));

        contents.add(new MediaContent(
                3L, "Interstellar", "FILM", "Bilim Kurgu", 2014, 8.7, "2s 49dk",
                "Christopher Nolan", "/images/interstellar.jpg",
                "https://www.youtube.com/embed/zSWdZVtXT7E",
                "Yaşanmaz hale gelen Dünya'dan insanlığı kurtarmak için Satürn yakınlarındaki bir solucan deliğinden geçerek yeni gezegenler arayan bir ekibin destansı yolculuğu.",
                List.of("Matthew McConaughey", "Anne Hathaway", "Jessica Chastain", "Michael Caine")
        ));

        contents.add(new MediaContent(
                4L, "Dark", "DIZI", "Bilim Kurgu", 2017, 8.7, "3 Sezon",
                "Baran bo Odar", "/images/dark.jpg",
                "https://www.youtube.com/embed/rrwycJ08PSA",
                "Almanya'nın küçük Winden kasabasında iki çocuğun kaybolması, dört aile arasındaki kırık ilişkileri ve üç nesle yayılan zaman yolculuğu döngüsünü açığa çıkarır.",
                List.of("Louis Hofmann", "Lisa Vicari", "Andreas Pietschmann", "Maja Schöne")
        ));

        contents.add(new MediaContent(
                5L, "The Dark Knight", "FILM", "Aksiyon", 2008, 9.0, "2s 32dk",
                "Christopher Nolan", "/images/dark-knight.jpg",
                "https://www.youtube.com/embed/EXeTwQWrcwY",
                "Batman, Teğmen Jim Gordon ve Savcı Harvey Dent, Gotham sokaklarını kaosa sürükleyen Joker adlı suç dehasına karşı ahlaki sınırlarını zorlayan bir mücadeleye girer.",
                List.of("Christian Bale", "Heath Ledger", "Aaron Eckhart", "Gary Oldman")
        ));

        contents.add(new MediaContent(
                6L, "Breaking Bad", "DIZI", "Suç", 2008, 9.5, "5 Sezon",
                "Vince Gilligan", "/images/breaking-bad.jpg",
                "https://www.youtube.com/embed/HhesaQXLuRY",
                "İleri evre akciğer kanseri teşhisi konulan lise kimya öğretmeni Walter White, ailesinin geleceğini güvence altına almak için eski öğrencisiyle tehlikeli bir suç dünyasına adım atar.",
                List.of("Bryan Cranston", "Aaron Paul", "Anna Gunn", "Bob Odenkirk")
        ));

        contents.add(new MediaContent(
                7L, "Dune: Part Two", "FILM", "Bilim Kurgu", 2024, 8.5, "2s 46dk",
                "Denis Villeneuve", "/images/dune2.jpg",
                "https://www.youtube.com/embed/Way9Dexny3w",
                "Paul Atreides, ailesini yok eden komploculardan intikam almak için Chani ve Fremenlerle birleşirken sevdiği kadın ile evrenin kaderi arasında seçim yapmak zorundadır.",
                List.of("Timothée Chalamet", "Zendaya", "Rebecca Ferguson", "Javier Bardem")
        ));

        contents.add(new MediaContent(
                8L, "True Detective", "DIZI", "Dram", 2014, 8.9, "4 Sezon",
                "Nic Pizzolatto", "/images/true-detective.jpg",
                "https://www.youtube.com/embed/fVQUcaO4AvE",
                "Louisiana'da işlenen ritüelistik bir cinayeti 17 yıl boyunca araştıran iki dedektifin saplantıları, karanlık sırları ve zamanla değişen hayatları.",
                List.of("Matthew McConaughey", "Woody Harrelson", "Michelle Monaghan")
        ));
    }

    public List<MediaContent> findAll() {
        return contents;
    }

    public List<MediaContent> findByType(String type) {
        return contents.stream()
                .filter(c -> c.getType().equalsIgnoreCase(type))
                .toList();
    }

    public List<MediaContent> searchAndFilter(String type, String genre, String query) {
        return contents.stream()
                .filter(c -> type == null || type.isBlank() || c.getType().equalsIgnoreCase(type))
                .filter(c -> genre == null || genre.isBlank() || c.getGenre().equalsIgnoreCase(genre))
                .filter(c -> query == null || query.isBlank()
                        || c.getTitle().toLowerCase().contains(query.toLowerCase())
                        || c.getDirector().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    public List<String> getAllGenres() {
        return contents.stream()
                .map(MediaContent::getGenre)
                .distinct()
                .sorted()
                .toList();
    }

    public MediaContent findById(Long id) {
        return contents.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<MediaContent> findSimilar(MediaContent current) {
        return contents.stream()
                .filter(c -> !c.getId().equals(current.getId()))
                .filter(c -> c.getGenre().equalsIgnoreCase(current.getGenre()) || c.getType().equalsIgnoreCase(current.getType()))
                .limit(3)
                .toList();
    }

    public List<MediaContent> findByIds(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }
        return contents.stream()
                .filter(c -> ids.contains(c.getId()))
                .toList();
    }

    // --- ADMIN CRUD METOTLARI ---

    public void saveOrUpdate(MediaContent formContent, String castCsv) {
        List<String> parsedCast = (castCsv == null || castCsv.isBlank())
                ? List.of("Belirtilmedi")
                : Arrays.stream(castCsv.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .toList();
        formContent.setCast(parsedCast);

        if (formContent.getPosterUrl() == null || formContent.getPosterUrl().isBlank()) {
            formContent.setPosterUrl("/images/placeholder.jpg");
        }
        if (formContent.getTrailerUrl() == null || formContent.getTrailerUrl().isBlank()) {
            formContent.setTrailerUrl("https://www.youtube.com/embed/YoHD9XEInc0");
        }

        if (formContent.getId() == null) {
            // CREATE (Yeni Ekle)
            formContent.setId(nextId++);
            contents.add(0, formContent);
        } else {
            // UPDATE (Mevcut Kaydı Güncelle)
            MediaContent existing = findById(formContent.getId());
            if (existing != null) {
                existing.setTitle(formContent.getTitle());
                existing.setType(formContent.getType());
                existing.setGenre(formContent.getGenre());
                existing.setReleaseYear(formContent.getReleaseYear());
                existing.setRating(formContent.getRating());
                existing.setDuration(formContent.getDuration());
                existing.setDirector(formContent.getDirector());
                existing.setPosterUrl(formContent.getPosterUrl());
                existing.setTrailerUrl(formContent.getTrailerUrl());
                existing.setSummary(formContent.getSummary());
                existing.setCast(parsedCast);
            }
        }
    }

    public void deleteById(Long id) {
        contents.removeIf(c -> c.getId().equals(id));
    }

    public void addSuggestion(SuggestionForm form) {
        suggestions.add(0, form);
    }

    public List<SuggestionForm> getSuggestions() {
        return suggestions;
    }

    public void deleteSuggestion(int index) {
        if (index >= 0 && index < suggestions.size()) {
            suggestions.remove(index);
        }
    }
}