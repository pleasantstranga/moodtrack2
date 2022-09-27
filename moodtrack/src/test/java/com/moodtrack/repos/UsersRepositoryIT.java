package com.moodtrack.repos;

import com.moodtrack.entities.Users;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersRepositoryIT {
    @Autowired
    UsersRepository undertest;

    @Test
    public void testFindAll()  {
        List<Users> allUsers = undertest.findAll().stream().toList();
        assertTrue(allUsers.size() == 3);
        assertTrue(allUsers.iterator().next().getFName().equals("Aaron"));

    }

}
