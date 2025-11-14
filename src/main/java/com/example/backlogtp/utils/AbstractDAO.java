package com.example.backlogtp.utils;

public class AbstractDAO implements DAO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
