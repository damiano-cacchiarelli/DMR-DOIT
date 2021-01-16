package it.unicam.dmr.doit.iscritto.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import it.unicam.dmr.doit.DoitTest;
import it.unicam.dmr.doit.dataTransferObject.invito.InvitoDto;
import it.unicam.dmr.doit.dataTransferObject.iscritto.EnteDto;
import it.unicam.dmr.doit.dataTransferObject.iscritto.PersonaDto;
import it.unicam.dmr.doit.dataTransferObject.iscritto.RuoloDto;
import it.unicam.dmr.doit.dataTransferObject.security.JwtDto;
import it.unicam.dmr.doit.dataTransferObject.security.LoginIscritto;
import it.unicam.dmr.doit.invito.TipologiaInvito;
import it.unicam.dmr.doit.security.jwt.JwtTokenFilter;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

@SpringBootTest(properties = {
		"spring.datasource.url = jdbc:mysql://localhost:3306/testing?useSSL=false&serverTimezone=UTC&useLegacyDateTimeCode=false",
		"spring.jpa.hibernate.ddl-auto = create-drop" })
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class ControllerInvitoTest {

	@Autowired
	private MockMvc mvc;

	private static JwtDto jwt;
	private static JwtDto jwt2;

	@Test
	@Order(1)
	void creaIscritto() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/visitatore/persona/crea").contentType(MediaType.APPLICATION_JSON)
				.content(DoitTest.asJsonString(new PersonaDto("persona", "emailpersona@al.it", "pass", "nome",
						"cognome", "cittadinanza", "m", "tel"))))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.messaggio").value("Registrazione avvenuta con successo.")).andReturn();
		mvc.perform(MockMvcRequestBuilders.post("/visitatore/ente/crea").contentType(MediaType.APPLICATION_JSON)
				.content(DoitTest.asJsonString(new EnteDto("ente", "emailente@al.it", "pass", "Italia", new Date()))))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.messaggio").value("Registrazione avvenuta con successo.")).andReturn();

		MvcResult res = mvc
				.perform(MockMvcRequestBuilders.post("/visitatore/accedi").contentType(MediaType.APPLICATION_JSON)
						.content(DoitTest.asJsonString(new LoginIscritto("persona", "pass"))))
				.andExpect(status().isOk()).andExpect(jsonPath("$.token").exists()).andReturn();
		jwt = DoitTest.parseResponse(res, JwtDto.class);
		
		MvcResult res2 = mvc
				.perform(MockMvcRequestBuilders.post("/visitatore/accedi").contentType(MediaType.APPLICATION_JSON)
						.content(DoitTest.asJsonString(new LoginIscritto("ente", "pass"))))
				.andExpect(status().isOk()).andExpect(jsonPath("$.token").exists()).andReturn();
		jwt2 = DoitTest.parseResponse(res2, JwtDto.class);
		mvc.perform(MockMvcRequestBuilders.put("/iscritto/aggiungi_ruolo").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwt2.getToken())
				.content(DoitTest.asJsonString(new RuoloDto(TipologiaRuolo.ROLE_PROGETTISTA)))).andExpect(status().isOk());
	}

	@Test
	@Order(2)
	void testListaInviti() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/invito/all").contentType(MediaType.APPLICATION_JSON).header(
				JwtTokenFilter.AUTHORIZATION_HEADER_NAME, JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwt.getToken()))
				.andExpect(status().isOk());
		mvc.perform(MockMvcRequestBuilders.get("/invito/all").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@Order(3)
	void testInviaInvito() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/invito/invia").contentType(MediaType.APPLICATION_JSON).header(
				JwtTokenFilter.AUTHORIZATION_HEADER_NAME, JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwt.getToken())
				.content(DoitTest.asJsonString(new InvitoDto("contenuto invito", TipologiaInvito.RICHIESTA, List.of("persona"), 1))));
		mvc.perform(MockMvcRequestBuilders.post("/invito/invia").contentType(MediaType.APPLICATION_JSON).header(
				JwtTokenFilter.AUTHORIZATION_HEADER_NAME, JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwt2.getToken())
				.content(DoitTest.asJsonString(new InvitoDto("contenuto invito", TipologiaInvito.RICHIESTA, List.of("persona"), 1))))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.messaggio").exists()).andReturn();
	}
}
