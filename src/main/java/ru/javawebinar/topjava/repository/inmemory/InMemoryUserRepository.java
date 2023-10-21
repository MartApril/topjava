package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        Arrays.asList(
                new User(null, "Chris", "chis@mail.ru", "1223334444", Role.USER),
                new User(null, "Anna", "anna@mail.com", "1223334444", Role.USER),
                new User(null, "Richard", "richard@mail.com", "1223334444", Role.USER)
        ).forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id,repository.values().stream().filter(u->u.getId().equals(id))
                .findAny().orElse(null));
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        return new User(user.getId(), user.getName(), user.getEmail(), user.getPassword(), Role.USER);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.values().stream().filter(u->u.getId().equals(id))
                .findAny().orElse(null);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return new ArrayList<>(repository.values());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream().filter(u->u.getEmail().equals(email))
                .findAny().orElse(null);
    }
}
