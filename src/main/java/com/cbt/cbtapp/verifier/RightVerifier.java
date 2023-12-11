package com.cbt.cbtapp.verifier;


import com.cbt.cbtapp.model.Course;
import com.cbt.cbtapp.model.Lesson;
import com.cbt.cbtapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class RightVerifier implements RightVerifierIn {


    @Override
    public boolean hasAccessToTheDataOf(User user, Course course) {
        return RightVerifierFactory.getRightVerifier(user).hasAccessToTheDataOf(user, course);
    }

    @Override
    public boolean hasAccessToTheDataOf(User user, Lesson lesson) {
        return RightVerifierFactory.getRightVerifier(user).hasAccessToTheDataOf(user, lesson);
    }

    @Override
    public boolean hasRightToModifyTheDataOf(User user, Course course) {
        return RightVerifierFactory.getRightVerifier(user).hasRightToModifyTheDataOf(user, course);
    }

    @Override
    public boolean hasRightToModifyTheDataOf(User user, Lesson lesson) {
        return RightVerifierFactory.getRightVerifier(user).hasRightToModifyTheDataOf(user, lesson);
    }
}
