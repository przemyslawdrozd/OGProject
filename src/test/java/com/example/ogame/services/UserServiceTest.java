package com.example.ogame.services;

import com.example.ogame.datasource.UserDataAccess;
import com.example.ogame.datasource.VerifyDataAccess;
import com.example.ogame.exeptions.ApiRequestException;
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

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    VerifyDataAccess verifyDataAccess;

    @Mock
    UserDataAccess userDataAccess;

    @InjectMocks
    UserService userService;

    User user;

    @Before
    public void setUp() throws Exception {

        given(userService.getAllUsers()).willReturn(getUserList());

        given(verifyDataAccess.ifThisUserExists("user", "123")).willReturn(true);

        given(userService.getUserByUsernamePassword("user", "123"))
                .willReturn(user);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void shouldGetAllUsers() {
        Assert.assertThat(userService.getAllUsers(), Matchers.hasSize(3));
    }

    @Test
    public void shouldGetUserByUsernamePassword() {
        Assert.assertThat(userService.getUserByUsernamePassword("user", "123"), Matchers.is(user));
    }

    @Test
    public void shouldNotGetUserByUsernamePassword() {
        given(verifyDataAccess.ifThisUserExists("nonExistUser", "123")).willReturn(true);
        User nonExistUser = new User("nonExistUser", "123", "user@email.com");
        Assert.assertThat(userService.getUserByUsernamePassword("nonExistUser", "123"), Matchers.not(nonExistUser));
    }

    @Test(expected = ApiRequestException.class)
    public void shouldThrowApiRequestException() {
        given(verifyDataAccess.ifThisUserExists("nonExistUser", "123")).willReturn(false);
        Assert.assertThat(userService.getUserByUsernamePassword("nonExistUser", "123"), Matchers.not(user));
    }

    public List<User> getUserList() {
        return Arrays.asList(
                user,
                new User("user2", "123", "user2@mail.com"),
                new User("user3", "123", "user3@mail.com")
        );
    }
}