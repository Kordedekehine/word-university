package com.cbt.cbtapp.exception.lessons;

/**
 * Exception thrown when an exercise for a lesson is requested, but there are no unknown words,
 * meaning that all the words that have been previously learnt as 'unknown' by the student, have
 * been studied thoroughly and reached the target points through the practice exercises.
 */
public class NoWordsToLearnException extends Exception {
}
