package br.com.project.senhas.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.project.senhas.model.Password;
import br.com.project.senhas.model.Password.CsvUtils;
import br.com.project.senhas.repository.PasswordRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Service
@RestController
@Slf4j
@Tag(name = "Passwords")
public class PasswordResource {

	@Autowired
	PasswordRepository passwordRepository;

	@PostMapping("/insert")
	public ResponseEntity<String> createPasswords(@RequestBody Password password) {
		Password save = passwordRepository.save(password);
		if (save != null) {
			log.info("Saved data: {}", save.toString());
		} else {
			log.info("Error saving passwords");
		}
		return ResponseEntity.ok(save.toString());
	}

	@GetMapping("/list")
	public ResponseEntity<String> findAllPasswords() {
		List<Password> findAll = passwordRepository.findAll();
		if (!findAll.isEmpty()) {
			log.info("Data: {}", findAll.toArray());
		} else {
			log.info("Data is empty");
		}
		return ResponseEntity.ok(findAll.toString());
	}

	@GetMapping("/readCSV")
	public ResponseEntity<String> readCSV(@RequestBody InputStream body) throws IOException  {
		List<Password> saveAll = passwordRepository.saveAll(CsvUtils.read(Password.class, body));
		log.info("Entidade salva: {}", saveAll.toString());

		return ResponseEntity.ok("final");
	}
	
	@PostMapping(value = "/upload", consumes = "multipart/form-data")
    public void uploadMultipart(@RequestParam("file") MultipartFile file) throws IOException {
        passwordRepository.saveAll(CsvUtils.read(Password.class, file.getInputStream()));
    }

	@SuppressWarnings("unchecked")
	@GetMapping("/find/{url}")
	public ResponseEntity<String> findUrl(@PathVariable String url) {
		Password findByUrl = passwordRepository.findByUrl(url);
		if (findByUrl != null) {
			log.info("URL Found it");
			return ResponseEntity.ok(findByUrl.toString());
		} else {
			log.info("URL not found");
			return (ResponseEntity<String>) ResponseEntity.noContent();
		}
	}
}
