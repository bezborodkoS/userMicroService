package com.example.userservice;

import com.example.userservice.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileUserComp {
    private static final String FILE_NAME= "users.txt";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<User> readUsers() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(file, new TypeReference<List<User>>() {});
        } catch (IOException e) {
            throw new RuntimeException("cant read users from file", e);
        }
    }

    public void writeUsers(List<User> users) {
        try {
            objectMapper.writeValue(new File(FILE_NAME), users);
        } catch (IOException e) {
            throw new RuntimeException("cant write courses to file", e);
        }
    }
}
