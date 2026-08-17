import java.util.Random;

public class Students {

    private String studentID;
    private String name;
    private String major;

    public static void main(String[] args) {

        Random rand = new Random();
        int number = rand.nextInt(200) + 1;

        Students s = new Students();

        System.out.println(s.getStudentId("68xxx"));
        System.out.println(s.getFullname("Napintor", "Pimpituk"));
        System.out.println("Random Number = " + number);
        System.out.println(s.getGrade(number));
    }

    public String getStudentId(String studentID) {
        return "Student ID : " + studentID;
    }

    public String getFullname(String fname, String lname) {
        return "Full Name : " + fname + " " + lname;
    }

    public String getGrade(int number) {

        if (number % 2 == 0) {
            return getEvenGrade(number);
        } else {
            return getOddGrade(number);
        }
    }

    public String getEvenGrade(int number) {

        double score = number / 2.0;
        String grade;

        if (score > 90) {
            grade = "A";
        } else if (score >= 70) {
            grade = "B";
        } else if (score >= 60) {
            grade = "C";
        } else if (score >= 50) {
            grade = "D";
        } else {
            grade = "F";
        }

        return "Type : Even Number" +
               "\nScore : " + score +
               "\nGrade : " + grade;
    }

    public String getOddGrade(int number) {

        double score = number / 2.0;
        String grade;

        if (score > 80) {
            grade = "A";
        } else if (score >= 70) {
            grade = "B";
        } else if (score >= 60) {
            grade = "C";
        } else if (score >= 50) {
            grade = "D";
        } else {
            grade = "F";
        }

        return "Type : Odd Number" +
               "\nScore : " + score +
               "\nGrade : " + grade;
    }
}