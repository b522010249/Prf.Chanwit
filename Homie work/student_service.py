# student_service.py
import re
from models import Student
from student_repository import StudentRepository
import csv

class StudentService:
    def __init__(self, repo: StudentRepository):
        self.repo = repo

    def validate(self, student: Student) -> None:
        if not student.student_id.strip():
            raise ValueError("กรุณากรอกรหัสนักศึกษา")
        if not student.first_name.strip():
            raise ValueError("กรุณากรอกชื่อ")
        if not student.last_name.strip():
            raise ValueError("กรุณากรอกนามสกุล")
        if not student.major.strip():
            raise ValueError("กรุณากรอกสาขาวิชา")
        if not student.faculty.strip():
            raise ValueError("กรุณากรอกคณะ")
        if not student.nick_name.strip():
            raise ValueError("กรุณากรอกชื่อเล่น")
        if not student.phone_number.strip():
            raise ValueError("กรุณากรอกหมายเลขโทรศัพท์")
        if not student.email.strip():
            raise ValueError("กรุณากรอกอีเมล")
        if not re.match(r"^\d+$", student.student_id.strip()):
            raise ValueError("รหัสนักศึกษาต้องเป็นตัวเลขเท่านั้น")
        
        if not re.match(r"^\d{9,10}$", student.phone_number.strip()):
            raise ValueError("หมายเลขโทรศัพท์ต้องเป็นตัวเลข 9-10 หลัก")
            
        email_regex = r"^[\w\.-]+@[\w\.-]+\.\w+$"
        if not re.match(email_regex, student.email.strip()):
            raise ValueError("รูปแบบอีเมลไม่ถูกต้อง (เช่น example@mail.com)")

    def create_student(self, student: Student) -> None:
        self.validate(student)
        if self.repo.get_by_id(student.student_id):
            raise ValueError("รหัสนักศึกษานี้มีอยู่แล้ว")
        self.repo.create(student)

    def list_students(self):
        return self.repo.get_all()

    def update_student(self, student: Student) -> None:
        self.validate(student)
        affected = self.repo.update(student)
        if affected == 0:
            raise ValueError("ไม่พบรหัสนักศึกษาที่ต้องการแก้ไข")

    def delete_student(self, student_id: str) -> None:
        if not student_id.strip():
            raise ValueError("กรุณากรอกรหัสนักศึกษาที่ต้องการลบ")
        affected = self.repo.delete(student_id)
        if affected == 0:
            raise ValueError("ไม่พบรหัสนักศึกษาที่ต้องการลบ")
    def get_report(self):
        return self.repo.get_summary_report()
    def import_csv_data(self, filepath: str) -> tuple[int, int]:
        inserted_count = 0
        updated_count = 0
        
        with open(filepath, mode='r', encoding='utf-8-sig') as file:
            reader = csv.DictReader(file)
            for row in reader:
                student = Student(
                    student_id=row.get('student_id', '').strip(),
                    first_name=row.get('first_name', '').strip(),
                    last_name=row.get('last_name', '').strip(),
                    major=row.get('major', '').strip(),
                    faculty=row.get('faculty', '').strip(),
                    nick_name=row.get('nick_name', '').strip(),
                    phone_number=row.get('phone_number', '').strip(),
                    email=row.get('email', '').strip()
                )
                
                try:
                    self.validate(student)
                    if self.repo.get_by_id(student.student_id):
                        self.repo.update(student)
                        updated_count += 1
                    else:
                        self.repo.create(student)
                        inserted_count += 1
                except ValueError:
                    # ข้ามแถวที่ข้อมูลไม่ถูกต้อง หรืออาจจะเก็บ log ไว้
                    continue
                    
        return inserted_count, updated_count