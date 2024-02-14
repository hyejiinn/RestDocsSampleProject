package com.sample.restDocs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sample.restDocs.vo.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>
{
}
