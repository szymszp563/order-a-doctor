package pl.polsl.orderadoctor.mappers;

import org.mapstruct.Mapper;
import pl.polsl.orderadoctor.dto.UserDto;
import pl.polsl.orderadoctor.model.User;

@Mapper(componentModel = "spring", uses = {VisitMapper.class, GradeMapper.class})
public interface UserMapper {

    User userDtoToUser(UserDto dto);

    UserDto userToUserDto(User entity);
}
