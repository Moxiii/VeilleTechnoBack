package com.moxi.veilletechnoback.Ideas;

import com.moxi.veilletechnoback.Ressources.Ressources;
import com.moxi.veilletechnoback.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Ideas {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String title;
private String description;
@ManyToOne
private User user;
@Lob
private byte[] image;
@ElementCollection
@CollectionTable(name = "ideas_links", joinColumns = @JoinColumn(name = "ideas_id"))
@Column(name = "link")
private List<String>  links = new ArrayList<>();
@ElementCollection
@CollectionTable(name="ideas_tags", joinColumns = @JoinColumn(name="ideas_id"))
@Column(name="tag")
private List<String> tags = new ArrayList<>();
@OneToMany
@JoinTable(
		name = "ideas_resources",
		joinColumns = @JoinColumn(name = "idea_id"),
		inverseJoinColumns = @JoinColumn(name = "resource_id")
)
private List<Ressources> ressources = new ArrayList<>();
}
