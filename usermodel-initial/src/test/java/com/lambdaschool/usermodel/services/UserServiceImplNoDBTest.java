package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplicationTesting;
import com.lambdaschool.usermodel.exceptions.ResourceNotFoundException;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
//weed need springrunner for junit4 testing
@SpringBootTest(classes = UserModelApplicationTesting.class,
    properties = {
        "command.line.runner.enabled=false"
    })
// We have a spring made application so we need the springboottest annotation
//properties is to use mock data during the testing process
public class UserServiceImplNoDBTest {
    @Autowired
    private UserService userService;
//    Mock -> Fake data
//    Stubs -> Fake methods
//    In Java - Mock and Stubs are both called mocks but reference fake data or methods to run tests.
// Then Add a Mock Bean repo for the repos in the servicesImpl

    @MockBean
    private HelperFunctions helperFunctions;

    @MockBean
    private UserRepository userrepos;

    @MockBean
    private RoleService roleService;


    private List<User> userList = new ArrayList<>();
    @Before
    public void setUp() throws Exception {

        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

//        r1 = roleService.save(r1);
//        r2 = roleService.save(r2);
//        r3 = roleService.save(r3);
//        Update the save method that is adding the id to making one for ourself.
        r1.setRoleid(1);
        r2.setRoleid(2);
        r3.setRoleid(3);

        // admin, data, user
        User u1 = new User("Test Admin",
                "password",
                "admin@lambdaschool.local");
//        Assign the U1 user a user id
        u1.setUserid(1);
        u1.getRoles()
                .add(new UserRoles(u1,
                        r1));
        u1.getRoles()
                .add(new UserRoles(u1,
                        r2));
        u1.getRoles()
                .add(new UserRoles(u1,
                        r3));
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@email.local"));
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@mymail.local"));

//        userService.save(u1);
//        Update to add each useremail individually
        u1.getUseremails().get(0).setUseremailid(1);
        u1.getUseremails().get(1).setUseremailid(2);
//        Then take our array of users we made and add(the user to it).
        userList.add(u1);

        // data, user


        // user
        User u3 = new User("barnbarn",
                "ILuvM4th!",
                "barnbarn@lambdaschool.local");
        u3.setUserid(3);
        u3.getRoles()
                .add(new UserRoles(u3,
                        r2));
        u3.getUseremails()
                .add(new Useremail(u3,
                        "barnbarn@email.local"));
        u3.getUseremails().get(0).setUseremailid(5);

        userList.add(u3);

        User u4 = new User("puttat",
                "password",
                "puttat@school.lambda");
        u4.setUserid(4);
        u4.getRoles()
                .add(new UserRoles(u4,
                        r2));

        userList.add(u4);

        User u5 = new User("misskitty",
                "password",
                "misskitty@school.lambda");
        u5.setUserid(5);
        u5.getRoles()
                .add(new UserRoles(u5,
                        r2));

        userList.add(u5);

        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findUserById() {
        Mockito.when(userrepos.findById(1L))
                .thenReturn(Optional.of(userList.get(0)));

        assertEquals("test admin", userService.findUserById(1).getUsername());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findUserByIdNotFound() {
        Mockito.when(userrepos.findById(100L))
                .thenReturn(Optional.empty());

        assertEquals("test admin", userService.findUserById(1).getUsername());
    }

    @Test
    public void findByNameContaining() {
        Mockito.when(userrepos.findByUsernameContainingIgnoreCase("a"))
                .thenReturn(userList);

        assertEquals(5,
                userService.findByNameContaining("a")
                        .size());
    }

    @Test
    public void findAll() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void findByName() {
    }

    @Test
    public void save() {

        User u1 = new User("jordan",
                "test password",
                "jordan@lambdaschool.local");
        Role r1 = new Role("admin");
        r1.setRoleid(1);
        u1.getRoles()
                .add(new UserRoles(u1,
                        r1));

        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@email.local"));


        Mockito.when(userrepos.findById(1L))
                .thenReturn(Optional.of(u1));

        Mockito.when(userrepos.save(any(User.class))).thenReturn(u1);

        Mockito.when(roleService.findRoleById(1))
                .thenReturn(r1);

        User addUser = userrepos.save(u1);

        assertEquals(addUser.getPrimaryemail(), u1.getPrimaryemail());
    }

    @Test
    public void savePut() {

    }

    @Test
    public void saveUserNotFound() {

    }

    @Test
    public void saveRoleNotFound() {

    }

    @Test
    public void update() {
    }

    @Test
    public void deleteAll() {
    }
}