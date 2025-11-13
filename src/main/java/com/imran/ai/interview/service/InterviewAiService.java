package com.imran.ai.interview.service;

import com.imran.ai.interview.dto.InterviewDtos.AnswerRequest;
import com.imran.ai.interview.dto.InterviewDtos.QuestionRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service that handles all interaction with the LLM through Spring AI.
 * Responsible for building prompts and parsing responses.
 */
@Service
public class InterviewAiService {

    // ChatClient abstracts the underlying provider (Ollama here).
    private final ChatClient chatClient;

    /**
     * Constructor injection of ChatClient.
     *
     * @param chatClient provided by AiConfig
     */
    public InterviewAiService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * Generates a list of interview questions based on job description and resume.
     *
     * @param request DTO containing jobDescription, resumeText, numQuestions
     * @return list of questions as strings
     */
    public List<String> generateQuestions(QuestionRequest request) {
        // Build prompt to instruct the LLM clearly
        String prompt = """
                You are a senior Java/Spring Boot technical interviewer.

                Generate %d high-quality interview questions for this candidate,
                based on the job description and the candidate's resume.

                The questions should be a mix of:
                - Technical (Java, Spring Boot, microservices, Kafka, databases)
                - System design / architecture
                - Behavioral and situational questions

                Job Description:
                %s

                Candidate Resume:
                %s

                Return only the questions as a numbered list like:
                1. ...
                2. ...
                3. ...
                """
                .formatted(
                        Math.max(1, request.getNumQuestions()), // ensure at least 1 question
                        request.getJobDescription(),
                        request.getResumeText()
                );

        // Call the model and get the raw response text
        String result = chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();

        // Simple parsing strategy: split by lines and remove empty ones.
        return Arrays.stream(result.split("\\r?\\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .toList();
    }

    /**
     * Generates a tailored answer for a given interview question.
     *
     * @param request DTO containing jobDescription, resumeText, question
     * @return generated answer text
     */
    public String generateAnswer(AnswerRequest request) {
        // Prompt instructs the model to answer like the candidate
        String prompt = """
                You are helping a candidate prepare for a Java/Spring Boot interview.

                Job Description:
                %s

                Candidate Resume:
                %s

                Interview Question:
                %s

                Write a strong answer in first person ("I"),
                tailored to this candidate's experience and skills.

                Requirements:
                - Keep the answer within 3 to 5 paragraphs.
                - Be specific and concrete, referencing microservices, Kafka,
                  cloud, databases, and leadership where appropriate.
                - Use clear, spoken English suitable for a real interview.
                """
                .formatted(
                        request.getJobDescription(),
                        request.getResumeText(),
                        request.getQuestion()
                );

        // Send the prompt and return the answer text
        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}
