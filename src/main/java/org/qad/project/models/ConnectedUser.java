package org.qad.project.models;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConnectedUser {

	Optional<User> user;
	Number activeUsers;
}
