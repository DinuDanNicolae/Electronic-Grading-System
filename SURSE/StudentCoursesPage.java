

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentCoursesPage extends JFrame implements ActionListener {

    private JFrame frame;
    private JTextArea infoArea = new JTextArea();
    private JTextField studentField;
    private JButton submitButton;

    public Student student;
    private final static String newline = "\n";

    Catalog cat = Catalog.getInstance();

    public StudentCoursesPage() {
        frame = new JFrame("Student Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel studentLabel = new JLabel("Student: ");
        studentField = new JTextField(30);
        JPanel studentPanel = new JPanel();
        studentPanel.add(studentLabel);
        studentPanel.add(studentField);
        JPanel coursePanel = new JPanel();
        JLabel couseLabel = new JLabel("Courses: ");
        coursePanel.add(couseLabel);

        DefaultListModel<String> model = new DefaultListModel<>();

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String studentString = studentField.getText();
                if (!studentString.isEmpty()) {
                    String[] name = studentString.split("\\s+");
                    String firstName = name[0];
                    String lastName = name[1];
                    student = new Student(firstName, lastName);
                    ArrayList<Course> courses = cat.getCourses();
                    for(Course course: courses) {
                        for(Student s: course.getAllStudents()) {
                            if(s.getFirstName().equals(firstName) && s.getLastName().equals(lastName)) {
                                student = s;
                                model.addElement(course.getName());
                            }
                        }
                    }
                }
            }
        });
        JList list = new JList<>(model);
        ListSelectionModel sModel = list.getSelectionModel();
        sModel.addListSelectionListener( new ListSelectionListener() {
            public void valueChanged (ListSelectionEvent e) {
                if ( !e.getValueIsAdjusting() ) {
                    coursePanel.setVisible(false);
                    int index = list.getSelectedIndex();
                    for(Course course: cat.getCourses()) {
                        if(list.getSelectedValue().equals(course.getName())) {
                            infoArea.setText("Course: " + course.getName() + newline);
                            infoArea.append("Teacher: " + course.getProfessor().getFirstName() + " " + course.getProfessor().getLastName() +  newline);
                            infoArea.append("Assistants: " + course.getStudentGrup(student).getAssistant() + newline);
                            infoArea.append("Groups: " + course.getStudentGrup(student).getID() + course.getStudentGrup(student).getStudents() + newline);
                            infoArea.append("Grades: " + course.getGrade(student));
                        }
                    }
                }
            }
        });
        coursePanel.add(list);

        frame.add(studentPanel, BorderLayout.NORTH);
        frame.add(coursePanel,BorderLayout.SOUTH);
        frame.add(submitButton, BorderLayout.EAST);
        frame.add(infoArea,BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
