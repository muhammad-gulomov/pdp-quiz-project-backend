package uz.muhammadtrying.pdpquizprojectbackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.mail.SimpleMailMessage;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserCredDTO;
import uz.muhammadtrying.pdpquizprojectbackend.dto.UserDTO;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Attachment;
import uz.muhammadtrying.pdpquizprojectbackend.entity.AttachmentContent;
import uz.muhammadtrying.pdpquizprojectbackend.entity.TempUser;
import uz.muhammadtrying.pdpquizprojectbackend.entity.User;
import uz.muhammadtrying.pdpquizprojectbackend.repo.AttachmentContentRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.AttachmentRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.TempUserRepository;
import uz.muhammadtrying.pdpquizprojectbackend.repo.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserServiceImpl userService;
    private JavaMailSender javaMailSender;
    private UserRepository userRepository;
    private TempUserRepository tempUserRepository;
    private PasswordEncoder passwordEncoder;
    private AttachmentRepository attachmentRepository;
    private AttachmentContentRepository attachmentContentRepository;

    @BeforeEach
    public void setUp() {
        javaMailSender = mock(JavaMailSender.class);
        userRepository = mock(UserRepository.class);
        tempUserRepository = mock(TempUserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        attachmentRepository = mock(AttachmentRepository.class);
        attachmentContentRepository = mock(AttachmentContentRepository.class);

        userService = new UserServiceImpl(javaMailSender, userRepository, tempUserRepository, passwordEncoder, attachmentRepository, attachmentContentRepository);

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn("user@example.com");
    }



    @Test
    void addDataToTempDBTest() {
        UserDTO userDTO = UserDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();
        String code = "1234";

        userService.addDataToTempDB(userDTO, code);

        TempUser expectedTempUser = TempUser.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .code("1234")
                .build();

        assertEquals(userDTO.getEmail(), expectedTempUser.getEmail());
        assertEquals(userDTO.getPassword(), expectedTempUser.getPassword());
        assertEquals(userDTO.getFirstName(), expectedTempUser.getFirstName());
        assertEquals(userDTO.getLastName(), expectedTempUser.getLastName());
    }

    @Test
    void sendEmailTest() {
        String email = "test@example.com";
        String code = "1234";

        userService.sendEmail(email, code);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void codeGeneratorTest() {
        String code = userService.codeGenerator();

        assertNotNull(code);
        assertEquals(4, code.length());
    }



    @Test
    void getDataFromTempDBTest() {
        String email = "john.doe@example.com";
        TempUser tempUser = TempUser.builder()
                .email(email)
                .build();
        Mockito.when(tempUserRepository.findByEmail(email)).thenReturn(tempUser);

        TempUser result = userService.getDataFromTempDB(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void getCurrentUserTest() {
        // Given
        String email = "user@example.com";
        User user = User.builder()
                .email(email)
                .build();
        Mockito.when(userRepository.findByEmail(email)).thenReturn(user);

        User result = userService.getCurrentUser();

        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    void checkIfValidAndSetTest() {
        UserCredDTO userCredDTO = UserCredDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .password("newPassword")
                .photo("base64photo")
                .build();
        User currentUser = User.builder()
                .firstName("OldFirstName")
                .lastName("OldLastName")
                .password("oldPassword")
                .build();

        Attachment attachment = new Attachment();
        Mockito.when(attachmentRepository.save(any(Attachment.class))).thenReturn(attachment);
        Mockito.when(attachmentContentRepository.save(any(AttachmentContent.class))).thenReturn(new AttachmentContent());

        userService.checkIfValidAndSet(userCredDTO, currentUser);

        assertEquals("John", currentUser.getFirstName());
        assertEquals("Doe", currentUser.getLastName());
        assertEquals("newPassword", currentUser.getPassword());
    }

    @Test
    void findAllTest() {
        User user = User.builder().build();
        List<User> users = Collections.singletonList(user);
        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}