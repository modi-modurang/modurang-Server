package modi.modurang.service;

import jakarta.transaction.Transactional;
import modi.modurang.entity.Announcement;
import modi.modurang.dto.AnnouncementDto;
import modi.modurang.repository.AnnouncementRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<AnnouncementDto> getAllAnnouncements() {
        return announcementRepository.findAll().stream()
                .map(a -> new AnnouncementDto(a.getId(), a.getTitle(), a.getWriter(), a.getContent()))
                .collect(Collectors.toList());
    }

    public AnnouncementDto getAnnouncementById(Long id) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        return announcement.map(a -> new AnnouncementDto(a.getId(), a.getTitle(), a.getWriter(), a.getContent()))
                .orElseThrow(() -> new RuntimeException("확인하려는 공지는 존재하지 않습니다."));
    }

    public AnnouncementDto createAnnouncement(AnnouncementDto announcementDto) {
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementDto.getTitle());
        announcement.setWriter(announcementDto.getAuthor());
        announcement.setContent(announcementDto.getContent());
        announcementRepository.save(announcement);
        return new AnnouncementDto(announcementDto.getId(), announcement.getTitle(), announcement.getWriter(), announcement.getContent());
    }

    @Transactional
    public AnnouncementDto updateAnnouncement(Long id, AnnouncementDto announcementDto) {
        Announcement announcement = announcementRepository.findById(id).
                orElseThrow(() -> new RuntimeException("수정하시려는 소설이 존재하지 않습니다."));
        announcement.setTitle(announcementDto.getTitle());
        announcement.setWriter(announcementDto.getAuthor());
        announcement.setContent(announcementDto.getContent());
        announcementRepository.save(announcement);
        return new AnnouncementDto(announcementDto.getId(), announcement.getTitle(), announcement.getWriter(), announcement.getContent());
    }

    @Transactional
    public void deleteAnnouncement(Long id) {
        announcementRepository.findById(id).
                orElseThrow(() -> new RuntimeException("수정하시려는 소설이 존재하지 않습니다."));
        announcementRepository.deleteById(id);
    }
}
