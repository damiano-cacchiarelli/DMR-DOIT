package it.unicam.dmr.doit.iscritto.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import it.unicam.dmr.doit.DoitTest;
import it.unicam.dmr.doit.dataTransferObject.iscritto.EnteDto;
import it.unicam.dmr.doit.dataTransferObject.iscritto.PersonaDto;
import it.unicam.dmr.doit.dataTransferObject.iscritto.RuoloDto;
import it.unicam.dmr.doit.dataTransferObject.security.JwtDto;
import it.unicam.dmr.doit.dataTransferObject.security.LoginIscritto;
import it.unicam.dmr.doit.security.jwt.JwtTokenFilter;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

@SpringBootTest(properties = {
		"spring.datasource.url = jdbc:mysql://localhost:3306/testing?useSSL=false&serverTimezone=UTC&useLegacyDateTimeCode=false",
		"spring.jpa.hibernate.ddl-auto = create-drop" })
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class ControllerVisitatoreTest {

	@Autowired
	protected MockMvc mvc;

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
	}

	@Test
	@Order(2)
	void testGetIscritto() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/visitatore/iderrato").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.messaggio").exists()).andReturn();
		mvc.perform(MockMvcRequestBuilders.get("/visitatore/ente").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.sede").exists()).andReturn();
		mvc.perform(MockMvcRequestBuilders.get("/visitatore/persona").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.nome").exists()).andReturn();
	}

	@Test
	@Order(3)
	void testAccedi() throws Exception {
		MvcResult res = mvc
				.perform(MockMvcRequestBuilders.post("/visitatore/accedi").contentType(MediaType.APPLICATION_JSON)
						.content(DoitTest.asJsonString(new LoginIscritto("persona", "pass"))))
				.andExpect(status().isOk()).andExpect(jsonPath("$.token").exists()).andReturn();

		mvc.perform(MockMvcRequestBuilders.post("/visitatore/accedi").contentType(MediaType.APPLICATION_JSON)
				.content(DoitTest.asJsonString(new LoginIscritto("persona", "passsbagliata"))))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").exists()).andReturn();
		mvc.perform(MockMvcRequestBuilders.post("/visitatore/accedi").contentType(MediaType.APPLICATION_JSON)
				.content(DoitTest.asJsonString(new LoginIscritto("pesonaidk", "pass"))))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.message").exists()).andReturn();

		JwtDto jwt = DoitTest.parseResponse(res, JwtDto.class);
		mvc.perform(MockMvcRequestBuilders.put("/iscritto/aggiungi_ruolo").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwt.getToken())
				.content(DoitTest.asJsonString(new RuoloDto(TipologiaRuolo.ROLE_ESPERTO)))).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	@Order(4)
	void testEsisteIscrittoByRuolo() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/visitatore/esiste/persona/ROLE_PROGETTISTA")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string("false")).andReturn();
		mvc.perform(MockMvcRequestBuilders.get("/visitatore/esiste/persona/ROLE_ESPERTO")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string("true"))
				.andReturn();
	}

}
