package com.swp.online_quizz.Controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.InfoPropertiesInfoContributor.Mode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.UsersRepository;
import com.swp.online_quizz.Service.ExcelUploadService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/test")
public class TestController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ExcelUploadService excelUploadService;

    @GetMapping("/heloo")
    public String testFilter() {
        return "hello";
    }

    @PostMapping("/upload")
    public ResponseEntity<Quiz> uploadExcel(@RequestParam("file") MultipartFile excelFile, HttpSession session,
            Mode model, Authentication auth,
            HttpServletRequest request) throws IOException {
        String username = "";
        if (request.getSession().getAttribute("authentication") != null) {
            Authentication authentication = (Authentication) request.getSession().getAttribute("authentication");
            username = authentication.getName();
        }
        Optional<User> userOptional = usersRepository.findByUsername(username);
        // nếu có thì lấy ra user
        User user1 = userOptional.get();
        return new ResponseEntity<>(excelUploadService.createQuizFromExcel(excelFile, user1), HttpStatus.CREATED);
    }
}
