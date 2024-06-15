package org.store.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private int id;
    private String user_name;
    private String email;
    private String password;
    private String user_type;
}
