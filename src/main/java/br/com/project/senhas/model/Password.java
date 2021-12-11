package br.com.project.senhas.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Slf4j
public class Password implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 2901242848263538059L;

//	@Autowired
//	PasswordRepository passwordRepository;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 1024)
	private String title;
	@Column(length = 1024)
	private String url;
	@Column(length = 1024)
	private String username;
	@Column(length = 1024)
	private String password;

	public class CsvUtils {
	    private static final CsvMapper mapper = new CsvMapper();
	    public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
	        CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
	        ObjectReader reader = mapper.readerFor(clazz).with(schema);
	        return reader.<T>readValues(stream).readAll();
	    }
	}

	
}
