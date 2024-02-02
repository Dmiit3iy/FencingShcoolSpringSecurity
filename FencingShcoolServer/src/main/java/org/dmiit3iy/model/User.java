package org.dmiit3iy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(unique = true)
    private String userName;
    @NonNull
    private String password;
    @NonNull
    private String fio;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roleList = new ArrayList<>();

    public void addRole(Role role) {
        this.roleList.add(role);
    }

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate regDate = LocalDate.now();
    @JsonIgnore
    @NonNull
    private boolean active = true;


    public User(String userName, String password, boolean active) {
        this.userName = userName;
        this.password = password;
        this.active = active;
    }

//    public User(@NonNull String userName, @NonNull String password, @NonNull String fio, Role role) {
//        this.userName = userName;
//        this.password = password;
//        this.fio = fio;
//        this.roleList.add(role);
//    }
    public User(@NonNull String userName, @NonNull String password, @NonNull String fio, String role) {
        this.userName = userName;
        this.password = password;
        this.fio = fio;
        addRole(new Role(role));
    }
    public void setRole(String role){
        addRole(new Role(role));
    }
}
