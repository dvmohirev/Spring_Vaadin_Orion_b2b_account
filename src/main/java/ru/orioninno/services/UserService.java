package ru.orioninno.services;

import ru.orioninno.controllers.dto.UserDto;
import ru.orioninno.controllers.dto.RoleDto;
import ru.orioninno.entities.Role;
import ru.orioninno.entities.User;
import ru.orioninno.exceptions.ManagerIsEarlierThanNeedException;
import ru.orioninno.exceptions.UnknownUserTypeException;
import ru.orioninno.exceptions.UserNotFoundException;
import ru.orioninno.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository,
                       RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public User saveUser(UserDto userDto) {
        if (userDto.getRoleDto().equals(RoleDto.MANAGER)) {
            saveManager(userDto);
        } else if (userDto.getRoleDto().equals(RoleDto.CUSTOMER)) {
            saveTypicallyUser(userDto);
        }

        throw new UnknownUserTypeException();
    }

    private User saveTypicallyUser(UserDto userDto) {
        User user = createUserFromDto(userDto);

        Role role = roleService.getByName("ROLE_USER");
        user.setRoles(List.of(role));

        return userRepository.save(user);
    }

    private User saveManager(UserDto userDto) {
        if (userDto.getAge() > 18 ) {
            User user = createUserFromDto(userDto);

            Role role = roleService.getByName("ROLE_MANAGER");
            user.setRoles(List.of(role));

            return userRepository.save(user);
        }

        throw new ManagerIsEarlierThanNeedException("Пользователь младше восемнадцати лет");
    }

    private User createUserFromDto(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setPhone(userDto.getPhone());
        user.setAge(userDto.getAge());
        user.setMoney(userDto.getMoney());

        return user;
    }

    public List<User> getAllUsersWithType(RoleDto roleDto) {
        Role role;

        if (roleDto == RoleDto.CUSTOMER) {
            role = roleService.getByName("ROLE_CUSTOMER");
            return userRepository.findAllByRoles(role);
        } else if (roleDto == RoleDto.MANAGER) {
            role = roleService.getByName("ROLE_MANAGER");
            return userRepository.findAllByRoles(role);
        }

        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("Пользователь с идентификатором %s не найден", id)));
    }

    public User findByUsername(String userName) {
        return userRepository.findByPhone(userName)
                .orElseThrow(() -> new UserNotFoundException(String.format("Пользователь с телефоном %s не найден", userName)));
    }

    public User saveUser(String phone, String password, String firstName, String lastName, String email, String age, String money) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .money(Integer.valueOf(money))
                .age(Integer.valueOf(age))
                .email(email)
                .lastName(lastName)
                .firstName(firstName)
                .password(encoder.encode(password))
                .phone(phone)
                .build();

        return userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
