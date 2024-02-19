package com.home.profile.student;

import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Super;

public class StudentValidationException extends Exception {
    public StudentValidationException (String message) {
        super(message);
    }
}
