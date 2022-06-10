package com.cybersoft.asimovapi.announcements.api;

import com.cybersoft.asimovapi.announcements.domain.service.AnnouncementService;
import com.cybersoft.asimovapi.announcements.mapping.AnnouncementMapper;
import com.cybersoft.asimovapi.announcements.resource.AnnouncementResource;
import com.cybersoft.asimovapi.announcements.resource.CreateAnnouncementResource;
import com.cybersoft.asimovapi.announcements.resource.UpdateAnnouncementResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/announcements")
public class AnnouncementsController {

    private final AnnouncementService announcementService;

    private final AnnouncementMapper mapper;

    public AnnouncementsController(AnnouncementService announcementService, AnnouncementMapper mapper) {
        this.announcementService = announcementService;
        this.mapper = mapper;
    }

    @GetMapping("api/v1/directors/{directorId}/announcements")
    public List<AnnouncementResource> getAllAnnouncementsByDirectorId(@PathVariable Long directorId) {


        return mapper.modelListToResource(announcementService.getAllByDirectorId(directorId));
    }

    @PostMapping("api/v1/directors/{directorId}/announcements")
    public AnnouncementResource createAnnouncement(@PathVariable Long directorId, @RequestBody CreateAnnouncementResource request) {
        return mapper.toResource(announcementService.create(directorId, mapper.toModel(request)));
    }

    @PutMapping("{announcementId}")
    public AnnouncementResource updateAnnouncement(@PathVariable Long announcementId, @RequestBody UpdateAnnouncementResource request) {
        return mapper.toResource(announcementService.update(announcementId, mapper.toModel(request)));
    }

    @DeleteMapping("{announcementId}")
    public ResponseEntity<?> deleteAnnouncement(@PathVariable Long announcementId) {
        return announcementService.delete(announcementId);
    }
}
