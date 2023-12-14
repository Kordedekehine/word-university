package com.cbt.cbtapp.service.utilService;

import org.springframework.stereotype.Service;

@Service
public class ScoreUpdater {


    public static final int CORRECT_ANSWER = 1;

    public static final int BAD_ANSWER = -1;

    public int getNewPoints(int currentPoints, boolean correctAnswer) {
        if (correctAnswer) {
            return currentPoints + CORRECT_ANSWER ;
        } else {
            return currentPoints + BAD_ANSWER;
        }
    }


}
