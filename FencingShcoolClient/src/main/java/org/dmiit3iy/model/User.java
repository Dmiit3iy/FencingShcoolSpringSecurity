package org.dmiit3iy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor

public class User {

    private long id;

    @NonNull
    private String userName;

    @NonNull
    private String password;


    @NonNull
    private String fio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate regDate = LocalDate.now();

    private List<Role> roleList=new ArrayList<>();
}
