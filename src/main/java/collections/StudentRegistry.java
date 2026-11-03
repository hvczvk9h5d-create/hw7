package collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

public class StudentRegistry {

    private StudentRegistry() {
    }

    public static List<Student> findStudentsByGradeRange(
            Map<Integer, Student> students,
            double minGrade,
            double maxGrade) {
        if (minGrade > maxGrade || minGrade < 0.0 || maxGrade > 10.0) {
            throw new IllegalArgumentException("wrong grade range");
        }
        List<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.grade() >= minGrade && student.grade() <= maxGrade) {
                result.add(student);
            }
        }
        return result;
    }

    public static List<Student> getTopNStudentsById(
            NavigableMap<Integer, Student> students,
            int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must not be negative");
        }
        List<Student> result = new ArrayList<>();
        for (Student student : students.values()) {
            if (result.size() == n) {
                break;
            }
            result.add(student);
        }
        return result;
    }
}
