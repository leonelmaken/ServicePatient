package com.patientmvc.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.patientmvc.demo.entity.Patient;
import com.patientmvc.demo.repository.PatientRepository;

import jakarta.validation.Valid;

@Controller
public class PatientController {

	@Autowired
	PatientRepository patientRepository;
	
	@GetMapping(path = "/index")
	public String patients(Model model,
			@RequestParam(name = "page",defaultValue = "0")  int page,
			@RequestParam(name = "size",defaultValue = "4") 	int size,
			@RequestParam(name = "keyword",defaultValue = "") 	String kw) {//si on ne trouve pas la valeur du parametre c'est 5
		Page<Patient> pagePatients = patientRepository.findByNomContains(kw, PageRequest.of(page, size));
		model.addAttribute("listPatients",pagePatients.getContent());//la liste des patients de la page 
		model.addAttribute("pages",new int[pagePatients.getTotalPages()]);//on stacke dans un attributes le nombre de pages
		model.addAttribute("currentPages", page);
		model.addAttribute("keyword",kw);
		return "patients";
	}
	
	@GetMapping(path="/delete")
	public String delete(Long id,String keyword,int page) {
		patientRepository.deleteById(id);
		return "redirect:/index?page="+page+"&keyword="+keyword;
		}
	@GetMapping(path="/FormPatient")
	public String formPatient(Model model) {
		model.addAttribute("patient",new Patient());
		return "FormPatient";
	}
	@PostMapping(path="/savePatient")
	public String savePatient(Model model, @Valid Patient patient,BindingResult bindingResult,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "") String keyword) {
		if(bindingResult.hasErrors()) return "FormPatient";
		patientRepository.save(patient);
		return "redirect:/index?page="+page+"&keyword="+keyword;
	}
	@GetMapping(path="/editPatient")
	public String editPatient(Model model,Long id,String keyword, int page) {
		Patient patient = patientRepository.findById(id).orElse(null);
		if(patient == null) throw new RuntimeException("Patient introuvable");
		model.addAttribute("patient",patient);
		model.addAttribute("page",page);
		model.addAttribute("keyword",keyword);
		return "editPatient";
	}
}
