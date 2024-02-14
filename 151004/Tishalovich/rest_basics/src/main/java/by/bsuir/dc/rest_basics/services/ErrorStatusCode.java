package by.bsuir.dc.rest_basics.services;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorStatusCode {

    NO_ENTITY_WITH_SUCH_ID(404000);

    private final int value;

}
