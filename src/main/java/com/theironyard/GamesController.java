package com.theironyard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String home(Model model, HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
        HttpSession session = request.getSession();
        PageRequest pr = new PageRequest(page, 5);
        Page p;
        String username = (String) session.getAttribute("gamerTag");

        p = games.findAll(pr);

        if (username == null){
            return"login";
        }
        else {
            model.addAttribute("user", users.findOneByGamerTag(username));
        }
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("games", p);
        model.addAttribute("showNext", p.hasNext());

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

    @RequestMapping("/edit")
    public String edit (Model model, int id, HttpSession session) throws Exception {
        String username = (String) session.getAttribute("gamerTag");
        if (username == null) {
            throw new Exception("Not Logged in");
        }
        model.addAttribute("id",id);
        model.addAttribute("user", users.findOneByGamerTag(username));
        return "edit";
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
    public String delete (int id){
        games.delete(id);
        return "redirect:/";
    }
}