package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Video;
import com.example.demo.service.VideoService;

public class VideoController {
	@Autowired
	private VideoService videoService;

	// List + search
	@GetMapping
	public String listVideos(Model model, @RequestParam(required = false) String search) {
		if (search != null && !search.isEmpty()) {
			model.addAttribute("videos", videoService.searchByTitle(search));
		} else {
			model.addAttribute("videos", videoService.findAll());
		}
		return "video/list";
	}

	// Show form create
	@GetMapping("/new")
	public String showCreateForm(Model model) {
		model.addAttribute("video", new Video());
		return "video/form";
	}

	// Save
	@PostMapping("/save")
	public String saveVideo(@ModelAttribute Video video) {
		videoService.save(video);
		return "redirect:/videos";
	}

	// Show form edit
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Integer id, Model model) {
		Video video = videoService.findById(id);
		model.addAttribute("video", video);
		return "video/form";
	}

	// Delete
	@GetMapping("/delete/{id}")
	public String deleteVideo(@PathVariable Integer id) {
		videoService.deleteById(id);
		return "redirect:/videos";
	}
}
