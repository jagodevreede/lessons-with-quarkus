package org.acme;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Greeting {
    private String text;
    @Id
    private UUID id;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
