package com.swp.online_quizz.Controller;

import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Service.ExcelUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    @Autowired
    private ExcelUploadService excelUploadService;

    @GetMapping("/heloo")
    public String testFilter() {
        return "hello";
    }

    @PostMapping("/upload")
    public ResponseEntity<Quiz> uploadExcel(@RequestParam("file") MultipartFile excelFile) throws IOException {
        return new ResponseEntity<>(excelUploadService.createQuizFromExcel(excelFile), HttpStatus.CREATED);
    }
}
