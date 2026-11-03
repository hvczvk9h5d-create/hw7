package collections;

public record Student(int id, String name, double grade) {

    public Student {
        if (id <= 0) {
            throw new IllegalArgumentException("id must be positive");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be empty");
        }
        if (grade < 0.0 || grade > 10.0) {
            throw new IllegalArgumentException("grade must be in [0, 10]");
        }
    }
}
