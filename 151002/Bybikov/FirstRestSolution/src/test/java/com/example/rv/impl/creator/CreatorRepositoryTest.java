package com.example.rv.impl.creator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CreatorRepositoryTest {

    CreatorRepository underTestCreatorRepository = new CreatorRepository();


    @Test
    void checkingOptionalToEditor(){
        Creator respTo = new Creator(-1L, "miki", "123", "me", "me");

        Creator ed = underTestCreatorRepository.save(respTo).orElse(null);

        Assertions.assertNotNull(ed);
        Assertions.assertEquals(ed.getId(), respTo.getId());
    }

}