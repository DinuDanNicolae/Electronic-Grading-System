# Electronic-Grading-System

Dinu Dan Nicolae 323CC
The project carried out includes the implementation of the application's architecture as well as the first proposed interface, namely the student page interface.
The architecture of the application contains the implementation of all proposed classes and methods, as well as additional methods for ease of resolution.
The Catalog class is created following the Singleton pattern, thus ensuring the existence of a single instance that can be accessed from any class of the project. This also contains a list of objects of type Course. 
The Course class defines all aspects related to a course: student groups, assistants, the teacher, and grades. This implements the Builder pattern. This is also implemented in the classes that branch out the type of course, namely PartialCourse and FullCourse, which inherit the Course class. 
The Group, Student, Teacher, User, Parent, Grade, and Assistant classes comply with the required methods as well as other additional methods.
The Observer design pattern allows parents set to a student to receive notifications when a grade has been added or modified by an assistant or a teacher, respectively. 
The Observer interface is inherited by the Parent class, and the Subject interface by the Catalog class.
The Strategy design pattern allows setting a strategy for a course. The three strategies are implemented in separate classes, implementing the SelectBestStudentStrategy interface. 
Depending on the strategy set for a certain course, the call to getBestStudent will return the best student according to the chosen strategy.
The Visitor design pattern allows adding grades to students by assistants or teachers. The Element interface is implemented by the Teacher and Assistant classes, and Visitor by the ScoreVisitor class. 
This contains two dictionaries in which grades are added by assistants and teachers, respectively, later the contents of the dictionary being added to the Catalog, and the parents notified.
The Memento design pattern allows the creation of a backup of the grades for a certain course and an undo functionality of the set grades. 
The Grade class contains a method for cloning the grades, which will later be added to the "backup" through the Snapshot class.
The Student Page graphical interface allows the search for a student by their name, displaying a list of the courses the student is enrolled in, and upon selecting a course, the information about the student enrolled in the chosen course will be displayed. 
Courses are displayed after pressing the "Submit" button.
The testing of the application is carried out by parsing a Json file from which all necessary information is extracted. 
The output initially displays all notifications following the addition of student grades by assistants and teachers, and subsequently iterates through each course and displays the name, teacher, assistants, group, and the methods “getAllStudent”, “getAllStudentsGrades”, “getGraduatedStudents” and “getBestStudent”. 
Then it iterates through each student from a course to display the name of the student, the parents, and the grades for the partial, exam, and total accumulated score. 
Finally, it iterates for each course, makes a backup of the grades, and then adds incorrect grades to all students. The grades are displayed with those added "incorrectly", and after the undo call, only the grades from the time of the backup call without the "incorrect" ones will be displayed.
The degree of difficulty of the project was medium, and the time allocated for its resolution was approximately one week.
