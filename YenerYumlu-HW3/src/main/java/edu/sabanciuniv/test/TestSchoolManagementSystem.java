package edu.sabanciuniv.test;

import edu.sabanciuniv.model.Course;
import edu.sabanciuniv.model.Instructor;
import edu.sabanciuniv.model.InstructorType;
import edu.sabanciuniv.model.Student;
import jakarta.persistence.*;

import java.time.LocalDate;

public class TestSchoolManagementSystem {
    public static void main(String[] args) {
        createTestData();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqlStudent");
        EntityManager entityManager = emf.createEntityManager();
        findStudentByName(entityManager,"Ali Veli");
        findCourseByCode(entityManager,"IT526");
        findInstructorByName(entityManager,"Koray Güney");


        System.out.println("All tests are completed!");
    }

    private static void createTestData(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqlStudent");
        EntityManager entityManager = emf.createEntityManager();

        removeAllData();

        Student student1 = new Student("Ali Veli", LocalDate.of(2000, 1, 3), "Kadıköy", "M");
        Student student2 = new Student("Ayşe Fatma", LocalDate.of(2001, 2, 4), "Üsküdar", "F");
        Student student3 = new Student("Ahmet Sezgin", LocalDate.of(2002, 3, 5), "Beşiktaş", "M");
        Student student4 = new Student("Derya Yılmaz", LocalDate.of(2003, 4, 6), "Maltepe", "F");
        Student student5 = new Student("Mehmet Şahin", LocalDate.of(2004, 5, 7), "Şişli", "M");

        Course course1 = new Course("Enterprise Java Frameworks and Design Patterns", "IT526", 3);
        Course course2 = new Course("Introduction to Machine Learning", "IT541", 3);
        Course course3 = new Course("Software Testing", "IT538", 3);

        Instructor instructor1 = new Instructor("Koray Güney", "Kadıköy", "5001001010",1000, InstructorType.VisitingResearcher);
        Instructor instructor2 = new Instructor("Altuğ Tanaltay", "Kadıköy", "5001001011",1000, InstructorType.PermanentInstructor);
        Instructor instructor3 = new Instructor("Ömer Karacan", "Munich", "5001001012",1000, InstructorType.VisitingResearcher);

        student1.enrollIn(course1);
        student1.enrollIn(course2);
        student2.enrollIn(course1);
        student3.enrollIn(course2);
        student4.enrollIn(course2);
        student5.enrollIn(course3);

        course1.setInstructor(instructor1);
        course2.setInstructor(instructor2);
        course3.setInstructor(instructor3);

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(student1);
            entityManager.persist(student2);
            entityManager.persist(student3);
            entityManager.persist(student4);
            entityManager.persist(student5);

            entityManager.persist(course1);
            entityManager.persist(course2);
            entityManager.persist(course3);

            entityManager.persist(instructor1);
            entityManager.persist(instructor2);
            entityManager.persist(instructor3);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.clear();
            entityManager.close();
            System.out.println("All test data are created!");
        }

    }

    private static void findStudentByName(EntityManager entityManager, String name) {
        try {
            Student foundStudent = entityManager.createQuery("FROM Student s WHERE s.name = ?1", Student.class)
                .setParameter(1, name).getSingleResult();
            System.out.println(foundStudent);
        } catch (NoResultException e) {
            System.out.println("no result found for name: " + name);
        }
    }

    private static void findInstructorByName(EntityManager entityManager, String name) {
        try {
            Instructor foundInstructor = entityManager.createQuery("FROM Instructor s WHERE s.name = ?1", Instructor.class)
                    .setParameter(1, name).getSingleResult();
            System.out.println(foundInstructor);
        } catch (NoResultException e) {
            System.out.println("no result found for name: " + name);
        }
    }

    private static void findCourseByCode(EntityManager entityManager, String courseCode) {
        try {
            Course foundCourse = entityManager.createQuery("FROM Course s WHERE s.courseCode = ?1", Course.class)
                    .setParameter(1, courseCode).getSingleResult();
            System.out.println(foundCourse);
        } catch (NoResultException e) {
            System.out.println("no result found for courseCode: " + courseCode);
        }
    }

    public static int hqlTruncate(EntityManager entityManager, String myTable){
        String hql = String.format("delete from %s", myTable);
        Query query = entityManager.createQuery(hql);
        return query.executeUpdate();
    }

    public static void removeAllData(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqlStudent");
        EntityManager entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            hqlTruncate(entityManager, "Student");
            hqlTruncate(entityManager, "Course");
            hqlTruncate(entityManager, "Instructor");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.clear();
            entityManager.close();
        }
    }
}