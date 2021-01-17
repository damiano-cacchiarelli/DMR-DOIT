package it.unicam.dmr.doit.iscritto.ruolo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import it.unicam.dmr.doit.DoitTest;
import it.unicam.dmr.doit.dataTransferObject.invito.InvitoDto;
import it.unicam.dmr.doit.dataTransferObject.security.JwtDto;
import it.unicam.dmr.doit.invito.TipologiaInvito;
import it.unicam.dmr.doit.security.jwt.JwtTokenFilter;

@SpringBootTest(properties = {
		"spring.datasource.url = jdbc:mysql://localhost:3306/testing?useSSL=false&serverTimezone=UTC&useLegacyDateTimeCode=false",
		"spring.jpa.hibernate.ddl-auto = create-drop" })
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ControllerProponenteTest {

	@Autowired
	private MockMvc mvc;

	private JwtDto jwtProponente;

	@Test
	void testPermetteValutazione() throws Exception {
		startDataTest();

		mvc.perform(
				MockMvcRequestBuilders.post("/proponente/permetti_valutazione").contentType(MediaType.APPLICATION_JSON)
						.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
								JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtProponente.getToken())
						.content(DoitTest.asJsonString(
								new InvitoDto("contenuto invito", TipologiaInvito.VALUTAZIONE, List.of("esperto"), 1))))
				.andExpect(status().isOk());

		mvc.perform(
				MockMvcRequestBuilders.post("/proponente/permetti_valutazione").contentType(MediaType.APPLICATION_JSON)
						.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
								JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtProponente.getToken())
						.content(DoitTest.asJsonString(
								new InvitoDto("contenuto invito", TipologiaInvito.VALUTAZIONE, List.of("esperto"), 1))))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.messaggio").exists()).andReturn();
	}

	private void startDataTest() throws Exception {
		DoitTest.generateEsperto(mvc, "esperto", "email1@mail.it");
		DoitTest.generateProponente(mvc, "proponente", "email3@mail.it");
		jwtProponente = DoitTest.getTokenAccesso(mvc, "proponente", "pass");
		DoitTest.generateProgetto(mvc, jwtProponente);

	}

}
