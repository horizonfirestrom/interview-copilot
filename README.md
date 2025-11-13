🚀 Interview Copilot – AI-Powered Interview Preparation Backend

Interview Copilot is a Spring Boot + Spring AI + Ollama powered backend application that helps generate custom interview questions and tailored answers based on a job description and a candidate’s resume.
This project runs fully locally using Ollama models — no OpenAI API key required.

⭐ Features

🔍 Generate role-specific interview questions using LLMs

🧠 Generate tailored answers based on resume + job description

⚡ Fully local inference using Ollama (supports llama3, phi3, mistral, qwen, etc.)

🏗️ REST APIs built with Spring Boot 3

🤖 AI integration using Spring AI’s ChatClient

📦 Clean architecture: Controller → Service → LLM Layer

📚 Easily pluggable into React, Angular, or mobile frontend apps

🧩 Ready for future extensions: mock chat interviewer, scoring engine, resume analyzer

🛠️ Tech Stack

Java 17+

Spring Boot 3.3.x

Spring AI 1.1.x

Ollama (local LLM runtime)

Maven

REST APIs

📦 Project Structure
interview-copilot/
│
├── src/main/java/com/imran/ai/interview/
│   ├── InterviewCopilotApplication.java
│   ├── config/
│   │   └── AiConfig.java
│   ├── controller/
│   │   └── InterviewController.java
│   ├── dto/
│   │   └── InterviewDtos.java
│   └── service/
│       └── InterviewAiService.java
│
└── src/main/resources/
    └── application.yml

🔧 Installation & Setup
1. Install Ollama

Download from:
➡️ https://ollama.com/download

Then pull your preferred model:

ollama pull llama3


Verify installation:

ollama list


You should see llama3 or any model you pulled.

Start the Ollama service (if not automatic):

ollama serve

2. Clone the repository
git clone https://github.com/<your-username>/interview-copilot.git
cd interview-copilot

3. Build and run the backend

Using Maven:

mvn clean install
mvn spring-boot:run


The server will start at:

http://localhost:8080

⚙️ Configuration (application.yml)
spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model: llama3
          temperature: 0.7
server:
  port: 8080

📡 API Endpoints
📌 1. Generate Interview Questions

POST /api/interview/questions

Request Body:
{
  "jobDescription": "Senior Java Developer with Spring Boot, Microservices, Kafka...",
  "resumeText": "6+ years of experience in Java, Spring Boot, Kafka, AWS, Angular...",
  "numQuestions": 10
}

Response:
{
  "questions": [
    "1. Can you walk me through your experience with microservices?",
    "2. How have you used Kafka in real-time systems?",
    ...
  ]
}

📌 2. Generate Answer for a Question

POST /api/interview/answers

Request Body:
{
  "jobDescription": "Java developer role...",
  "resumeText": "Spring Boot, Microservices, AWS...",
  "question": "Explain your experience with Kafka?"
}

Response:
{
  "answer": "In my previous project at Truist, I used Kafka for building event-driven pipelines..."
}

🧪 Testing the API in Postman

Set:

Body → raw

Format → JSON

Paste JSON and send the request.



🚀 Future Enhancements

🗣️ Mock interviewer chat mode

📝 Resume analyzer + score engine

🧮 Interview difficulty levels

🌐 Frontend UI (React/Angular/Flutter/iOS)

🤝 Multi-user interview sessions

🔒 JWT authentication for secure access

🙋‍♂️ Author

MD Imran
Java Full Stack Developer
Email: codiimran@gmail.com
