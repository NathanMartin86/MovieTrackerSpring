package com.theironyard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
        String username = (String) session.getAttribute("gamerTag");

        if (username == null){
            return"login";
        }
        else {
            model.addAttribute("games", games.findAll());
            model.addAttribute("user", users.findOneByGamerTag(username));
        }
        return "home";
    }

    @RequestMapping("/login")
    public String createUser(HttpSession session, String name, String gamerTag, String password) throws Exception {
        session.setAttribute("gamerTag", gamerTag);
        session.setAttribute("name", name);

        User user = users.findOneByGamerTag(gamerTag);

        if (user == null){
            user = new User();
            user.name = name;
            user.gamerTag = gamerTag;
            user.password = PasswordHash.createHash(password);
            users.save(user);
        }
        else if (!PasswordHash.validatePassword(password,user.password)){
            throw new Exception("Wrong Password");
        }
        return "redirect:/";
    }

    @RequestMapping("/add-game")
    public String addGame (String title, String system, HttpSession session) throws Exception {
        String gamerTag = (String) session.getAttribute("gamerTag");
        if (gamerTag == null) {
            throw new Exception("Not Logged in");
        }
        User user = users.findOneByGamerTag(gamerTag);
        Game game = new Game();
        game.system = system;
        game.title = title;
        game.user = user;
        games.save(game);

        return "redirect:/";
    }

    @RequestMapping("edit")
    public String edit (){
        return"redirect:edit";
    }
    @RequestMapping("editGame")
    public String editGame (int id, String title){
        Game game = games.findOne(id);
        if(game != null){
            game.title = title;
            games.save(game);
        }
        return "redirect:/";
    }

    @RequestMapping ("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping ("deleteGame")
    public String delete (Integer id){
        games.delete(id);
        return "redirect:/";
    }
}

//play with HTML
// how to link users <--> games
// edit / delete functionality