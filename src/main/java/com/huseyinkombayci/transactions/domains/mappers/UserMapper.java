package com.huseyinkombayci.transactions.domains.mappers;

import com.huseyinkombayci.transactions.domains.dtos.RegisterUserDTO;
import com.huseyinkombayci.transactions.domains.dtos.RegisteredUserDTO;
import com.huseyinkombayci.transactions.domains.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  RegisteredUserDTO userToUserDTO(User user);

  User userDTOToUser(RegisterUserDTO userDTO);
}
