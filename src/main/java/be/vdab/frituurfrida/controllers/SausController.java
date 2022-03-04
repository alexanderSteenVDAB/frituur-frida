package be.vdab.frituurfrida.controllers;

import be.vdab.frituurfrida.services.SausService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("sauzen")
class SausController {
    private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final SausService sausService;

    SausController(SausService sausService) {
        this.sausService = sausService;
    }

    @GetMapping
    public ModelAndView findAll() {
        return new ModelAndView("sauzen", "alleSauzen", sausService.findAll());
    }

    @GetMapping("{nummer}")
    public ModelAndView findByNummer(@PathVariable long nummer) {
        var modelAndView = new ModelAndView("saus");
        sausService.findById(nummer).ifPresent(
                gevondenSaus -> modelAndView.addObject("saus", gevondenSaus));
        return modelAndView;
    }

    @GetMapping("alfabet")
    public ModelAndView findAlfabet() {
        return new ModelAndView("alfabet", "alfabet", alfabet);
    }

    @GetMapping("alfabet/{letter}")
    public ModelAndView findByFirstLetter(@PathVariable Character letter) {
        return new ModelAndView("alfabet", "alfabet", alfabet)
                .addObject("sauzen", sausService.findByBeginNaam(letter));
    }
}
