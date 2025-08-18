package org.papertrail.persistence.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.papertrail.persistence.dto.AuditLogRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AuditLogTest {

    @Autowired
    private WebTestClient client;

    AuditLogRegistrationDTO body;
    private static final Long GUILD_ID = 124587145126L;
    private static final Long CHANNEL_ID = 541812154121L;

    private static final String BASE_URL = "/api/v1/log/audit";

    @BeforeEach
    void loadEntities(){

        body = new AuditLogRegistrationDTO();
        body.setGuildId(GUILD_ID);
        body.setChannelId(CHANNEL_ID);
    }

	@Test
	void guildSaveTest() {

        client.post()
                .uri(BASE_URL)
                .bodyValue(body)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AuditLogRegistrationDTO.class)
                .value(dto -> assertThat(dto.getGuildId()).isEqualTo(GUILD_ID))
                .value(dto -> assertThat(dto.getChannelId()).isEqualTo(CHANNEL_ID));
	}

}
