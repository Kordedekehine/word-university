package com.cbt.cbtapp.verifier;


import com.cbt.cbtapp.model.Student;
import com.cbt.cbtapp.model.Teacher;
import com.cbt.cbtapp.model.User;

public class RightVerifierFactory {

    //check type if they are same
    public static RightVerifierIn getRightVerifier(User user) {
        if (user instanceof Student) {
            return new StudentAccessVerifier();
        }
        if (user instanceof Teacher) {
            return new TeacherAccessVerifier();
        }
        throw new IllegalArgumentException("Unknown user type");
    }
}
