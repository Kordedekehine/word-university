package com.cbt.cbtapp.verifier;

import com.cbt.cbtapp.model.Course;
import com.cbt.cbtapp.model.Lesson;
import com.cbt.cbtapp.model.User;

public class TeacherAccessVerifier implements RightVerifierIn {

    @Override
    public boolean hasAccessToTheDataOf(User user, Course course) {
        return course.getSupervisor().equals(user);
    }

    @Override
    public boolean hasAccessToTheDataOf(User user, Lesson lesson) {
        return hasAccessToTheDataOf(user,lesson.getCourse());
    }

    @Override
    public boolean hasRightToModifyTheDataOf(User user, Course course) {
        return course.getSupervisor().equals(user);
    }

    @Override
    public boolean hasRightToModifyTheDataOf(User user, Lesson lesson) {
        return hasRightToModifyTheDataOf(user,lesson.getCourse());
    }
}
