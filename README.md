# **Software Engineering Documentation**

# **Introduction**

This software engineering documentation describes the design and architecture of a learning management system for all kinds of languages. The system aims to efficiently manage both teachers and students in a college like way.  The system will handle various aspects such as user management(enrollment, courses, lessons, scoring,onboarding and all), language translation, and activities monitoring.

Java 17 improved(features) version of Fakaza's language app.

# **Requirements**

The following requirements have been identified for the drone management system:

### **TEACHER MANAGEMENT**

The system supports the teacher authentication and authorization.
Teacher should be authorized to create a course he/she will supervise.
Teacher should have each lesson sessions in the course where they upload a pdf text for pupils to read .
The system supports the system giving assignments through pdfs and students will be autograded.


### **STUDENT MANAGEMENT**

The system supports the student authentication and authorization.
The student can pick from the many courses and enroll for it.
The student should get and read the lessons text
The student can mark and sideline unknown words
The system allow student to practice random unknown words and earn or lose points
The system allow student to create a personal course and upload pdfs in lessons format


## **FLEXIBILITIES**

The app helps students translate unknown words they encounter when reading through texts
The app publicizes the best current students
The app rewards students with points when they successfully translate a trivial keyword
The app reduces student points when they fail to translate a trivial word correctly
Student scores and performances are recorded and at a point be rewarded
The app retrieves the list of all users according to their registered date



# **Design Considerations**

## **Design Patterns**

To design the cbt-language system, the following design patterns can be considered:

Factory Pattern: The Factory pattern can be used to create instances of students and teachers. It allows for flexible object creation without specifying the exact class of the object that will be created.



## **Software Architecture Design**

The word university system can be designed using a layered architecture, specifically the Model-View-Controller (MVC) pattern. This pattern separates the system into three major components:

Model: The Model component represents the data and business logic of the system. It includes classes such as Student, Teacher, Course, Lesson, Translation responsible for managing the education platform.
View: The View component handles the presentation and user interface of the system. It includes the user interfaces for creating courses, enrolling, reading and attempting exercises.
Controller: The Controller component acts as an intermediary between the Model and View components. It receives user input from the View, performs necessary operations on the Model, and updates the View accordingly. It includes classes such as Authentication Controller, StudentManagement Controller, Teacher Management Controller and so on.
The layered architecture ensures separation of concerns and modularity, making the system easier to maintain and extend.


## **Environment**

Install JDK 17 (https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) , IDE Intellij IDEA

### **How to run**

Clone or download zip file from git
Access the root folder then open with an IDE such as Netbeans, Eclipse, Intelij ...
Allow maven to sync dependencies by turning ON your internet connectivity.
After a successful maven sync you can now
run the SpringBoot-Bank-App file
For documentation (http://localhost:8080/swagger-ui.html#/)
Replicate the basic functions of digital banking. The app should provide parents with:

The Basic API OF Word University

Architecture and Design part:

Creating Diagrams:

Class

Use cases

Deployment

Package

To analyse

Sequence

Object

SpringBoot Back-End part:

Connection With The MySQL database through php myadmin.

Creation of Entity tables.

Creation of Relations Between Entity Tables.

Creation of Crud (View, Delete, Add, Edit).

Creation of Advanced Trades.

Creation of Services.

Consumption of Rest.

Creation of APIs (Translation, Pdf exchange, Course setups, Lesson updates, Mapping, Authentication and so on)