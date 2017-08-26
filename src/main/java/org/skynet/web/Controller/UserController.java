package org.skynet.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

    @RequestMapping("/")
    public String index() {
        return "Index";
    }

    @RequestMapping("LogIn")
    public String logIn() {
        return "LogIn";
    }

    @RequestMapping("Register")
    public String register() {
        return "Register";
    }

//    @RequestMapping(value = "/{reader}", method = RequestMethod.GET)
//    public String readersBooks(@PathVariable("reader") String reader, Model model) {
//        List<Book> readingList = readingListRepository.findByReader(reader);
//        if (readingList != null) {
//            model.addAttribute("books", readingList);
//        }
//        return "userList";
//    }
//
//    @RequestMapping(value = "/{reader}", method = RequestMethod.POST)
//    public String addToReadingList(@PathVariable("reader") String reader, Book book) {
//        book.setReader(reader);
//        readingListRepository.save(book);
//        return "redirect:/{reader}";
//    }
}
