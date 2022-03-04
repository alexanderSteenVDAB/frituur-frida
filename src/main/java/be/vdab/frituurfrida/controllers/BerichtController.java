package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Bericht;
import be.vdab.frituurfrida.services.BerichtService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("gastenboek")
public class BerichtController {
    private final BerichtService berichtService;

    public BerichtController(BerichtService berichtService) {
        this.berichtService = berichtService;
    }

    @GetMapping
    public ModelAndView gastenboek() {
        return new ModelAndView("gastenboek")
                .addObject("berichten", berichtService.findAll());
    }

    @GetMapping("form")
    public ModelAndView toevoegenForm() {
        return new ModelAndView("gastenboek")
                .addObject("berichten", berichtService.findAll())
                .addObject(new Bericht(0, LocalDate.now(), "", ""));
    }

    @PostMapping
    public ModelAndView toevoegen(@Valid Bericht bericht, Errors errors) {
        if (errors.hasErrors()) {
            return new ModelAndView("gastenboek")
                    .addObject("berichten", berichtService.findAll())
                    .addObject(bericht);
        }

        berichtService.create(bericht);
        return new ModelAndView("redirect:/gastenboek");
    }

    @PostMapping("verwijderen")
    public String delete(Long[] id) {
        berichtService.delete(id);
        return "redirect:/gastenboek";
    }
}
