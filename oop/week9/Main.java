package week9;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, Course> courseDb = new HashMap<>();
        courseDb.put("CS101", new Course("CS101", "Java Programming", 1));

        Student s1 = new Student("6601", "Somchai");
        Student s2 = new Student("6602", "Somsri");

        System.out.println("=== 1. ทดสอบการลงทะเบียน ===");
        try {
            Course cs101 = findCourse(courseDb, "CS101");
            
            s1.registerCourse(cs101); // ลงทะเบียนสำเร็จ
            s2.registerCourse(cs101); // วิชาเต็ม -> โยน CourseFullException

        } catch (CourseNotFoundException | DuplicateRegistrationException | CourseFullException e) {
            System.err.println("❌ [Error Log] " + e.getMessage());
        }

        System.out.println("\n=== 2. ทดสอบการถอนวิชา ===");
        try {
            Course cs101 = findCourse(courseDb, "CS101");

            s2.dropCourse(cs101); // ไม่เคยลงไว้ -> โยน CourseNotRegisteredException

        } catch (CourseNotFoundException | CourseNotRegisteredException e) {
            System.err.println("❌ [Error Log] " + e.getMessage());
        }
    }

    public static Course findCourse(Map<String, Course> db, String code) throws CourseNotFoundException {
        Course course = db.get(code);
        if (course == null) {
            throw new CourseNotFoundException("ไม่พบรหัสวิชา " + code + " ในระบบ");
        }
        return course;
    }
}
