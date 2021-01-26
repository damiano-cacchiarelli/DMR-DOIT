package it.unicam.dmr.doit.iscritto.ruolo.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import it.unicam.dmr.doit.DoitTest;
import it.unicam.dmr.doit.dataTransferObject.invito.InvitoDto;
import it.unicam.dmr.doit.dataTransferObject.invito.RispostaInvitoDto;
import it.unicam.dmr.doit.dataTransferObject.security.JwtDto;
import it.unicam.dmr.doit.invito.TipologiaInvito;
import it.unicam.dmr.doit.invito.TipologiaRisposta;
import it.unicam.dmr.doit.security.jwt.JwtTokenFilter;

@SpringBootTest(properties = {
		"spring.datasource.url = jdbc:mysql://localhost:3306/testing?useSSL=false&serverTimezone=UTC&useLegacyDateTimeCode=false",
		"spring.jpa.hibernate.ddl-auto = create-drop" 
		})
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class ControllerProgettistaTest {

	@Autowired
	private MockMvc mvc;

	private static JwtDto jwtProponente;
	private static JwtDto jwtProgettista;

	@Test
	@Order(1)
	void testGestisciRichiestePartecipazione() throws Exception {
		startDataTest();

		richiestaDiPartecipazione(mvc, "progettista", 1);

		mvc.perform(MockMvcRequestBuilders.put("/progettista/gestisci_proposta_partecipazione").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtProgettista.getToken())
				.content(DoitTest.asJsonString(new RispostaInvitoDto("proponente1", TipologiaRisposta.IN_ATTESA))))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.messaggio").exists());
		
		mvc.perform(MockMvcRequestBuilders.put("/progettista/gestisci_proposta_partecipazione").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtProgettista.getToken())
				.content(DoitTest.asJsonString(new RispostaInvitoDto("proponente1", TipologiaRisposta.ACCETTATA))))
				.andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders.put("/progettista/gestisci_proposta_partecipazione").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtProgettista.getToken())
				.content(DoitTest.asJsonString(new RispostaInvitoDto("proponente1", TipologiaRisposta.ACCETTATA))))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.messaggio").exists());
	}
	
	@Test
	@Order(2)
	void testCandidatiAlProgetto() throws Exception{
		DoitTest.generateProgettista(mvc, "progettista1", "email3@mail.it");
		jwtProgettista = DoitTest.getTokenAccesso(mvc, "progettista1", "pass");
		
		mvc.perform(MockMvcRequestBuilders.put("/progettista/candidati").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtProgettista.getToken())
				.content(DoitTest.asJsonString(new InvitoDto("Test", TipologiaInvito.RICHIESTA, List.of("proponente"), 1))))
				.andExpect(status().isOk());
		
		mvc.perform(MockMvcRequestBuilders.put("/progettista/candidati").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtProgettista.getToken())
				.content(DoitTest.asJsonString(new InvitoDto("Test", TipologiaInvito.RICHIESTA, List.of("proponente"), 1))))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.messaggio").exists());
	}

	private void startDataTest() throws Exception {
		DoitTest.generateProgettista(mvc, "progettista", "email1@mail.it");
		DoitTest.generateProponente(mvc, "proponente", "email2@mail.it");
		jwtProponente = DoitTest.getTokenAccesso(mvc, "proponente", "pass");
		jwtProgettista = DoitTest.getTokenAccesso(mvc, "progettista", "pass");
		DoitTest.generateProgetto(mvc, jwtProponente);
				
	}

	private static void richiestaDiPartecipazione(MockMvc mvc, String identificativoProgettista, int idProgetto)
			throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/invito/invia").contentType(MediaType.APPLICATION_JSON)
				.header(JwtTokenFilter.AUTHORIZATION_HEADER_NAME,
						JwtTokenFilter.TOKEN_BEARER_HEADER_NAME + jwtProponente.getToken())
				.content(DoitTest.asJsonString(
						new InvitoDto("contenuto invito", TipologiaInvito.RICHIESTA, List.of(identificativoProgettista), idProgetto))))
				.andExpect(status().isOk());
	}
	

}
