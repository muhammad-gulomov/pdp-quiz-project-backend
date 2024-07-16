package uz.muhammadtrying.pdpquizprojectbackend.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attachment;
import uz.muhammadtrying.pdpquizprojectbackend.entity.AttachmentContent;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AttachmentContentRepositoryTest {

    @Autowired
    private AttachmentContentRepository attachmentContentRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Test
    void save() {
        Attachment attachment = Attachment.builder().build();
        attachmentRepository.save(attachment);

        AttachmentContent content = AttachmentContent.builder().attachment(attachment).content(new byte[]{1, 2, 3}).build();
        AttachmentContent savedContent = attachmentContentRepository.save(content);

        assertNotNull(savedContent.getId());
    }

    @Test
    void findByAttachment() {
        Attachment attachment = Attachment.builder().build();
        attachmentRepository.save(attachment);

        AttachmentContent content = AttachmentContent.builder().attachment(attachment).content(new byte[]{1, 2, 3}).build();
        attachmentContentRepository.save(content);

        AttachmentContent foundContent = attachmentContentRepository.findByAttachment(attachment);
        assertNotNull(foundContent);
    }
}