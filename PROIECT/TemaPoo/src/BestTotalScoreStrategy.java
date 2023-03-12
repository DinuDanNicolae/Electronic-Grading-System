

public class BestTotalScoreStrategy implements BestStudentStrategy{
    @Override
    public Student selectBestStudent(Course course) {
        Student bestStudent = null;
        double highestTotalScore = 0.0;
        for (Grade grade : course.getGrades()) {

            double TotalScore = grade.getTotal();
            if (TotalScore > highestTotalScore) {
                highestTotalScore = TotalScore;
                bestStudent = grade.getStudent();
            }
        }
        return bestStudent;
    }
}

