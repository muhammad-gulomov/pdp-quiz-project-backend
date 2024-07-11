package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attachment;
import uz.muhammadtrying.pdpquizprojectbackend.entity.AttachmentContent;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Integer> {
    AttachmentContent findByAttachment(Attachment attachment);
}