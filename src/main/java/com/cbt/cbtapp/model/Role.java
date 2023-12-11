package com.cbt.cbtapp.model;

import com.cbt.cbtapp.dto.UserRequestDto;

public enum Role {

    STUDENT("STUDENT") {
        @Override
        public User buildUser(UserRequestDto userRequestDto, Language language) {
            return new Student(userRequestDto.getName(), userRequestDto.getPassword(),userRequestDto.getRole(),
                   userRequestDto.getEmailAddress(), language);
        }
    },


    TEACHER("TEACHER") {
        @Override
        public User buildUser(UserRequestDto userRequestDto, Language language) {
            return  new Teacher(userRequestDto.getName(), userRequestDto.getPassword(),userRequestDto.getRole(),
                    userRequestDto.getEmailAddress(), language);
        }
    };

    private final String createRole;

    Role(String createRole) {
        this.createRole = createRole;
    }


    public String getCreateRole() {
        return createRole;
    }

    public abstract User buildUser(UserRequestDto userRequestDto, Language language);
}

