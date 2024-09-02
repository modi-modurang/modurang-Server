package modi.modurang.controller;

import modi.modurang.dto.AnnouncementDto;
import modi.modurang.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/modurang")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping
    public ResponseEntity<List<AnnouncementDto>> getAllAnnouncements() {
        List<AnnouncementDto> announcements = announcementService.getAllAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    @GetMapping("/")
    public ResponseEntity<AnnouncementDto> getAnnouncementById(@PathVariable long id) {
        AnnouncementDto announcement = announcementService.getAnnouncementById(id);
        return ResponseEntity.ok(announcement);
    }

    @PostMapping("/")
    public ResponseEntity<AnnouncementDto> createAnnouncement(@RequestBody AnnouncementDto announcement) {
        AnnouncementDto announcementDto = announcementService.createAnnouncement(announcement);
        return ResponseEntity.ok(announcementDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnouncementDto> updateAnnouncement(@PathVariable Long id, @RequestBody AnnouncementDto announcement) {
        AnnouncementDto announcementDto = announcementService.updateAnnouncement(id, announcement);
        return ResponseEntity.ok(announcementDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AnnouncementDto> deleteAnnouncement(@PathVariable Long id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.noContent().build();
    }
}
