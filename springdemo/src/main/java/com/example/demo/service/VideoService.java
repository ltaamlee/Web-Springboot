package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Video;

public interface VideoService {
	
    List<Video> findAll();

    Video findById(Integer id);

    Video save(Video video);

    void deleteById(Integer id);

    List<Video> searchByTitle(String keyword);

    List<Video> findByUserId(Integer userId);

}
