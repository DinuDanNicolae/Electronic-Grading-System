

public class BestExamScoreStrategy implements BestStudentStrategy {
    @Override
    public Student selectBestStudent(Course course) {
        Student bestStudent = null;
        double highestExamScore = 0.0;
        for (Grade grade : course.getGrades()) {
            double ExamScore = grade.getExamScore();
            if (ExamScore > highestExamScore) {
                highestExamScore = ExamScore;
                bestStudent = grade.getStudent();
            }
        }
        return bestStudent;
    }
}

