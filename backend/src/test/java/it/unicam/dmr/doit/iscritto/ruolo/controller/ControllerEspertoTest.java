package it.unicam.dmr.doit.iscritto.ruolo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import it.unicam.dmr.doit.DoitTest;
import it.unicam.dmr.doit.dataTransferObject.invito.InvitoDto;
import it.unicam.dmr.doit.dataTransferObject.iscritto.EnteDto;
import it.unicam.dmr.doit.dataTransferObject.iscritto.PersonaDto;
import it.unicam.dmr.doit.dataTransferObject.iscritto.RuoloDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.ProgettoDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.TagDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.ValutazioneDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.ValutazioneProgettistaDto;
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
class ControllerEspertoTest {

	@Autowired
	private MockMvc mvc;

	private JwtDto jwtEsperto;
	private JwtDto jwtEsperto2;
	private JwtDto jwtProponente;

	@Test
	void testValutaProgetto() throws Exception {
		startDataTest();

		richiestaValutazione("esperto", 1);

		// TODO errore passaggio parametro id_invito
		mvc.perform(MockMvcRequestBuilders.post("/esperto/progetto/valuta/{id_invito}", "proponente1")
				.contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtEsperto.getToken())
				.content(DoitTest
						.asJsonString(new ValutazioneDto("recensione", new HashSet<ValutazioneProgettistaDto>(), 1))))
				.andExpect(status().isOk());

		mvc.perform(MockMvcRequestBuilders.post("/esperto/progetto/valuta/proponente1")
				.contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtEsperto2.getToken())
				.content(DoitTest
						.asJsonString(new ValutazioneDto("recensione", new HashSet<ValutazioneProgettistaDto>(), 1))))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.messaggio").exists()).andReturn();

	}

	private void startDataTest() throws Exception {
		jwtEsperto = generateEsperto("esperto", "email1@mail.it", jwtEsperto);
		jwtEsperto2 = generateEsperto("esperto2", "email2@mail.it", jwtEsperto2);
		jwtProponente = generateProponente("proponente", "email3@mail.it", jwtProponente);
		generateProgetto();
	}

	private void richiestaValutazione(String identificativoEsperto, int idProgetto) throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/invito/invia").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtProponente.getToken())
				.content(DoitTest.asJsonString(new InvitoDto("contenuto invito", TipologiaInvito.VALUTAZIONE,
						List.of(identificativoEsperto), idProgetto))))
				.andExpect(status().isOk());
	}

	private void generateProgetto() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/proponente/proponi").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtProponente.getToken())
				.content(DoitTest
						.asJsonString(new ProgettoDto("Test", "obiettivi", "requisiti", new HashSet<TagDto>()))))
				.andExpect(status().isCreated());

	}

	private JwtDto generateEsperto(String identificativoEsperto, String mail, JwtDto jwtEsperto) throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/visitatore/persona/crea").contentType(MediaType.APPLICATION_JSON)
				.content(DoitTest.asJsonString(new PersonaDto(identificativoEsperto, mail, "pass", "nome", "cognome",
						"cittadinanza", "maschio", "telefono"))))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.messaggio").value("Registrazione avvenuta con successo.")).andReturn();

		MvcResult resEsperto = mvc
				.perform(MockMvcRequestBuilders.post("/visitatore/accedi").contentType(MediaType.APPLICATION_JSON)
						.content(DoitTest.asJsonString(new LoginIscritto(identificativoEsperto, "pass"))))
				.andExpect(status().isOk()).andExpect(jsonPath("$.token").exists()).andReturn();
		jwtEsperto = DoitTest.parseResponse(resEsperto, JwtDto.class);

		mvc.perform(MockMvcRequestBuilders.put("/iscritto/aggiungi_ruolo").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtEsperto.getToken())
				.content(DoitTest.asJsonString(new RuoloDto(TipologiaRuolo.ROLE_ESPERTO)))).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
		return jwtEsperto;
	}

	private JwtDto generateProponente(String identificativoProponente, String mail, JwtDto jwtProponente)
			throws Exception {
		mvc.perform(
				MockMvcRequestBuilders.post("/visitatore/ente/crea").contentType(MediaType.APPLICATION_JSON).content(
						DoitTest.asJsonString(new EnteDto(identificativoProponente, mail, "pass", "sede", new Date()))))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.messaggio").value("Registrazione avvenuta con successo.")).andReturn();

		MvcResult resProponente = mvc
				.perform(MockMvcRequestBuilders.post("/visitatore/accedi").contentType(MediaType.APPLICATION_JSON)
						.content(DoitTest.asJsonString(new LoginIscritto(identificativoProponente, "pass"))))
				.andExpect(status().isOk()).andExpect(jsonPath("$.token").exists()).andReturn();
		jwtProponente = DoitTest.parseResponse(resProponente, JwtDto.class);

		mvc.perform(MockMvcRequestBuilders.put("/iscritto/aggiungi_ruolo").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtProponente.getToken())
				.content(DoitTest.asJsonString(new RuoloDto(TipologiaRuolo.ROLE_PROPONENTE))))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
		return jwtProponente;
	}

}
