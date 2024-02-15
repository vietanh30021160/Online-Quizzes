package com.swp.online_quizz.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.swp.online_quizz.Entity.Answer;
import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Repository.AnswersRepository;
import com.swp.online_quizz.Repository.QuestionsRepository;
import com.swp.online_quizz.Repository.QuizRepository;

@Service
public class ExcelUploadService {

    QuizRepository quizRepository;
    QuestionsRepository questionsRepository;
    AnswersRepository answersRepository;
    ISubjectService iSubjectService;

    public Quiz createQuizFromExcel(MultipartFile excelFile) throws IOException {

        Workbook workbook = WorkbookFactory.create(excelFile.getInputStream());
        Sheet quizSheet = workbook.getSheetAt(0);

        Quiz quiz = new Quiz();
        String quizNameString = (quizSheet).getRow(0).getCell(0).getStringCellValue();
        int colonIndex = quizNameString.indexOf(":");
        String quizName = "";
        if (colonIndex != -1) {
            quizName = quizNameString.substring(colonIndex + 1).trim();
        }
        quiz.setQuizName(quizName);
        String subjectString = (quizSheet).getRow(1).getCell(0).getStringCellValue();
        int subColonIndex = quizNameString.indexOf(":");
        String subjectName = "";
        if (subColonIndex != -1) {
            subjectName = subjectString.substring(colonIndex + 1).trim();
        }
        Subject subject = iSubjectService.createOrUpdateSubject(subjectName);
        quiz.setSubject(subject);
        Integer timeLimit = Integer.valueOf((int) (quizSheet).getRow(1).getCell(2).getNumericCellValue());
        quiz.setTimeLimit(timeLimit);

        int rowNumber = 6;
        while (rowNumber <= quizSheet.getLastRowNum()) {
            Row currentRow = quizSheet.getRow(rowNumber++);
            Question question = new Question();
            question.setQuestionContent(currentRow.getCell(0).getStringCellValue());
            question.setQuestionType(currentRow.getCell(1).getStringCellValue());
            String input = currentRow.getCell(6).getStringCellValue();
            List<Integer> correctAnswerList = new ArrayList<>();
            String[] parts = input.split(",");
            for (String part : parts) {
                try {
                    int number = Integer.parseInt(part.trim());
                    correctAnswerList.add(number);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            int cellNumber = 2;
            while (cellNumber <= 5) {
                if (currentRow.getCell(cellNumber) != null) {
                    String answerContent = currentRow.getCell(cellNumber).getStringCellValue();
                    Answer answer = new Answer();
                    answer.setAnswerContent(answerContent);
                    answer.setQuestion(question);
                    if (correctAnswerList.contains(cellNumber - 1)) {
                        answer.setIsCorrect(true);
                    } else {
                        answer.setIsCorrect(false);
                    }
                    question.getListAnswer().add(answer);
                }
                cellNumber++;
            }
            quiz.getListQuestions().add(question);
        }
        return quizRepository.save(quiz);
    }
}
