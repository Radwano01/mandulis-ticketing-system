package org.mandulis.mts.dto.response;

import lombok.*;
import org.mandulis.mts.dto.UserGroupDetails;
import org.mandulis.mts.entity.Group;
import org.mandulis.mts.enums.Role;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private List<UserGroupDetails> groups;
    private Role role;
    private String email;

    public String getGroupInfo() {
        return (groups == null || groups.isEmpty()) ? "No group assigned" : groups.toString();
    }
}