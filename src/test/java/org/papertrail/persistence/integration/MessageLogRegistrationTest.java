package org.papertrail.persistence.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.papertrail.persistence.dto.MessageLogRegistrationDTO;
import org.papertrail.persistence.repository.MessageLogRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("dev")
@Slf4j
class MessageLogRegistrationTest {
    @Autowired
    private WebTestClient client;

    @Autowired
    private MessageLogRegistrationRepository repository;

    @Autowired
    private CacheManager cacheManager;

    MessageLogRegistrationDTO body;
    private static final Long GUILD_ID = 124587145126L;
    private static final Long CHANNEL_ID = 541812154121L;

    private static final String BASE_URL = "/api/v1/log/message";

    private static RedisServer redisServer;

    @BeforeAll
    static void startRedis() throws IOException {
        redisServer = RedisServer.newRedisServer().build();
        redisServer.start();
    }

    @AfterAll
    static void stopRedis() throws IOException {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    @BeforeEach
    void loadEntities(){
        body = new MessageLogRegistrationDTO();
        body.setGuildId(GUILD_ID);
        body.setChannelId(CHANNEL_ID);
    }

    @BeforeEach
    void clearState() {
        repository.deleteAll();
        Objects.requireNonNull(cacheManager.getCache("messageLog")).clear();
    }

    @Test
    void registerGuild_success() {

        client.post()
                .uri(BASE_URL)
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(MessageLogRegistrationDTO.class)
                .value(dto -> assertThat(dto.getGuildId()).isEqualTo(GUILD_ID))
                .value(dto -> assertThat(dto.getChannelId()).isEqualTo(CHANNEL_ID));
    }

    @Test
    void registerGuild_exists_throwsException() {

        client.post()
                .uri(BASE_URL)
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(MessageLogRegistrationDTO.class)
                .value(dto -> assertThat(dto.getGuildId()).isEqualTo(GUILD_ID))
                .value(dto -> assertThat(dto.getChannelId()).isEqualTo(CHANNEL_ID));

        client.post()
                .uri(BASE_URL)
                .bodyValue(body)
                .exchange()
                .expectStatus().isEqualTo(HttpStatusCode.valueOf(409));
    }

    @Test
    void findByGuild_success() {

        client.post()
                .uri(BASE_URL)
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(MessageLogRegistrationDTO.class)
                .value(dto -> assertThat(dto.getGuildId()).isEqualTo(GUILD_ID))
                .value(dto -> assertThat(dto.getChannelId()).isEqualTo(CHANNEL_ID));

        client.get()
                .uri(BASE_URL+"/"+GUILD_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MessageLogRegistrationDTO.class)
                .value(dto -> assertThat(dto.getGuildId()).isEqualTo(GUILD_ID))
                .value(dto -> assertThat(dto.getChannelId()).isEqualTo(CHANNEL_ID));

    }

    @Test
    void findByGuild_exists_throwsException() {

        client.get()
                .uri(BASE_URL+"/"+GUILD_ID)
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    void updateGuild_success() {

        MessageLogRegistrationDTO updatedDto = new MessageLogRegistrationDTO();
        updatedDto.setGuildId(body.getGuildId());
        updatedDto.setChannelId(456L);

        client.post()
                .uri(BASE_URL)
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(MessageLogRegistrationDTO.class)
                .value(dto -> assertThat(dto.getGuildId()).isEqualTo(GUILD_ID))
                .value(dto -> assertThat(dto.getChannelId()).isEqualTo(CHANNEL_ID));

        client.put()
                .uri(BASE_URL)
                .bodyValue(updatedDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(MessageLogRegistrationDTO.class)
                .value(dto -> assertThat(dto.getGuildId()).isEqualTo(updatedDto.getGuildId()))
                .value(dto -> assertThat(dto.getChannelId()).isEqualTo(updatedDto.getChannelId()));

        client.get()
                .uri(BASE_URL+"/"+updatedDto.getGuildId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(MessageLogRegistrationDTO.class)
                .value(dto -> assertThat(dto.getGuildId()).isEqualTo(updatedDto.getGuildId()))
                .value(dto -> assertThat(dto.getChannelId()).isEqualTo(updatedDto.getChannelId()));
    }

    @Test
    void updateGuild_notExists_throwsException() {

        client.put()
                .uri(BASE_URL)
                .bodyValue(body)
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    void deleteGuild_success() {

        client.post()
                .uri(BASE_URL)
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(MessageLogRegistrationDTO.class)
                .value(dto -> assertThat(dto.getGuildId()).isEqualTo(GUILD_ID))
                .value(dto -> assertThat(dto.getChannelId()).isEqualTo(CHANNEL_ID));

        client.delete()
                .uri(BASE_URL+"/"+GUILD_ID)
                .exchange()
                .expectStatus().isNoContent();

        client.get()
                .uri(BASE_URL+"/"+GUILD_ID)
                .exchange()
                .expectStatus().isNotFound();

    }

    @Test
    void deleteGuild_notExists_throwsException() {

        client.delete()
                .uri(BASE_URL+"/"+GUILD_ID)
                .exchange()
                .expectStatus().isNotFound();

    }
}
