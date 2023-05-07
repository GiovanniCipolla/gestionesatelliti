package it.prova.gestionesatelliti.web.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.model.StatoSatellite;
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
		model.addAttribute("satellite_list_attribute", results);
		return "satellite/list";
	}
	
	@GetMapping("/lanciatiDaPiu2Anni")
	public String lanciatiDaPiu2Anni(ModelMap model) {
		List<Satellite> results = satelliteService.lanciatiDa2AnniOPiuAttivi();
		model.addAttribute("satellite_list_attribute", results);
		return "satellite/list";
	}
	
	@GetMapping("/disattivatiNonRientrati")
	public String disattivatiNonRientrati(ModelMap model) {
		List<Satellite> results = satelliteService.disattivatiInOrbita();
		model.addAttribute("satellite_list_attribute", results);
		return "satellite/list";
	}
	
	@GetMapping("/Fissi10")
	public String Fissi10(ModelMap model) {
		List<Satellite> results = satelliteService.lanciatiDa10AnniFissi();
		model.addAttribute("satellite_list_attribute", results);
		return "satellite/list";
	}

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("insert_satellite_attr", new Satellite());
		return "satellite/insert";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("insert_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		if (result.hasErrors())
			return "satellite/insert";

		if (satellite.getDataRientro() != null && satellite.getDataLancio() == null) {
			result.rejectValue("dataLancio", "error.satellite", "Inserisci anche la data di lancio");
			return "satellite/insert";
		}

		if (satellite.getDataLancio() != null && satellite.getDataRientro() != null
				&& satellite.getDataRientro().isBefore(satellite.getDataLancio())) {
			result.rejectValue("dataLancio", "error.satellite", "Inserisci la data di lancio prima del rientro");
			return "satellite/insert";
		}
		if(satellite.getStato().equals(satellite.getStato().DISABILITATO ) && satellite.getDataLancio().isAfter(LocalDate.now() )  ) {
			result.rejectValue("dataLancio", "error.satellite",
					"questo stato non può avere una data di lancio futura");
			return "satellite/insert";
		}

		if (satellite.getStato() == null) {
			if (satellite.getDataLancio() != null) {
				result.rejectValue("dataLancio", "error.satellite",
						"se non è stato inserito lo stato non si può inserire una data");
				return "satellite/insert";
			}
			if (satellite.getDataRientro() != null) {
				result.rejectValue("dataRientro", "error.satellite",
						"se non è stato inserito lo stato non si può inserire una data");
				return "satellite/insert";
			}
		} else if (satellite.getStato().equals(satellite.getStato().FISSO)
				|| satellite.getStato().equals(satellite.getStato().IN_MOVIMENTO)) {

			if (satellite.getDataLancio() == null) {
				result.rejectValue("dataLancio", "error.satellite",
						"con questo stato , la data di lancio è obbligatoria");
				return "satellite/insert";
			}

			if (satellite.getDataRientro() != null && satellite.getDataRientro().isBefore(LocalDate.now())) {
				result.rejectValue("dataRientro", "error.satellite",
						"con questo stato , la data di rientro non può essere già avvenuta ");
				return "satellite/insert";
			}
		} else {
			if (satellite.getDataLancio() == null) {
				result.rejectValue("dataLancio", "error.satellite",
						"con questo stato , la data di lancio è obbligatoria");
				return "satellite/insert";
			}
		}

		satelliteService.inserisciNuovo(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@GetMapping("/show/{idSatellite}")
	public String show(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("show_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/show";
	}

	@GetMapping("/delete/{idSatellite}")
	public String prepareDelete(@PathVariable(required = true) Long idSatellite, Model model,
			RedirectAttributes redirectAttributes) {

		Satellite verificaSatellite = satelliteService.caricaSingoloElemento(idSatellite);

		if (verificaSatellite.getStato() != null) {
			redirectAttributes.addFlashAttribute("errorMessage",
					"Impossibile eliminare il satellite perché è in uno stato non eliminabile.");
			return "redirect:/satellite";
		}

		model.addAttribute("delete_satellite_attr", verificaSatellite);
		return "satellite/delete";
	}

	@PostMapping("/delete")
	public String delete(Long id, RedirectAttributes redirectAttrs) {
		satelliteService.rimuovi(id);
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@GetMapping("/edit/{idSatellite}")
	public String edit(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("edit_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/edit";
	}

	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("edit_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		if (result.hasErrors())
			return "satellite/edit";


		if (satellite.getDataRientro() != null && satellite.getDataLancio() == null) {
			result.rejectValue("dataLancio", "error.satellite", "Inserisci anche la data di lancio");
			return "satellite/edit";
		}

		if (satellite.getDataLancio() != null && satellite.getDataRientro() != null
				&& satellite.getDataRientro().isBefore(satellite.getDataLancio())) {
			result.rejectValue("dataLancio", "error.satellite", "Inserisci la data di lancio prima del rientro");
			return "satellite/edit";
		}
		
		if(satellite.getStato().equals(satellite.getStato().DISABILITATO ) && satellite.getDataLancio().isAfter(LocalDate.now() )  ) {
			result.rejectValue("dataLancio", "error.satellite",
					"questo stato non può avere una data di lancio futura");
			return "satellite/edit";
		}

		if (satellite.getStato() == null) {
			if (satellite.getDataLancio() != null) {
				result.rejectValue("dataLancio", "error.satellite",
						"se non è stato inserito lo stato non si può inserire una data");
				return "satellite/edit";
			}
			if (satellite.getDataRientro() != null) {
				result.rejectValue("dataRientro", "error.satellite",
						"se non è stato inserito lo stato non si può inserire una data");
				return "satellite/edit";
			}
		} else if (satellite.getStato().equals(satellite.getStato().FISSO)
				|| satellite.getStato().equals(satellite.getStato().IN_MOVIMENTO)) {

			if (satellite.getDataLancio() == null) {
				result.rejectValue("dataLancio", "error.satellite",
						"con questo stato , la data di lancio è obbligatoria");
				return "satellite/edit";
			}

			if (satellite.getDataRientro() != null && satellite.getDataRientro().isBefore(LocalDate.now())) {
				result.rejectValue("dataRientro", "error.satellite",
						"con questo stato , la data di rientro non può essere già avvenuta ");
				return "satellite/edit";
			}
		} else {
			if (satellite.getDataLancio() == null) {
				result.rejectValue("dataLancio", "error.satellite",
						"con questo stato , la data di lancio è obbligatoria");
				return "satellite/edit";
			}
		}
		
		satelliteService.aggiorna(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}
	
	@PostMapping("/lancia")
	public String lancia(@ModelAttribute("lancia_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {
			satelliteService.lancio(satellite.getId());
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
			return "redirect:/satellite";
		}
	
	@PostMapping("/rientro")
	public String rientro(@ModelAttribute("rientro_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {
			satelliteService.rientro(satellite.getId());
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
			return "redirect:/satellite";
		}

	
}
