

public class BestPartialScoreStrategy implements BestStudentStrategy {
    @Override
    public Student selectBestStudent(Course course) {
        Student bestStudent = null;
        double highestPartialScore = 0.0;
        for (Grade grade : course.getGrades()) {
            double partialScore = grade.getPartialScore();
            if (partialScore > highestPartialScore) {
                highestPartialScore = partialScore;
                bestStudent = grade.getStudent();
            }
        }
        return bestStudent;
    }
}