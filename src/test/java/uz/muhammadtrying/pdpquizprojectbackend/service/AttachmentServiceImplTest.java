package uz.muhammadtrying.pdpquizprojectbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attachment;
import uz.muhammadtrying.pdpquizprojectbackend.entity.AttachmentContent;
import uz.muhammadtrying.pdpquizprojectbackend.repo.AttachmentContentRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.AttachmentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AttachmentServiceImplTest {

    private AttachmentServiceImpl attachmentService;
    private AttachmentRepository attachmentRepository;
    private AttachmentContentRepository attachmentContentRepository;

    @BeforeEach
    void setUp() {
        this.attachmentRepository = Mockito.mock(AttachmentRepository.class);
        this.attachmentContentRepository = Mockito.mock(AttachmentContentRepository.class);
        this.attachmentService = new AttachmentServiceImpl(attachmentContentRepository, attachmentRepository);
    }

    @Test
    void getPhotoByIdAttachmentNotFoundTest() {
        Integer attachmentId = 1;

        Mockito.when(attachmentRepository.findById(attachmentId)).thenReturn(Optional.empty());

        Optional<AttachmentContent> result = attachmentService.getPhotoById(attachmentId);

        assertTrue(result.isEmpty(), "AttachmentContent should be empty when attachment is not found.");
    }

    @Test
    void getPhotoByIdAttachmentFoundTest() {
        Integer attachmentId = 1;
        Attachment attachment = new Attachment();
        attachment.setId(attachmentId);
        AttachmentContent attachmentContent = new AttachmentContent();

        Mockito.when(attachmentRepository.findById(attachmentId)).thenReturn(Optional.of(attachment));
        Mockito.when(attachmentContentRepository.findByAttachment(attachment)).thenReturn(attachmentContent);

        Optional<AttachmentContent> result = attachmentService.getPhotoById(attachmentId);

        assertTrue(result.isPresent(), "AttachmentContent should be present when attachment is found.");
        assertEquals(attachmentContent, result.get(), "The returned AttachmentContent should match the expected one.");
    }

    @Test
    void getPhotoByIdAttachmentContentNotFoundTest() {
        Integer attachmentId = 1;
        Attachment attachment = new Attachment();
        attachment.setId(attachmentId); // Ensure the attachment has an ID

        Mockito.when(attachmentRepository.findById(attachmentId)).thenReturn(Optional.of(attachment));
        Mockito.when(attachmentContentRepository.findByAttachment(attachment)).thenReturn(null); // Simulate that no content is found

        Optional<AttachmentContent> result = attachmentService.getPhotoById(attachmentId);

        assertTrue(result.isEmpty(), "AttachmentContent should be empty when no content is found for the attachment.");
    }
}