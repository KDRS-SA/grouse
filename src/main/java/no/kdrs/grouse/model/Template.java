package no.kdrs.grouse.model;

import javax.persistence.*;

@Entity
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;


}
