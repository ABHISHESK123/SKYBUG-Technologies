import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class QuizSubject {
    private String subjectName;
    private int marks;  // Marks obtained in the subject

    public QuizSubject(String subjectName, int marks) {
        this.subjectName = subjectName;
        this.marks = marks;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getMarks() {
        return marks;
    }
}

public class STUDENT_GRADE_CALCULATOR {
    private static Timer timer;
    private static int totalMarks = 0;
    private static int totalSubjects = 0;

    public static void main(String[] args) {
        List<QuizSubject> quizSubjects = new ArrayList<>();

        // Sample subjects
        quizSubjects.add(new QuizSubject("Mathematics", 0));
        quizSubjects.add(new QuizSubject("Science", 0));
        quizSubjects.add(new QuizSubject("History", 0));
        quizSubjects.add(new QuizSubject("English", 0));  // New subject
        quizSubjects.add(new QuizSubject("Computer Science", 0));  // New subject

        // Display one subject at a time and get user marks with a timer
        Scanner scanner = new Scanner(System.in);

        for (QuizSubject subject : quizSubjects) {
            displaySubject(subject);

            // Set up a timer for 10 seconds
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\nTime's up! Moving to the next subject.");
                    timer.cancel(); // Stop the timer
                    synchronized (STUDENT_GRADE_CALCULATOR.class) {
                        STUDENT_GRADE_CALCULATOR.class.notify(); // Notify the main thread to continue
                    }
                }
            }, 10000);

            // Get user marks within the given time
            getUserMarks(scanner, subject);

            // Cancel the timer if the user marks before the time is up
            timer.cancel();
        }

        displayResult(quizSubjects);
    }

    private static void displaySubject(QuizSubject subject) {
        System.out.println(subject.getSubjectName());
        System.out.print("Marks obtained (out of 100): ");
    }

    private static void getUserMarks(Scanner scanner, QuizSubject subject) {
        synchronized (STUDENT_GRADE_CALCULATOR.class) {
            try {
                int marks = scanner.nextInt();
                subject = new QuizSubject(subject.getSubjectName(), marks);
                totalMarks += marks; // Increment totalMarks for each subject
                totalSubjects++;     // Increment totalSubjects
                STUDENT_GRADE_CALCULATOR.class.wait();    // Wait for the timer expiration
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Marks obtained: " + subject.getMarks() + "/100");
    }

    private static void displayResult(List<QuizSubject> quizSubjects) {
        if (totalSubjects == 0) {
            System.out.println("No subjects entered. Exiting.");
            return;
        }

        double averagePercentage = (double) totalMarks / totalSubjects;
        char grade = calculateGrade(averagePercentage);

        System.out.println("\n--- Quiz Result ---");
        System.out.println("Total Marks Obtained: " + totalMarks + "/" + totalSubjects * 100);
        System.out.printf("Average Percentage: %.2f%%\n", averagePercentage);
        System.out.println("Grade: " + grade);
    }

    private static char calculateGrade(double averagePercentage) {
        if (averagePercentage >= 90) {
            return 'A';
        } else if (averagePercentage >= 80) {
            return 'B';
        } else if (averagePercentage >= 70) {
            return 'C';
        } else if (averagePercentage >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }
}
