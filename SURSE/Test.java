

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Test {

   public static void main(String[] args) throws IOException, ParseException {

       Catalog catalaog = Catalog.getInstance();
       JSONParser parser = new JSONParser();
       StudentCoursesPage coursePage = new StudentCoursesPage();

       try {
           FileReader file = new FileReader("C:\\Users\\danut\\IdeaProjects\\TemaPoo\\src\\to-parse.json");
           JSONObject jsonObject = (JSONObject) parser.parse(file);
           JSONArray jsonArraycourses = (JSONArray) jsonObject.get("courses");
           for (int i = 0; i < jsonArraycourses.size(); i++) {
               JSONObject course = (JSONObject) jsonArraycourses.get(i);

               String courseType = (String) course.get("type");
//               System.out.println("COURSE TYPE: " + courseType);

               String courseStrategy = (String) course.get("strategy");
//               System.out.println("COURSE STRATEGY: " + courseStrategy);

               String courseName = (String) course.get("name");
//               System.out.println("COURSE NAME: " + courseName);

               JSONObject teacher = (JSONObject) course.get("teacher");
               String professorFirstName = (String) teacher.get("firstName");
//               System.out.println("TEACHER FIRST NAME: " + professorFirstName);
               String professorLastName = (String) teacher.get("lastName");
//               System.out.println("TEACHER LAST NAME: " + professorLastName);

               Teacher courseTeacher = new Teacher(professorFirstName,professorLastName);

               JSONArray jsonArrayassistants = (JSONArray) course.get("assistants");
               HashSet<Assistant> assistants = new HashSet<>();
               TreeSet<Student> all_Students = new TreeSet<>();
               for (int j = 0; j < jsonArrayassistants.size(); j++) {
                   JSONObject assistant = (JSONObject) jsonArrayassistants.get(j);
                   String assistantFirstName = (String) assistant.get("firstName");
//                   System.out.println("ASSISTANT FIRST NAME: " + assistantFirstName);
                   String assistantLastName = (String) assistant.get("lastName");
//                   System.out.println("ASSISTANT LAST NAME: " + assistantLastName);

                   Assistant assistantTobePlaced = new Assistant(assistantFirstName,assistantLastName);
                   assistants.add(assistantTobePlaced);
               }

               JSONArray jsonArraygroups = (JSONArray) course.get("groups");
               HashMap<String, Group> groupsToBePlaced = new HashMap<>();
               for (int j = 0; j < jsonArraygroups.size(); j++) {
                   JSONObject groups = (JSONObject) jsonArraygroups.get(j);
                   String groupID = (String) groups.get("ID");
//                   System.out.println("GROUP ID: " + groupID);
                   JSONObject groupAssistant = (JSONObject) groups.get("assistant");
                   String assistantFirstName = (String) groupAssistant.get("firstName");
//                   System.out.println("ASSISTANT FIRST NAME: " + assistantFirstName);
                   String assistantLastName = (String) groupAssistant.get("lastName");
//                   System.out.println("ASSISTANT LAST NAME: " + assistantLastName);

                   Assistant GroupAssistant = new Assistant(assistantFirstName, assistantLastName);

                   JSONArray jsonArraystudents = (JSONArray) groups.get("students");
                   for (int k = 0; k < jsonArraystudents.size(); k++) {
                       JSONObject student = (JSONObject) jsonArraystudents.get(k);
                       String studentFirstName = (String) student.get("firstName");
//                       System.out.println("STUDENT FIRST NAME: " + studentFirstName);
                       String studentLastName = (String) student.get("lastName");
//                       System.out.println("STUDENT LAST NAME: " + studentLastName);

                        Student studentToBePlaced;
                       if(catalaog.getStudent(studentFirstName,studentLastName) != null) {
                           studentToBePlaced = catalaog.getStudent(studentFirstName,studentLastName);
                       }
                       else {
                           studentToBePlaced = new Student(studentFirstName,studentLastName);
                       }

                       Object mother = student.get("mother");
                       if(mother != null) {
                           JSONObject studentMother = (JSONObject) student.get("mother");

                           String studentMotherFirstName = (String) studentMother.get("firstName");
//                           System.out.println("STUDENT MOTHER FIRST NAME: " + studentMotherFirstName);
                           String studentMotherLastName = (String) studentMother.get("lastName");
//                           System.out.println("STUDENT MOTHER LAST NAME: " + studentMotherLastName);

                           Parent motherToBePlaced = new Parent(studentMotherFirstName,studentMotherLastName);
                           studentToBePlaced.setMother(motherToBePlaced);
                           if(!catalaog.getObserver().contains(motherToBePlaced)){
                               catalaog.addObserver(motherToBePlaced);
                           }

                       }

                       Object father = student.get("father");
                       if(father != null) {
                           JSONObject studentFather = (JSONObject) student.get("father");

                           String studentFatherFirstName = (String) studentFather.get("firstName");
//                           System.out.println("STUDENT FATHER FIRST NAME: " + studentFatherFirstName);
                           String studentFatherLastName = (String) studentFather.get("lastName");
//                           System.out.println("STUDENT FATHER LAST NAME: " + studentFatherLastName);

                           Parent fatherToBePlaced = new Parent(studentFatherFirstName,studentFatherLastName);
                           studentToBePlaced.setFather(fatherToBePlaced);

                           if(!catalaog.getObserver().contains(fatherToBePlaced)) {
                               catalaog.addObserver(fatherToBePlaced);
                           }

                       }
                       all_Students.add(studentToBePlaced);
                   }
                   Group group = new Group(groupID,GroupAssistant);
                   group.addAll(all_Students);
                   group.setAssistant(GroupAssistant);
                   groupsToBePlaced.put(groupID,group);
               }
               System.out.println("\n");
               if(courseType.equals("FullCourse")) {
                   FullCourse.FullCourseBuilder curs = new FullCourse.FullCourseBuilder(courseName, courseTeacher, 6);
                   FullCourse FinalCourse = (FullCourse) curs.build();
                   FinalCourse.setAssistants(assistants);
                   FinalCourse.setGroups(groupsToBePlaced);
                   FinalCourse.setCourseStrategy(FinalCourse,courseStrategy);
                   catalaog.addCourse(FinalCourse);
               }
               else if(courseType.equals("PartialCourse")) {
                   PartialCourse.PartialCourseBuilder curs = new PartialCourse.PartialCourseBuilder(courseName,courseTeacher,6);
                   PartialCourse FinalCourse = (PartialCourse) curs.build();
                   FinalCourse.setAssistants(assistants);
                   FinalCourse.setGroups(groupsToBePlaced);
                   FinalCourse.setCourseStrategy(FinalCourse,courseStrategy);
                   catalaog.addCourse(FinalCourse);
               }
           }

           ScoreVisitor visitor = new ScoreVisitor();
           JSONArray jsonArrayExamScores = (JSONArray) jsonObject.get("examScores");
           for( int i = 0; i < jsonArrayExamScores.size(); i++) {
               JSONObject examScore = (JSONObject) jsonArrayExamScores.get(i);

               JSONObject examScoreTeacher = (JSONObject) examScore.get("teacher");
               String examScoreTeacherFirstName = (String) examScoreTeacher.get("firstName");
//               System.out.println("EXAM SCORE TEACHER FIRST NAME: " + examScoreTeacherFirstName);
               String examScoreTeacherLastName = (String) examScoreTeacher.get("lastName");
//               System.out.println("EXAM SCORE TEACHER LAST NAME: " + examScoreTeacherLastName);

               JSONObject examScoreStudent = (JSONObject) examScore.get("student");
               String examScoreStudentFirstName = (String) examScoreStudent.get("firstName");
//               System.out.println("EXAM SCORE STUDENT FIRST NAME: " + examScoreStudentFirstName);
               String examScoreStudentLastName = (String) examScoreStudent.get("lastName");
//               System.out.println("EXAM SCORE STUDENT LAST NAME: " + examScoreStudentLastName);

               String examScoreCourseName = (String) examScore.get("course");
//               System.out.println("EXAM SCORE COURSE NAME: " + examScoreCourseName);

               double examScoreGrade = (double) examScore.get("grade");
//               System.out.println("EXAM SCORE GRADE: " + examScoreGrade);

               Teacher ExamScoreTeacher = new Teacher(examScoreTeacherFirstName,examScoreTeacherLastName);
//               Student ExamScoreStudent = new Student(examScoreStudentFirstName,examScoreStudentLastName);
                Student ExamScoreStudent = catalaog.getStudent(examScoreStudentFirstName,examScoreStudentLastName);

               visitor.addExamGrade(ExamScoreTeacher,ExamScoreStudent,examScoreGrade,examScoreCourseName);
//               System.out.println(visitor.examScores);
               ExamScoreTeacher.accept(visitor);

//               System.out.println("\n");
           }
//           ScoreVisitor visitor = new ScoreVisitor();
           JSONArray jsonArrayPartialScores = (JSONArray) jsonObject.get("partialScores");
           for( int i = 0; i < jsonArrayPartialScores.size(); i++) {
               JSONObject partialScore = (JSONObject) jsonArrayPartialScores.get(i);

               JSONObject partialScoreAssistant = (JSONObject) partialScore.get("assistant");
               String partialScoreAssistantFirstName = (String) partialScoreAssistant.get("firstName");
//               System.out.println("PARTIAL SCORE ASSISTANT FIRST NAME: " + partialScoreAssistantFirstName);
               String partialScoreAssistantLastName = (String) partialScoreAssistant.get("lastName");
//               System.out.println("PARTIAL SCORE ASSISTANT LAST NAME: " + partialScoreAssistantLastName);

               JSONObject partialScoreStudent = (JSONObject) partialScore.get("student");
               String partialScoreStudentFirstName = (String) partialScoreStudent.get("firstName");
//               System.out.println("PARTIAL SCORE STUDENT FIRST NAME: " + partialScoreStudentFirstName);
               String partialScoreStudentLastName = (String) partialScoreStudent.get("lastName");
//               System.out.println("PARTIAL SCORE STUDENT LAST NAME: " + partialScoreStudentLastName);

//               JSONObject examScoreCourse = (JSONObject) examScore.get("course");
               String partialScoreCourseName = (String) partialScore.get("course");
//               System.out.println("PARTIAL SCORE COURSE NAME: " + partialScoreCourseName);

               double partialScoreGrade = (double) partialScore.get("grade");
//               System.out.println("PARTIAL SCORE GRADE: " + partialScoreGrade);

               Assistant PartialScoreAssistant = new Assistant(partialScoreAssistantFirstName,partialScoreAssistantLastName);
               Student PartialScoreStudent = new Student(partialScoreStudentFirstName,partialScoreStudentLastName);

               visitor.addPartialGrade(PartialScoreAssistant,PartialScoreStudent,partialScoreGrade,partialScoreCourseName);
               PartialScoreAssistant.accept(visitor);
//               System.out.println("\n");
           }

           for(Course course : catalaog.getCourses()) {
               System.out.println("COURSE NAME: " + course.getName());
               System.out.println("COURSE TEACHER: " + course.getProfessor());
               System.out.println("COURSE ASSISTANTS: " + course.getAssistants());
               System.out.println("COURSE GROUPS: " + course.getGroups());

               ArrayList<Student> allStudents = course.getAllStudents();
               System.out.println("All students: " + allStudents);

               HashMap<Student, Grade> allStudentGrades = course.getAllStudentGrades();
               System.out.println("All student grades: " + allStudentGrades);

               System.out.println("GRADES: " + course.getGrades());

               ArrayList<Student> graduatedStudents = course.getGraduatedStudents();
               System.out.println("Graduated students: " + graduatedStudents);

               Student the_best = course.getBestStudent();
               System.out.println("Best Student is: " + the_best);

               System.out.println("\n");
               for(Student student : course.getAllStudents()) {
                   System.out.println("STUDENT NAME: " + student.getName());
                   System.out.println("STUDENT MOTHER: " + student.getMother());
                   System.out.println("STUDENT FATHER: " + student.getFather());
                   Grade grade = course.getGrade(student);
                   System.out.println("STUDENTUL ARE NOTELE: " + "partial: "+ grade.getPartialScore() + " + " + " examen: " + grade.getExamScore() + " = " + " total: "+grade.getTotal());
                   System.out.println("\n");
               }
           }
           for(Course course : catalaog.getCourses()) {
               course.makeBackup();
               for(Student student: course.getAllStudents()) {
                   Grade grade = new Grade(student,course.getName());
                   grade.setPartialScore(99999.0);
                   grade.setExamScore(99999.0);
                   course.addGrade(grade);
               }
               System.out.println("MODIFIED COURSE BEFORE UNDO: "+course.getGrades());
               course.undo();
               System.out.println("BACKUP COURSE AFTER UNDO: " + course.getGrades());
           }

       } catch (IOException e) {
           throw new RuntimeException(e);
       } catch (ParseException e) {
           throw new RuntimeException(e);
       }
   }


}
