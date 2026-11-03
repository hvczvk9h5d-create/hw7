package collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentRegistryTest {

    private Map<Integer, Student> hashMap;
    private NavigableMap<Integer, Student> treeMap;

    @BeforeEach
    void setUp() {
        hashMap = new HashMap<>();
        treeMap = new TreeMap<>(Collections.reverseOrder());
        add(new Student(1, "Anna", 5.0));
        add(new Student(2, "Boris", 7.5));
        add(new Student(3, "Vera", 10.0));
        add(new Student(4, "Gleb", 0.0));
        add(new Student(5, "Dina", 7.5));
    }

    private void add(Student student) {
        hashMap.put(student.id(), student);
        treeMap.put(student.id(), student);
    }

    private Set<Integer> ids(List<Student> students) {
        Set<Integer> result = new HashSet<>();
        for (Student student : students) {
            result.add(student.id());
        }
        return result;
    }

    @Test
    void rangeFindsStudents() {
        List<Student> result = StudentRegistry.findStudentsByGradeRange(hashMap, 5.0, 8.0);
        assertEquals(Set.of(1, 2, 5), ids(result));
    }

    @Test
    void rangeIncludesBothBorders() {
        List<Student> result = StudentRegistry.findStudentsByGradeRange(hashMap, 0.0, 10.0);
        assertEquals(5, result.size());
        assertEquals(Set.of(4), ids(StudentRegistry.findStudentsByGradeRange(hashMap, 0.0, 0.0)));
        assertEquals(Set.of(3), ids(StudentRegistry.findStudentsByGradeRange(hashMap, 10.0, 10.0)));
    }

    @Test
    void rangeWithNoMatches() {
        assertTrue(StudentRegistry.findStudentsByGradeRange(hashMap, 8.0, 9.0).isEmpty());
    }

    @Test
    void rangeOnEmptyMap() {
        assertTrue(StudentRegistry.findStudentsByGradeRange(new HashMap<>(), 0.0, 10.0).isEmpty());
    }

    @Test
    void rangeThrowsOnWrongArguments() {
        assertThrows(IllegalArgumentException.class,
                () -> StudentRegistry.findStudentsByGradeRange(hashMap, 8.0, 5.0));
        assertThrows(IllegalArgumentException.class,
                () -> StudentRegistry.findStudentsByGradeRange(hashMap, -0.1, 5.0));
        assertThrows(IllegalArgumentException.class,
                () -> StudentRegistry.findStudentsByGradeRange(hashMap, 5.0, 10.1));
    }

    @Test
    void rangeDoesNotChangeMap() {
        StudentRegistry.findStudentsByGradeRange(hashMap, 5.0, 8.0);
        assertEquals(5, hashMap.size());
    }

    @Test
    void rangeReturnsNewMutableList() {
        List<Student> result = StudentRegistry.findStudentsByGradeRange(hashMap, 0.0, 10.0);
        result.clear();
        assertEquals(5, hashMap.size());
    }

    @Test
    void topNReturnsLargestIdsInOrder() {
        List<Student> result = StudentRegistry.getTopNStudentsById(treeMap, 3);
        assertEquals(3, result.size());
        assertEquals(5, result.get(0).id());
        assertEquals(4, result.get(1).id());
        assertEquals(3, result.get(2).id());
    }

    @Test
    void topNBiggerThanSize() {
        List<Student> result = StudentRegistry.getTopNStudentsById(treeMap, 100);
        assertEquals(5, result.size());
        assertEquals(5, result.get(0).id());
        assertEquals(1, result.get(4).id());
    }

    @Test
    void topNEqualToSize() {
        assertEquals(5, StudentRegistry.getTopNStudentsById(treeMap, 5).size());
    }

    @Test
    void topZeroIsEmpty() {
        assertTrue(StudentRegistry.getTopNStudentsById(treeMap, 0).isEmpty());
    }

    @Test
    void topNOnEmptyMap() {
        NavigableMap<Integer, Student> empty = new TreeMap<>(Collections.reverseOrder());
        assertTrue(StudentRegistry.getTopNStudentsById(empty, 3).isEmpty());
    }

    @Test
    void topNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () -> StudentRegistry.getTopNStudentsById(treeMap, -1));
    }

    @Test
    void topDoesNotChangeMap() {
        StudentRegistry.getTopNStudentsById(treeMap, 2);
        assertEquals(5, treeMap.size());
        assertEquals(5, treeMap.firstKey());
    }

    @Test
    void topReturnsNewMutableList() {
        List<Student> result = StudentRegistry.getTopNStudentsById(treeMap, 2);
        result.clear();
        assertEquals(5, treeMap.size());
    }

    @Test
    void studentValidation() {
        assertThrows(IllegalArgumentException.class, () -> new Student(0, "A", 5.0));
        assertThrows(IllegalArgumentException.class, () -> new Student(1, "", 5.0));
        assertThrows(IllegalArgumentException.class, () -> new Student(1, "A", 10.5));
        assertThrows(IllegalArgumentException.class, () -> new Student(1, "A", -1.0));
    }

    @Test
    void studentEqualsAndHashCode() {
        Student first = new Student(1, "Anna", 5.0);
        Student second = new Student(1, "Anna", 5.0);
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        List<Student> list = new ArrayList<>();
        list.add(first);
        assertTrue(list.contains(second));
    }
}
