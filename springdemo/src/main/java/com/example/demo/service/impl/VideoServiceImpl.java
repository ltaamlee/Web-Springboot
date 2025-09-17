package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Video;
import com.example.demo.repository.VideoRepository;
import com.example.demo.service.VideoService;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	private VideoRepository videoRepository;

	@Override
	public List<Video> findAll() {
		return videoRepository.findAll();
	}

	@Override
	public Video findById(Integer id) {
		return videoRepository.findById(id).orElse(null);
	}

	@Override
	public Video save(Video video) {
		return videoRepository.save(video);
	}

	@Override
	public void deleteById(Integer id) {
		videoRepository.deleteById(id);
	}

	@Override
	public List<Video> searchByTitle(String keyword) {
		return videoRepository.findByTitleContainingIgnoreCase(keyword);
	}

	@Override
	public List<Video> findByUserId(Integer userId) {
		return videoRepository.findByUser_Id(userId);
	}

}
