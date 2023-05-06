package it.prova.gestionesatelliti.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.service.SatelliteService;

@Controller
@RequestMapping(value = "/satellite")
public class SatelliteController {
	
	@Autowired
	private SatelliteService satelliteService;
	
	@GetMapping
	public ModelAndView listAll() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = satelliteService.listAllElements();
		mv.addObject("satellite_list_attribute", results);
		mv.setViewName("satellite/list");
		return mv;
	}

	@GetMapping("/search")
	public String search() {
		return "satellite/search";
	}
	
	@PostMapping("/list")
	public String listByExample(Satellite example, ModelMap model) {
		List<Satellite> results = satelliteService.findByExample(example);
		model.addAttribute("impiegato_list_attribute", results);
		return "satellite/list";
	}
	
	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("insert_satellite_attr", new Satellite());
		return "Satellite/insert";
	}
	
//	@PostMapping("/save")
//	public String save(@Valid @ModelAttribute("insert_impiegato_attr") Impiegato impiegato, BindingResult result,
//			RedirectAttributes redirectAttrs) {
//
//		if (result.hasErrors())
//			return "impiegato/insert";
//
//		impiegatoService.inserisciNuovo(impiegato);
//
//		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
//		return "redirect:/impiegato";
//	}
	
	
	
}
