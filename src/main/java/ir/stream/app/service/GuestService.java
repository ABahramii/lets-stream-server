package ir.stream.app.service;

import ir.stream.app.entity.Guest;
import ir.stream.app.repository.GuestRepository;
import ir.stream.core.service.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class GuestService extends AbstractService<Guest, Long, GuestRepository> {
    private final GuestRepository guestRepository;

    public GuestService(GuestRepository abstractRepository, GuestRepository guestRepository) {
        super(abstractRepository);
        this.guestRepository = guestRepository;
    }

}
