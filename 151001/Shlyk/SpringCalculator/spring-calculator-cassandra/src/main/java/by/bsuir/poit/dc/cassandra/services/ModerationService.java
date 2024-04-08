package by.bsuir.poit.dc.cassandra.services;

import jakarta.validation.constraints.NotNull;

/**
 * @author Paval Shlyk
 * @since 08/04/2024
 */
public interface ModerationService {
    ModerationResult verify(@NotNull String content);
}
