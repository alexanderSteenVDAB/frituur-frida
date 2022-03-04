package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.domain.Snack;
import be.vdab.frituurfrida.forms.BeginlettersForm;
import be.vdab.frituurfrida.services.SnackService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/snacks")
public class SnackController {
    private final SnackService snackService;

    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();


    public SnackController(SnackService snackService) {
        this.snackService = snackService;
    }

    @GetMapping("alfabet")
    public ModelAndView findAlfabet() {
        return new ModelAndView("snacks-alfabet", "alfabet", alfabet);
    }

    @GetMapping("alfabet/{letter}")
    public ModelAndView findByFirstLetter(@PathVariable String letter) {
        return new ModelAndView("snacks-alfabet", "alfabet", alfabet)
                .addObject("snacks", snackService.findByBeginNaam(letter));
    }

    @GetMapping("aantalverkocht")
    public ModelAndView findSnacksMetAantalVerkocht() {
        return new ModelAndView("snacks-aantalverkocht",
                "snacksMetAantalVerkocht", snackService.findSnacksMetAantalVerkocht());
    }

    @GetMapping("beginletters/form")
    public ModelAndView beginlettersForm() {
        return new ModelAndView("beginletters").addObject(new BeginlettersForm(null));
    }

    @GetMapping("beginletters")
    public ModelAndView beginletters(@Valid BeginlettersForm form, Errors errors) {
        var modelAndView = new ModelAndView("beginletters");
        if (errors.hasErrors()) {
            return modelAndView;
        }

        return modelAndView.addObject("snacks", snackService.findByBeginNaam(form.beginletters()));
    }

    @GetMapping("update/{id}")
    public ModelAndView updateForm(@PathVariable long id) {
        var modelAndView = new ModelAndView("update");
        if (snackService.read(id).isPresent()) {
            modelAndView.addObject(snackService.read(id).get());
        }

        return modelAndView;
    }


    @PostMapping("update")
    public String toevoegen(@Valid Snack snack, Errors errors) {
        if (errors.hasErrors()) {
            return "update";
        }
        snackService.update(snack);
        return "redirect:/";
    }
}
