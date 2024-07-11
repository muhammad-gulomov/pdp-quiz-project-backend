package uz.muhammadtrying.pdpquizprojectbackend.service;

import org.springframework.stereotype.Service;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attachment;
import uz.muhammadtrying.pdpquizprojectbackend.entity.AttachmentContent;

import java.util.Optional;

@Service
public interface AttachmentService {
    Optional<AttachmentContent> getPhotoById(Integer attachmentId);
}
