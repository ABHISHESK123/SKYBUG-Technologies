import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class QuizQuestion {
    private String question;
    private List<String> options;
    private char correctAnswer;

    public QuizQuestion(String question, List<String> options, char correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }
}

public class Quiz {
    private static Timer timer;
    private static char userAnswer;
    private static int score = 0;
    private static int correctAnswers = 0;
    private static int incorrectAnswers = 0;
    private static List<QuizQuestion> quizQuestions;

    public static void main(String[] args) {
        quizQuestions = new ArrayList<>();

        // Sample questions
        quizQuestions.add(new QuizQuestion("What is the capital of France?",
                List.of("A. Berlin", "B. Madrid", "C. Paris", "D. Rome"), 'C'));

        quizQuestions.add(new QuizQuestion("Which planet is known as the Red Planet?",
                List.of("A. Mars", "B. Venus", "C. Jupiter", "D. Saturn"), 'A'));

        // Display one question at a time and get user answers with a timer
        Scanner scanner = new Scanner(System.in);

        for (QuizQuestion question : quizQuestions) {
            displayQuestion(question);

            // Set up a timer for 10 seconds
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\nTime's up! Moving to the next question.");
                    timer.cancel(); // Stop the timer
                    synchronized (Quiz.class) {
                        Quiz.class.notify(); // Notify the main thread to continue
                    }
                }
            }, 10000);

            // Get user answer within the given time
            getUserAnswer(scanner, question);

            // Cancel the timer if the user answers before the time is up
            timer.cancel();

            if (userAnswer == question.getCorrectAnswer()) {
                System.out.println("Correct!\n");
                score++;
                correctAnswers++;
            } else {
                System.out.println("Incorrect. The correct answer is " + question.getCorrectAnswer() + ".\n");
                incorrectAnswers++;
            }
        }

        displayResult();
    }

    private static void displayQuestion(QuizQuestion question) {
        System.out.println(question.getQuestion());
        for (String option : question.getOptions()) {
            System.out.println(option);
        }
        System.out.print("Your answer: ");
    }

    private static void getUserAnswer(Scanner scanner, QuizQuestion question) {
        synchronized (Quiz.class) {
            try {
                userAnswer = scanner.next().toUpperCase().charAt(0);
                Quiz.class.wait(); // Wait for the timer expiration
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("You selected: " + userAnswer);
    }

    private static void displayResult() {
        System.out.println("\n--- Quiz Result ---");
        System.out.println("Final Score: " + score + "/" + quizQuestions.size());
        System.out.println("Correct Answers: " + correctAnswers);
        System.out.println("Incorrect Answers: " + incorrectAnswers);
    }
}