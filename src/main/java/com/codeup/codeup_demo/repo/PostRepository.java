package com.codeup.codeup_demo.repo;

import com.codeup.codeup_demo.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	Post findByTitle(String title);

	List<Post> findAllByTitleIsLike(@Param("term") String term);

	List<Post> findAllByBodyIsLike(@Param("term") String term);

}
