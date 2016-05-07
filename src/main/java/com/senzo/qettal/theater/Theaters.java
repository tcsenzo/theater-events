package com.senzo.qettal.theater;

import java.util.List;
import java.util.Optional;

public interface Theaters {
	Theater save(Theater theater);
	Optional<Theater> findById(Long id);
	List<Theater> all();
}
