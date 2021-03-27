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

	@Query("from Post post where post.body like %:term%")
	List<Post> searchByBodyLike(@Param("term") String term);

}
