package kz.ziyatbek.studentapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentApiApplicationTests {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate rest;

    @Test
    void createAndRetrieveStudent() {
        Student payload = new Student(
            "Ziyatbek Dinmukhamed",
            "ziyatbek@example.com",
            "DevOps"
        );

        ResponseEntity<Student> created =
            rest.postForEntity(url(), payload, Student.class);

        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(created.getBody()).isNotNull();
        assertThat(created.getBody().getId()).isNotNull();

        ResponseEntity<Student> fetched =
            rest.getForEntity(url() + "/" + created.getBody().getId(), Student.class);

        assertThat(fetched.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(fetched.getBody().getName()).isEqualTo("Ziyatbek Dinmukhamed");
    }

    @Test
    void missingStudentReturns404() {
        ResponseEntity<String> response =
            rest.getForEntity(url() + "/999999", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateStudent() {
        Student payload = new Student(
            "Ziyatbek Dinmukhamed",
            "ziyatbek@example.com",
            "DevOps"
        );

        ResponseEntity<Student> created =
            rest.postForEntity(url(), payload, Student.class);

        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(created.getBody()).isNotNull();

        Long id = created.getBody().getId();

        Student updated = new Student(
            "Ziyatbek Dinmukhamed",
            "ziyatbek.updated@example.com",
            "Software Engineering"
        );

        rest.put(url() + "/" + id, updated);

        ResponseEntity<Student> response =
            rest.getForEntity(url() + "/" + id, Student.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getEmail())
            .isEqualTo("ziyatbek.updated@example.com");
        assertThat(response.getBody().getProgram())
            .isEqualTo("Software Engineering");
    }

    @Test
    void deleteStudent() {
        Student payload = new Student(
            "Ziyatbek Dinmukhamed",
            "ziyatbek.delete@example.com",
            "DevOps"
        );

        ResponseEntity<Student> created =
            rest.postForEntity(url(), payload, Student.class);

        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(created.getBody()).isNotNull();

        Long id = created.getBody().getId();

        rest.delete(url() + "/" + id);

        ResponseEntity<String> response =
            rest.getForEntity(url() + "/" + id, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private String url() {
        return "http://localhost:" + port + "/api/students";
    }
}
