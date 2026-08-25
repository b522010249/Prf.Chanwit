package week9;
import java.util.*;

// 1. นิยาม Custom Exceptions สำหรับเงื่อนไขต่างๆ
class CourseNotFoundException extends Exception {
    public CourseNotFoundException(String message) { super(message); }
}

class CourseFullException extends Exception {
    public CourseFullException(String message) { super(message); }
}

class DuplicateRegistrationException extends Exception {
    public DuplicateRegistrationException(String message) { super(message); }
}

class CourseNotRegisteredException extends Exception {
    public CourseNotRegisteredException(String message) { super(message); }
}

// 2. คลาสวิชา (Course)
class Course {
    private String code;
    private String name;
    private int capacity;
    private int enrolled;

    public Course(String code, String name, int capacity) {
        this.code = code;
        this.name = name;
        this.capacity = capacity;
        this.enrolled = 0;
    }

    public String getCode() { return code; }
    public boolean isFull() { return enrolled >= capacity; }
    public void enroll() { enrolled++; }
    public void drop() { enrolled--; }
    public int getEnrolled() { return enrolled; }
    public int getCapacity() { return capacity; }
}

// 3. คลาสนักศึกษา (Student)
class Student {
    private String id;
    private String name;
    private List<Course> registeredCourses = new ArrayList<>();

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // เพิ่มวิชาเรียนพร้อมตรวจสอบเงื่อนไข
    public void registerCourse(Course course) throws DuplicateRegistrationException, CourseFullException {
        if (registeredCourses.contains(course)) {
            throw new DuplicateRegistrationException("ลงทะเบียนไม่สำเร็จ: นักศึกษาลงวิชา " + course.getCode() + " ไปแล้ว");
        }
        if (course.isFull()) {
            throw new CourseFullException("ลงทะเบียนไม่สำเร็จ: วิชา " + course.getCode() + " ที่นั่งเต็ม (" + course.getEnrolled() + "/" + course.getCapacity() + ")");
        }
        
        registeredCourses.add(course);
        course.enroll();
        System.out.println(name + " ลงทะเบียนวิชา " + course.getCode() + " สำเร็จ");
    }

    // ถอนวิชาเรียนพร้อมตรวจสอบเงื่อนไข
    public void dropCourse(Course course) throws CourseNotRegisteredException {
        if (!registeredCourses.contains(course)) {
            throw new CourseNotRegisteredException("ถอนวิชาไม่สำเร็จ: ไม่พบวิชา " + course.getCode() + " ในรายการที่ลงทะเบียนไว้");
        }
        
        registeredCourses.remove(course);
        course.drop();
        System.out.println(name + " ถอนวิชา " + course.getCode() + " สำเร็จ");
    }
}

// 4. คลาสหลักทดสอบการทำงาน
public class Main {
    public static void main(String[] args) {
        // จำลองฐานข้อมูลรายวิชา
        Map<String, Course> courseDb = new HashMap<>();
        courseDb.put("CS101", new Course("CS101", "Java Programming", 1)); // รับได้แค่ 1 คน

        Student s1 = new Student("6601", "Somchai");
        Student s2 = new Student("6602", "Somsri");

        // --- ทดสอบการลงทะเบียนและการจับ Exception ---
        try {
            Course cs101 = findCourse(courseDb, "CS101");
            
            // กรณีที่ 1: ลงทะเบียนปกติ (สำเร็จ)
            s1.registerCourse(cs101);

            // กรณีที่ 2: ลองลงทะเบียนซ้ำ (โยน DuplicateRegistrationException)
            // s1.registerCourse(cs101);

            // กรณีที่ 3: วิชาเต็ม (โยน CourseFullException เพราะรับได้ 1 คน)
            s2.registerCourse(cs101);

        } catch (CourseNotFoundException | DuplicateRegistrationException | CourseFullException e) {
            System.err.println("[Error Log] " + e.getMessage());
        }

        // --- ทดสอบการถอนวิชา ---
        try {
            Course cs101 = findCourse(courseDb, "CS101");

            // กรณีที่ 4: ถอนวิชาที่ตัวเองไม่ได้ลงไว้ (โยน CourseNotRegisteredException)
            s2.dropCourse(cs101);

        } catch (CourseNotFoundException | CourseNotRegisteredException e) {
            System.err.println("[Error Log] " + e.getMessage());
        }
    }

    // เมธอดค้นหาวิชา
    public static Course findCourse(Map<String, Course> db, String code) throws CourseNotFoundException {
        Course course = db.get(code);
        if (course == null) {
            throw new CourseNotFoundException("ไม่พบรหัสวิชา " + code + " ในระบบ");
        }
        return course;
    }
}