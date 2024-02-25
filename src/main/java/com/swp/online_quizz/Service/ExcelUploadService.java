package com.swp.online_quizz.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.swp.online_quizz.Entity.Answer;
import com.swp.online_quizz.Entity.Question;
import com.swp.online_quizz.Entity.Quiz;
import com.swp.online_quizz.Entity.Subject;
import com.swp.online_quizz.Entity.User;
import com.swp.online_quizz.Repository.AnswersRepository;
import com.swp.online_quizz.Repository.QuestionsRepository;
import com.swp.online_quizz.Repository.QuizRepository;

@Service
public class ExcelUploadService {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private AnswersRepository answersRepository;
    @Autowired
    private ISubjectService iSubjectService;

    public Quiz createQuizFromExcel(MultipartFile excelFile, User teacher) throws IOException {
        Workbook workbook = WorkbookFactory.create(excelFile.getInputStream());
        Sheet quizSheet = workbook.getSheetAt(0);

        Quiz quiz = new Quiz();
        quiz.setTeacher(teacher);
        quiz.setIsCompleted(false);
        // Lấy tên của bài kiểm tra từ file excel
        String quizNameString = quizSheet.getRow(0).getCell(0).getStringCellValue();
        int colonIndex = quizNameString.indexOf(":");
        String quizName = "";
        if (colonIndex != -1) {
            quizName = quizNameString.substring(colonIndex + 1).trim();
        }
        quiz.setQuizName(quizName);

        // Lấy tên môn học từ file excel và tạo môn học nếu không tồn tại
        String subjectString = quizSheet.getRow(1).getCell(0).getStringCellValue();
        int subColonIndex = subjectString.indexOf(":");
        String subjectName = "";
        if (subColonIndex != -1) {
            subjectName = subjectString.substring(subColonIndex + 1).trim();
        }
        Subject subject = iSubjectService.createOrUpdateSubject(subjectName);
        quiz.setSubject(subject);

        // Lấy giới hạn thời gian từ file excel
        String timeLimitString = quizSheet.getRow(1).getCell(2).getStringCellValue(); // Lấy giá trị của ô trong bảng
                                                                                      // tính dưới dạng chuỗi
        int timeLimitColonIndex = timeLimitString.indexOf(":"); // Tìm vị trí của dấu ':' trong chuỗi
        if (timeLimitColonIndex != -1) {
            String numberString = timeLimitString.substring(timeLimitColonIndex + 1).trim();
            try {
                int timeLimit = Integer.parseInt(numberString);
                quiz.setTimeLimit(timeLimit); // Thiết lập giới hạn thời gian cho bài kiểm tra
            } catch (NumberFormatException e) {
                // Xử lý nếu chuỗi không chứa số hợp lệ
            }
        }
        quiz = quizRepository.save(quiz);
        // Xử lý các câu hỏi và câu trả lời
        int rowNumber = 6;
        while (rowNumber <= quizSheet.getLastRowNum()) {
            Row currentRow = quizSheet.getRow(rowNumber++);
            Question question = new Question();
            question.setQuiz(quiz);
            question.setQuestionContent(currentRow.getCell(0).getStringCellValue());
            question.setQuestionType(currentRow.getCell(1).getStringCellValue());
            Cell correctAnswerCell = currentRow.getCell(6);
            List<Integer> correctAnswerList = new ArrayList<>();

            if (correctAnswerCell.getCellType() == CellType.NUMERIC) {
                // Nếu cell chứa một số, chuyển đổi giá trị thành số nguyên và thêm vào danh
                // sách
                int number = (int) correctAnswerCell.getNumericCellValue();
                correctAnswerList.add(number);
            } else if (correctAnswerCell.getCellType() == CellType.STRING) {
                // Nếu cell chứa một chuỗi, kiểm tra nếu chứa dấu phẩy
                String correctAnswerInput = correctAnswerCell.getStringCellValue();
                if (correctAnswerInput.contains(",")) {
                    // Nếu chứa dấu phẩy, chia thành các phần và thêm vào danh sách
                    String[] parts = correctAnswerInput.split(",");
                    for (String part : parts) {
                        try {
                            int number = Integer.parseInt(part.trim());
                            correctAnswerList.add(number);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    // Nếu không chứa dấu phẩy, giá trị là một số duy nhất
                    try {
                        int number = Integer.parseInt(correctAnswerInput.trim());
                        correctAnswerList.add(number);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
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