package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Integer>{
	
    List<Video> findByTitleContainingIgnoreCase(String keyword);

    List<Video> findByUser_Id(Integer userId);

}
