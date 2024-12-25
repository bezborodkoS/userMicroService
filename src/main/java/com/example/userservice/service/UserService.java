package com.example.userservice.service;

import com.example.userservice.FileUserComp;
import com.example.userservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final FileUserComp fileUserComp;

    public UserService(FileUserComp fileUserComp) {
        this.fileUserComp = fileUserComp;
    }

    public User saveUser(User user){
        List<User> users = fileUserComp.readUsers();
        user.setId(idGenerator.getAndIncrement());
        users.add(user);
        fileUserComp.writeUsers(users);
        return user;
    }

    public User getUserById(Long id){
        List<User> userList = fileUserComp.readUsers();
        return userList.stream()
                .filter(user -> user.getId()==id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public User updateUser(Long id, User updateUser){
        List<User> userList = fileUserComp.readUsers();
        Optional<User> optionalCourse = userList.stream()
                .filter(user -> user.getId()==id)
                .findFirst();

        if (optionalCourse.isEmpty()) {
            throw new RuntimeException("user not found");
        }

        User user = optionalCourse.get();
        user.setName(updateUser.getName());
        user.setSurname(updateUser.getSurname());
        user.setAge(updateUser.getAge());
        fileUserComp.writeUsers(userList);
        return user;
    }

    public void deleteUser(Long id) {
        List<User> userList = fileUserComp.readUsers();
        userList.removeIf(user -> user.getId()==(id));
        fileUserComp.writeUsers(userList);
    }
}
