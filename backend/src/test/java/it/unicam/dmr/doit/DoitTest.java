package it.unicam.dmr.doit;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unicam.dmr.doit.dataTransferObject.iscritto.EnteDto;
import it.unicam.dmr.doit.dataTransferObject.iscritto.PersonaDto;
import it.unicam.dmr.doit.dataTransferObject.iscritto.RuoloDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.ProgettoDto;
import it.unicam.dmr.doit.dataTransferObject.progetto.TagDto;
import it.unicam.dmr.doit.dataTransferObject.security.JwtDto;
import it.unicam.dmr.doit.dataTransferObject.security.LoginIscritto;
import it.unicam.dmr.doit.security.jwt.JwtTokenFilter;
import it.unicam.dmr.doit.utenti.ruoli.TipologiaRuolo;

public class DoitTest {

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T parseResponse(MvcResult result, Class<T> responseClass) {
		try {
			String contentAsString = result.getResponse().getContentAsString();
			return new ObjectMapper().readValue(contentAsString, responseClass);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void generateEsperto(MockMvc mvc, String identificativoEsperto, String mail) throws Exception {

		mvc.perform(MockMvcRequestBuilders.post("/visitatore/persona/crea").contentType(MediaType.APPLICATION_JSON)
				.content(DoitTest.asJsonString(new PersonaDto(identificativoEsperto, mail, "pass", "nome", "cognome",
						"cittadinanza", "maschio", "telefono"))))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.messaggio").value("Registrazione avvenuta con successo.")).andReturn();

		MvcResult resEsperto = mvc
				.perform(MockMvcRequestBuilders.post("/visitatore/accedi").contentType(MediaType.APPLICATION_JSON)
						.content(DoitTest.asJsonString(new LoginIscritto(identificativoEsperto, "pass"))))
				.andExpect(status().isOk()).andExpect(jsonPath("$.token").exists()).andReturn();
		JwtDto jwtEsperto = DoitTest.parseResponse(resEsperto, JwtDto.class);

		mvc.perform(MockMvcRequestBuilders.put("/iscritto/aggiungi_ruolo").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtEsperto.getToken())
				.content(DoitTest.asJsonString(new RuoloDto(TipologiaRuolo.ROLE_ESPERTO)))).andExpect(status().isOk());
	}
	
	public static void generateProgettista(MockMvc mvc, String identificativoEsperto, String mail) throws Exception {

		mvc.perform(MockMvcRequestBuilders.post("/visitatore/persona/crea").contentType(MediaType.APPLICATION_JSON)
				.content(DoitTest.asJsonString(new PersonaDto(identificativoEsperto, mail, "pass", "nome", "cognome",
						"cittadinanza", "maschio", "telefono"))))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.messaggio").value("Registrazione avvenuta con successo.")).andReturn();

		MvcResult resEsperto = mvc
				.perform(MockMvcRequestBuilders.post("/visitatore/accedi").contentType(MediaType.APPLICATION_JSON)
						.content(DoitTest.asJsonString(new LoginIscritto(identificativoEsperto, "pass"))))
				.andExpect(status().isOk()).andExpect(jsonPath("$.token").exists()).andReturn();
		JwtDto jwtEsperto = DoitTest.parseResponse(resEsperto, JwtDto.class);

		mvc.perform(MockMvcRequestBuilders.put("/iscritto/aggiungi_ruolo").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtEsperto.getToken())
				.content(DoitTest.asJsonString(new RuoloDto(TipologiaRuolo.ROLE_PROGETTISTA)))).andExpect(status().isOk());
	}

	public static void generateProponente(MockMvc mvc, String identificativoProponente, String mail)
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
		JwtDto jwtProponente = DoitTest.parseResponse(resProponente, JwtDto.class);

		mvc.perform(MockMvcRequestBuilders.put("/iscritto/aggiungi_ruolo").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtProponente.getToken())
				.content(DoitTest.asJsonString(new RuoloDto(TipologiaRuolo.ROLE_PROPONENTE))))
				.andExpect(status().isOk());
	}
	
	public static void generateProgetto(MockMvc mvc, JwtDto jwtProponente) throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/proponente/proponi").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtProponente.getToken())
				.content(DoitTest
						.asJsonString(new ProgettoDto("Test", "obiettivi", "requisiti", new HashSet<TagDto>()))))
				.andExpect(status().isCreated());

	}
	
	public static JwtDto getTokenAccesso(MockMvc mvc, String identificativo, String password) throws Exception {
		MvcResult resProponente = mvc
				.perform(MockMvcRequestBuilders.post("/visitatore/accedi").contentType(MediaType.APPLICATION_JSON)
						.content(DoitTest.asJsonString(new LoginIscritto(identificativo, password))))
				.andExpect(status().isOk()).andExpect(jsonPath("$.token").exists()).andReturn();
		return DoitTest.parseResponse(resProponente, JwtDto.class);
	}
}
