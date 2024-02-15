package com.swp.online_quizz.Service;

import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import com.swp.online_quizz.Entity.Answer;
import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;

public class ExcelUploadService {

    public Quiz createQuizFromExcel(MultipartFile excelFile) throws IOException {

        Workbook workbook = WorkbookFactory.create(excelFile.getInputStream());
        Sheet quizSheet = workbook.getSheetAt(0);

        Quiz quiz = new Quiz();
        String quizNameString = (quizSheet).getRow(0).getCell(0).getStringCellValue();
        int colonIndex = quizNameString.indexOf(":");
        String quizName;
        if (colonIndex != -1) {
            quizName = quizNameString.substring(colonIndex + 1).trim();
        }
        quiz.setQuizName(quizName);
        String subjectString = (quizSheet).getRow(0).getCell(0).getStringCellValue();
        int subColonIndex = quizNameString.indexOf(":");
        String subjectName;
        if (subColonIndex != -1) {
            subjectName = subjectString.substring(colonIndex + 1).trim();
        }
        Subject subject = new Subject();
        subject.setSubjectName(subjectName);
        quiz.setSubject(subject);
        Integer timeLimit = Integer.valueOf((int) (quizSheet).getRow(1).getCell(2).getNumericCellValue());
        quiz.setTimeLimit(timeLimit);

        int rowNumber = 6;

        while (rowNumber <= quizSheet.getLastRowNum()) {

            Row currentRow = quizSheet.getRow(rowNumber++);

            Question question = new Question();
            question.setQuestionContent(currentRow.getCell(0).getStringCellValue());
            question.setQuestionType(currentRow.getCell(1).getStringCellValue());

            int cellNumber = 2;
            while (cellNumber <= 6) {
                if (currentRow.getCell(cellNumber) != null) {

                    Answer answer = new Answer();
                    answer.setAnswerContent(currentRow.getCell(cellNumber).getStringCellValue());
                    answer.setCorrect(currentRow.getCell(6).getStringCellValue().equals(String.valueOf(cellNumber)));
                    answer.setQuestion(question);
                    answerRepo.save(answer);
                }
                cellNumber++;
            }

            questionRepo.save(question);
            quiz.addQuestion(question);
        }

        return quizRepo.save(quiz);
    }

}
