package com.example.spring_intro_exercise.services;

import com.example.spring_intro_exercise.models.Author;
import com.example.spring_intro_exercise.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService{
    private AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getRandomAuthor() {
        long countOfAuthors = this.authorRepository.count();
        return this.authorRepository.findById(new Random().nextLong(1, countOfAuthors)).get();
    }

}
