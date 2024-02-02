package org.dmiit3iy.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Role {

    private long id;
    @NonNull
    private String role;
    private List<User> users = new ArrayList<>();
}
