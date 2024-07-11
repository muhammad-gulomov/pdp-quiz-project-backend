package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}