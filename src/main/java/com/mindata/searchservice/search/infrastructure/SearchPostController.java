package com.mindata.searchservice.search.infrastructure;

import com.mindata.searchservice.search.application.SearchCommand;
import com.mindata.searchservice.search.domain.InvalidSearchArgumentException;
import com.mindata.searchservice.shared.domain.DomainError;
import com.mindata.searchservice.shared.domain.InvalidDateFormatException;
import com.mindata.searchservice.shared.domain.UuidGenerator;
import com.mindata.searchservice.shared.domain.bus.command.CommandBus;
import com.mindata.searchservice.shared.domain.bus.query.QueryBus;
import com.mindata.searchservice.shared.infrastructure.spring.ApiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@RestController
public final class SearchPostController extends ApiController {
    private final UuidGenerator uuidGenerator;

    public SearchPostController(QueryBus queryBus, CommandBus commandBus, UuidGenerator uuidGenerator) {
        super(queryBus, commandBus);
        this.uuidGenerator = uuidGenerator;
    }

    @PostMapping("/search")
    public SearchResponse search(@RequestBody RequestSearch request) {
        final String searchId = uuidGenerator.generate();

        this.dispatch(new SearchCommand(
            searchId,
            request.hotelId(),
            request.checkIn(),
            request.checkOut(),
            request.ages()
        ));

        return new SearchResponse(searchId);
    }

    @Override
    public HashMap<Class<? extends DomainError>, HttpStatus> errorMapping() {
        return new HashMap<>(){{
            put(InvalidDateFormatException.class, HttpStatus.BAD_REQUEST);
            put(InvalidSearchArgumentException.class, HttpStatus.BAD_REQUEST);
        }};
    }
}
