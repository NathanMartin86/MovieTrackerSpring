package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.security.util.Password;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by macbookair on 11/12/15.
 */
@Controller
public class GamesController {

    @Autowired
    GamesRepository games;
    @Autowired
    UserRepository users;


    @RequestMapping("/")
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null){
            return"login";
        }
        else {
            model.addAttribute("games", games.findAll());
            model.addAttribute("users", users.findAll());
        }
        return "home";
    }

    @RequestMapping("/login")
    public String createUser(HttpSession session, String name, String username, String password) throws Exception {
        session.setAttribute("username", username);
        session.setAttribute("name", name);

        User user = users.findOneByName(username);

        if (user == null){
            user = new User();
            user.name = name;
            user.username = username;
        }

        return "redirect:/";
        // this isn't complete. You should make a thing for passwords.
    }

    @RequestMapping("/add-game")
    public String addGame (String title, String genre,String system, HttpSession session) throws Exception {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            throw new Exception("Not Logged in");
        }

        User user = users.findOneByName(username);
        Game game = new Game();
        game.system = system;
        game.title = title;
        game.genre = genre;
        game.user = user;
        games.save(game);

        return "redirect:/";
    }
    @RequestMapping ("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}

