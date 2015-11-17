package com.theironyard;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GamesSpringApplication.class)
@WebAppConfiguration
public class MoviesSpringApplicationTests {
    @Autowired
    GamesRepository games;

    @Autowired
    UserRepository users;

    @Autowired
    WebApplicationContext wap;

    MockMvc mockMvc;

    @org.junit.Before
    public void before(){
        games.deleteAll();
        users.deleteAll();
        mockMvc = MockMvcBuilders.webAppContextSetup(wap).build();
    }

    @Test
    public void testLogin()throws Exception{
        mockMvc.perform(
        MockMvcRequestBuilders.post("/login")
        .param("name", "test")
        .param("gamerTag", "test")
        .param("password", "test")


        );
        assertTrue(users.count()==1);
    }

    @Test
    public void testAdd() throws Exception{
        mockMvc.perform(
        MockMvcRequestBuilders.post("/add-game")
        .param("system","test")
        .param("title","test")
        .sessionAttr("gamerTag","testUser")
        );
        Assert.assertTrue(games.count() == 1);
    }
    @Test
    public void testEdit() throws Exception{
        mockMvc.perform(
        MockMvcRequestBuilders.post("/editGame")

        );


        Assert.assertTrue(games.count() == 1);
    }
}
