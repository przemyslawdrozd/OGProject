package com.example.ogame.services;

import com.example.ogame.datasource.BuildingDataAccess;
import com.example.ogame.datasource.UserDataAccess;
import com.example.ogame.datasource.VerifyDataAccess;
import com.example.ogame.models.User;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    VerifyDataAccess verifyDataAccess;

    @Mock
    UserDataAccess userDataAccess;

    @Mock
    BuildingDataAccess buildingDataAccess;

    @Mock
    ApplicationUserDao applicationUserDao;

    @InjectMocks
    UserService userService;

    @Before
    public void setUp() throws Exception {
        given(userService.getAllUsers()).willReturn(getUserList());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldGetAllUsers() {
        Assert.assertThat(userService.getAllUsers(), Matchers.hasSize(3));
    }

    @Test
    public void shouldCreateNerUser() {
//        userService.createNewUser()
    }

    public List<User> getUserList() {
        return Arrays.asList(
                new User("user", "123", "user@mail.com"),
                new User("user2", "123", "user2@mail.com"),
                new User("user3", "123", "user3@mail.com")
        );
    }
}