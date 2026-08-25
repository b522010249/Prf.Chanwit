package week9;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String id;
    private String name;
    private List<Course> registeredCourses = new ArrayList<>();

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void registerCourse(Course course) throws DuplicateRegistrationException, CourseFullException {
        if (registeredCourses.contains(course)) {
            throw new DuplicateRegistrationException("ลงทะเบียนไม่สำเร็จ: นักศึกษา " + name + " ลงวิชา " + course.getCode() + " ไปแล้ว");
        }
        if (course.isFull()) {
            throw new CourseFullException("ลงทะเบียนไม่สำเร็จ: วิชา " + course.getCode() + " ที่นั่งเต็ม (" + course.getEnrolled() + "/" + course.getCapacity() + ")");
        }
        
        registeredCourses.add(course);
        course.enroll();
        System.out.println("✅ " + name + " ลงทะเบียนวิชา " + course.getCode() + " สำเร็จ");
    }

    public void dropCourse(Course course) throws CourseNotRegisteredException {
        if (!registeredCourses.contains(course)) {
            throw new CourseNotRegisteredException("ถอนวิชาไม่สำเร็จ: ไม่พบวิชา " + course.getCode() + " ในรายการของ " + name);
        }
        
        registeredCourses.remove(course);
        course.drop();
        System.out.println("✅ " + name + " ถอนวิชา " + course.getCode() + " สำเร็จ");
    }
}
