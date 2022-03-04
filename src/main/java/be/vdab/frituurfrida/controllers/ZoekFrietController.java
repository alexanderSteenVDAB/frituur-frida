package be.vdab.frituurfrida.controllers;


import be.vdab.frituurfrida.sessions.ZoekFriet;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("zoekfriet")
class ZoekFrietController {
    private final ZoekFriet zoekFriet;

    ZoekFrietController(ZoekFriet zoekFriet) {
        this.zoekFriet = zoekFriet;
    }

    @GetMapping
    public ModelAndView zoekFriet() {
        return new ModelAndView("zoekfriet")
                .addObject("opendeuren", zoekFriet.getOpenDeuren())
                .addObject("deurMetFriet", zoekFriet.getDeurMetFrietId());
    }

    @PostMapping("nieuwspel")
    public String nieuwspel() {
        zoekFriet.nieuwSpel();
        return "redirect:/zoekfriet";
    }

    @PostMapping
    public String openDeur(int index) {
        zoekFriet.openDeur(index);
        return "redirect:/zoekfriet";
    }
}
