package com.cbt.cbtapp.verifier;

import com.cbt.cbtapp.model.Course;
import com.cbt.cbtapp.model.Lesson;
import com.cbt.cbtapp.model.User;

public interface RightVerifierIn {


    boolean hasAccessToTheDataOf(User user, Course course);


    boolean hasAccessToTheDataOf(User user, Lesson lesson);


    boolean hasRightToModifyTheDataOf(User user, Course course);


    boolean hasRightToModifyTheDataOf(User user, Lesson lesson);
}
