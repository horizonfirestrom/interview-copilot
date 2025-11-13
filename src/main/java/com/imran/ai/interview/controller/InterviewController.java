package com.imran.ai.interview.controller;

import com.imran.ai.interview.dto.InterviewDtos.AnswerRequest;
import com.imran.ai.interview.dto.InterviewDtos.AnswerResponse;
import com.imran.ai.interview.dto.InterviewDtos.QuestionRequest;
import com.imran.ai.interview.dto.InterviewDtos.QuestionResponse;
import com.imran.ai.interview.service.InterviewAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing the Interview Copilot APIs.
 */
@RestController
@RequestMapping("/api/interview")
public class InterviewController {

    // Service that talks to the LLM
    private final InterviewAiService interviewAiService;

    /**
     * Constructor injection of InterviewAiService.
     *
     * @param interviewAiService main service bean
     */
    public InterviewController(InterviewAiService interviewAiService) {
        this.interviewAiService = interviewAiService;
    }

    /**
     * Endpoint to generate interview questions.
     *
     * POST /api/interview/questions
     *
     * Example request body:
     * {
     *   "jobDescription": "JD text here...",
     *   "resumeText": "Resume text here...",
     *   "numQuestions": 7
     * }
     */
    @PostMapping("/questions")
    public ResponseEntity<QuestionResponse> generateQuestions(
            @RequestBody QuestionRequest request) {

        var questions = interviewAiService.generateQuestions(request);
        return ResponseEntity.ok(new QuestionResponse(questions));
    }

    /**
     * Endpoint to generate an answer for a specific question.
     *
     * POST /api/interview/answers
     *
     * Example request body:
     * {
     *   "jobDescription": "JD text here...",
     *   "resumeText": "Resume text here...",
     *   "question": "Can you describe your experience with Kafka?"
     * }
     */
    @PostMapping("/answers")
    public ResponseEntity<AnswerResponse> generateAnswer(
            @RequestBody AnswerRequest request) {

        String answer = interviewAiService.generateAnswer(request);
        return ResponseEntity.ok(new AnswerResponse(answer));
    }
}
